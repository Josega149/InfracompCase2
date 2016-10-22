package cliente;

import java.security.PublicKey;

public class MainCliente 
{
	// canal = new Socket(pDireccion , pPuerto);
	// new BufferedReader(new InputStreamReader( canal.getInputStream()));
	
	CifradorAsimetricoRSA cifradorAsim;
	PublicKey publicKey;
	
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
		
	}
	
	
	public static void main(String[] args) 
	{
		MainCliente main = new MainCliente();
	}
}
