package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.*;

import javax.crypto.Cipher;

public class CifradorAsimetricoRSA 
{
	/**
	 * @Author jg.tamura10
	 * Debe descifrar con la llave publica del server la llave simetrica que se va a usar
	 * Debe cifrar con la llave privada del cliente la llave simetrica que se va a usar
	 * Debe cifrar el digest
	 */
	private final static String ALGORITMO="RSA";
	private KeyPair keyPair;
	
	public CifradorAsimetricoRSA()
	{
		try 
		{
			KeyPairGenerator generator =KeyPairGenerator.getInstance(ALGORITMO);
			
			generator.initialize(1024);
			keyPair = generator.generateKeyPair();
			
			System.out.println("llave privada "+keyPair.getPrivate()+" \n llave publica "+keyPair.getPublic().toString() );
		}
		catch(Exception e){e.getStackTrace();}
	}
	
	public String descifrarLlaveSimetrica(byte [] textoCifrado, PublicKey llavePublicaServer) throws Exception
	{
		String llaveSimetrica = "";
		//llega un texto cifrado con llave privada del server que contiene la simetrica que sera usada
		llaveSimetrica = descifrar(textoCifrado, llavePublicaServer);
		
		if(llaveSimetrica == null){throw new Exception("Ocurrio un error en descifrar");}
		return llaveSimetrica;
	}
	public PublicKey darLlavePublica()
	{
		return keyPair.getPublic();
	}

	public byte[] cifrar(String mensajeACifrar) 
	{
		try 
		{
			Cipher cipher = Cipher.getInstance(ALGORITMO);
			//BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));
			//String pwd = stdIn.readLine();
			
			byte [] clearText = mensajeACifrar.getBytes();
			String s1 = new String (clearText);
			System.out.println("texto original: " + s1);
			
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());//encripta con la publica
			
			long startTime = System.nanoTime();
			byte [] cipheredText = cipher.doFinal(clearText);
			long endTime = System.nanoTime();
			
			System.out.println("texto cifrado: " + cipheredText);
			System.out.println("Tiempo asimetrico: " +(endTime - startTime));
			
			return cipheredText;
		}
		catch (Exception e)
		{
			System.out.println("Excepcion: " + e.getMessage());
			return null;
		}
	}

	public String descifrar(byte[] cipheredText, PublicKey llave) 
	{
		try 
		{
			Cipher cipher = Cipher.getInstance(ALGORITMO);
			cipher.init(Cipher.DECRYPT_MODE, llave); // desencripta con la llave que le entra
			byte [] clearText = cipher.doFinal(cipheredText);
			String s3 = new String(clearText);
			System.out.println("texto original: " + s3);
			return s3;
		}
		catch (Exception e) 
		{
			System.out.println("Excepcion: " + e.getMessage());
		}
		return null;
	}
}
