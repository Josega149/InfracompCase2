package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;

public class MainCliente 
{
	private Socket canal;
	private PrintWriter out;
	private BufferedReader in;

	private final static String DIRECCION = "localhost";
	private final static int PUERTO = 4444;

	private CifradorAsimetricoRSA cifradorAsim;
	private PublicKey publicKey;

	public MainCliente()
	{
		//crea el cifrador asimetrico y la llave publica
		cifradorAsim = new CifradorAsimetricoRSA();
		publicKey = cifradorAsim.darLlavePublica();

		//crea el cifrador simetrico 

		//crea el hash

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
		out.println("CERTFICADOCLIENTE");
		resp = in.readLine();
		if(!resp.equals("CERTIFICADOSERVIDOR")){throw new Exception ("SERVIDOR RESPONDIO MAL (el certificado)");}
		
		out.println("OK");
		resp = in.readLine();
		if(!resp.equals("CIFRADOKC+")){throw new Exception ("SERVIDOR RESPONDIO MAL (el cifrado ck+)");}
		
		out.println("CIFRADOKS+");
		resp = in.readLine();
		if(!resp.equals("OK")){throw new Exception ("SERVIDOR RESPONDIO MAL (el OK despues del cifrado ks+)");}
		
		out.println("CIFRADOLS1");
		resp = in.readLine();
		if(!resp.equals("CIFRADOLS2")){throw new Exception ("SERVIDOR RESPONDIO MAL (el CIFRADOLS2");}
		
		System.out.println("TERMINA!");
	}


		public static void main(String[] args) 
		{
			MainCliente main = new MainCliente();
		}
	}
