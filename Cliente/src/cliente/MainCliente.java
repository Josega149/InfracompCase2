package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class MainCliente 
{
	private Socket canal;
	private PrintWriter out;
	private OutputStream os;
	private BufferedReader in;

	private final static String DIRECCION = "localhost";
	private final static int PUERTO = 4443;

	private CifradorSimetricoAES cifradorSim;
	private CifradorAsimetricoRSA cifradorAsim;
	private KeyPair keyAsin;
	
	private CifradorHmacMD5 cifradorHash;
	private ManejadorCertificado manejadorCertificado;

	public MainCliente()
	{
		//crea el cifrador asimetrico y la llave publica
		cifradorAsim = new CifradorAsimetricoRSA();
		keyAsin = cifradorAsim.darLlave();

		//crea el cifrador simetrico 
		cifradorSim = new CifradorSimetricoAES();

		//crea el hash
		cifradorHash = new CifradorHmacMD5();
		
		//crea el manejador de certificado
		manejadorCertificado = new ManejadorCertificado();
		
		//inicia el protocolo de comunicacion
		iniciarComunicacion();

	}

	public void iniciarComunicacion()
	{
		// conectar al servidor
		try 
		{
			canal = new Socket(DIRECCION, PUERTO);
			out = new PrintWriter(canal.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(canal.getInputStream()));

			iniciarConversacion();
		}catch (Exception e)
		{
			e.getStackTrace();
		}
	}
	public void iniciarConversacion() throws Exception
	{
		out.println("HOLA");
		String resp = in.readLine();
		if(!resp.equals("OK")){throw new Exception("SERVIDOR REPONDIO MAL (el hola)");}
		
		out.println("ALGORITMOS:AES:RSA:HMACMD5");
		resp = in.readLine();
		if(resp.equals("OK") || resp.equals("ERROR"))
		{
			if(resp.equals("ERROR")){throw new Exception("SACO ERROR POR LOS ALGORITMOS");}
		}else{throw new Exception("SERVIDOR REPONDIO MAL (ni ok ni error para algoritmos)");}
		
		long tInicioAut = System.nanoTime();
		// comienzo pasar el certificado
		PublicKey llavePublicaServer = manejadorCertificado.creationYProcesamiento(keyAsin,canal.getOutputStream(),out, in);
		//manejadorCertificado.procesarCertificado();
		
		
		out.println("OK");
		out.println("OK");
		
		resp = in.readLine();//llega cifrado con la llave publica del cliente (la mia)
		byte [] textoEnBytes = DatatypeConverter.parseHexBinary(resp);
		byte [] llaveSimetricaAcordada = cifradorAsim.descifrarLlaveSimetrica(textoEnBytes, keyAsin.getPrivate());
						System.out.println("llave simetrica acordada "+llaveSimetricaAcordada);
						byte[] decodedKey = llaveSimetricaAcordada;// hasta aqui deberia estar bien
		
		SecretKey llaveSimetrica = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
						System.out.println("LLAVE SIMETRICAAA "+llaveSimetrica);
		//mando cifrado con la llave publica del server la llave que me llego
		byte[] llaveSimetricaCifrada = cifradorAsim.cifrarLlaveSimetrica(decodedKey, llavePublicaServer);
		System.out.println("Va a mandar llave simetrica cifrada ");
		out.println(DatatypeConverter.printHexBinary(llaveSimetricaCifrada) );
	
		resp = in.readLine();
		if(!resp.equals("OK")){throw new Exception ("SERVIDOR RESPONDIO MAL (el OK despues de mandar la llave simetrica)");}
		
		//// Se cuenta el tiempo de autenticación
		long tiempoAutenticacion = System.nanoTime()-tInicioAut;
		
		//cifra el mensaje a mandar
		String consulta = "1";
		byte [] consultaCifrada = cifradorSim.cifrar(consulta, llaveSimetrica);
		byte[] hConsulta = cifradorHash.calcular(consulta, llaveSimetrica);
		
		byte[] hConsultaCifrado = cifradorSim.cifrarB(hConsulta, llaveSimetrica);
		String mensajeCompleto = DatatypeConverter.printHexBinary(consultaCifrada)+":"+DatatypeConverter.printHexBinary(hConsultaCifrado);
		
		//manda el mensaje concaenado
		long tInicioConsulta = System.nanoTime();
		out.println(mensajeCompleto);
		System.out.println("ya mando el mensaje completo");
		resp = in.readLine();
		
		byte[] elBinary = DatatypeConverter.parseHexBinary(resp);
		//decifra el mensaje de manera sincronica la respuesta a la consulta
		resp = cifradorSim.descifrar(elBinary, llaveSimetrica);
		System.out.println(resp);
		String [] respArray = resp.split(":"); 
		
		
		//verifica si dice ok o error
		if(resp.startsWith("OK") || resp.startsWith("ERROR"))
		{
			if(resp.equals("ERROR")){throw new Exception("SACO ERROR POR LA CONSULTA");}
		}
		else
		{
			throw new Exception("SERVIDOR REPONDIO MAL (ni ok ni error para la consulta)");
		}
		resp= respArray[1];
		long tiempoRespuesta = System.nanoTime()-tInicioConsulta;
		System.out.println("TERMINA! "+ resp);
		
		try{
			File tiempos = new File("./data/tiempos");
			PrintWriter writer = new PrintWriter(new FileWriter(tiempos,true));
			writer.println("tAutenticación:"+ TimeUnit.MILLISECONDS.toMillis(tiempoAutenticacion));
			writer.println("tRespuesta:"+ TimeUnit.MILLISECONDS.toMillis(tiempoRespuesta));
			writer.close();
			}catch (Exception e){
				//:)
			}
	}

		public static void main(String[] args) 
		{
			MainCliente main = new MainCliente();
		}
	}
