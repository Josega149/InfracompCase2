package clienteSinSeguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


public class MainClienteSinSeguridad 
{
	private Socket canal;
	private PrintWriter out;
	private BufferedReader in;
	
	private final static String DIRECCION = "192.168.0.5";
	private final static int PUERTO = 4444;

	
	
	public MainClienteSinSeguridad()
	{
		
		// conectar al servidor
		try 
		{
		    canal = new Socket(DIRECCION, PUERTO);
			out = new PrintWriter(canal.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(canal.getInputStream()));
			
			iniciarConversacion();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			try{
				File tiempos = new File("./data/tiempos");
				PrintWriter writer = new PrintWriter(new FileWriter(tiempos,true));
				writer.println("falla:m");
				writer.close();
				}catch (Exception x){
					//:)
				}
		} 

	}
	public void iniciarConversacion() throws Exception
	{
		
		out.println("HOLA");
		String resp = in.readLine();
		//if(!resp.equals("OK")){throw new Exception("SERVIDOR REPONDIO MAL (el hola)");}
		
		out.println("ALGORITMOS:AES:RSA:HMACMD5");
		resp = in.readLine();
		if(resp.equals("OK") || resp.equals("ERROR"))
		{
			//if(resp.equals("ERROR")){throw new Exception("SACO ERROR POR LOS ALGORITMOS");}
		}//else{throw new Exception("SERVIDOR REPONDIO MAL (ni ok ni error para algoritmos)");}
		
		// comienzo pasar el certificado
		long tInicioAut= System.nanoTime()	;
		out.println("CERTFICADOCLIENTE");
		resp = in.readLine();
		//if(!resp.equals("CERTIFICADOSERVIDOR")){throw new Exception ("SERVIDOR RESPONDIO MAL (el certificado)");}
		
		out.println("OK");
		
		
		resp = in.readLine();
		//if(!resp.equals("CIFRADOKC+")){throw new Exception ("SERVIDOR RESPONDIO MAL (el cifrado ck+)");}
		
		out.println("CIFRADOKS+");
		resp = in.readLine();
		//if(!resp.equals("OK")){throw new Exception ("SERVIDOR RESPONDIO MAL (el OK despues del cifrado ks+)");}
		long tiempoAutenticacion = System.nanoTime() - tInicioAut;
		
		long tInicioConsulta = System.nanoTime();
		out.println("CIFRADOLS1");
		resp = in.readLine();
		//if(!resp.equals("CIFRADOLS2")){throw new Exception ("SERVIDOR RESPONDIO MAL (el CIFRADOLS2");}
		
		long tiempoRespuesta = System.nanoTime() - tInicioConsulta;
		System.out.println("TERMINA!");
		
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
	/**
	public static void main(String[] args)
	{
		MainClienteSinSeguridad main = new MainClienteSinSeguridad();
	}*/

}
