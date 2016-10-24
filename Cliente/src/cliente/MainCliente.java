package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

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
		
		// comienzo pasar el certificado
		PublicKey llavePublicaServer = manejadorCertificado.creationYProcesamiento(keyAsin,canal.getOutputStream(),out, in);
		//manejadorCertificado.procesarCertificado();
		
		
		out.println("OK");
		out.println("OK");
		
		resp = in.readLine();//llega cifrado con la llave publica del cliente (la mia)
		byte [] textoEnBytes = DatatypeConverter.parseHexBinary(resp);
		String llaveSimetricaAcordada = cifradorAsim.descifrarLlaveSimetrica(textoEnBytes, keyAsin.getPrivate());
		System.out.println("llave simetrica acordada "+llaveSimetricaAcordada);
		byte[] decodedKey = (llaveSimetricaAcordada).getBytes();
		// rebuild key using SecretKeySpec
		//System.out.println(" que pasaaaaa ");
		SecretKey llaveSimetrica = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		System.out.println("LLAVE SIMETRICAAA "+llaveSimetrica);
		//mando cifrado con la llave publica del server la llave que me llego
		String llaveSimetricaCifrada = cifradorAsim.cifrarLlaveSimetrica(llaveSimetrica.toString(), llavePublicaServer);
		System.out.println("La llave simetrica cifradaaaaaaaa ");
		out.println(DatatypeConverter.printHexBinary((llaveSimetricaCifrada).getBytes()) );
	
		resp = in.readLine();
		if(!resp.equals("OK")){throw new Exception ("SERVIDOR RESPONDIO MAL (el OK despues de mandar la llave simetrica)");}
		
		
		
		//cifra el mensaje a mandar
		String consulta = "consulta";
		String consultaCifrada = new String(cifradorSim.cifrar(consulta, llaveSimetrica));
		String hConsulta = new String(cifradorHash.calcular(consulta));
		
		String mensajeCompleto = consultaCifrada+":"+hConsulta;
		
		//manda el mensaje concaenado
		out.println(mensajeCompleto);
		resp = in.readLine();
		
		//decifra el mensaje de manera sincronica la respuesta a la consulta
		resp = cifradorSim.descifrar(resp.getBytes(), llaveSimetrica);
		
		//verifica si dice ok o error
		if(resp.startsWith("OK") || resp.equals("ERROR"))
		{
			if(resp.equals("ERROR")){throw new Exception("SACO ERROR POR LA CONSULTA");}
		}
		else
		{
			throw new Exception("SERVIDOR REPONDIO MAL (ni ok ni error para la consulta)");
		}
		
		System.out.println("TERMINA!");
	}


		public static void main(String[] args) 
		{
			MainCliente main = new MainCliente();
		}
	}
