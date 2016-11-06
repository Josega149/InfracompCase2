package clienteSinSeguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class MainPruebas {
	
	public final static int NUMBER_OF_TASKS=400; //400, 200  y 80
	
	public final static int GAP_BETWEEN_TASKS=20; //20, 40 y 100
	
	private static int promedioAutenticacion;
	
	private static int promedioConsulta;
	
	private static int numPerdidas;

	public static void main(String[] args)
	{
		for (int i=0;i<10;i++)
		{
			@SuppressWarnings("unused")
			Generator gen = new Generator();
			
			int sumaAut=0;
			int sumaCons=0;
			try{
				//Leo los tiempos
				FileReader fr = new FileReader("./data/resultados");
				BufferedReader br = new BufferedReader(fr);
				String banana = br.readLine();
				while (banana!= null){
					String [] arreglito = banana.split(":");
					if (arreglito[0].equals("promAut"))
						sumaAut+=Integer.parseInt(arreglito[1]);
					else if (arreglito[0].equals("numPerdidas"))
						numPerdidas+=Integer.parseInt(arreglito[1]);
					else
						sumaCons+=Integer.parseInt(arreglito[1]);
					banana = br.readLine();
				}
				promedioAutenticacion = NUMBER_OF_TASKS!=numPerdidas? sumaAut / (NUMBER_OF_TASKS-numPerdidas):-1;
				promedioConsulta = NUMBER_OF_TASKS!=numPerdidas? sumaCons/(NUMBER_OF_TASKS-numPerdidas):-1;
				br.close();
			}catch(Exception e){
				
			}
			
			try{
				File res = new File("./data/final");
				PrintWriter writer = new PrintWriter(new FileWriter(res,true));
				writer.println("tAutenticación con "+MainPruebas.NUMBER_OF_TASKS+" clientes "
						+ "y ramp-up de "+MainPruebas.GAP_BETWEEN_TASKS+" : "
						+  promedioAutenticacion);
				writer.println("tRespuesta con "+MainPruebas.NUMBER_OF_TASKS+" clientes "
						+ "y ramp-up de "+MainPruebas.GAP_BETWEEN_TASKS+" : "
						+ promedioConsulta);
				writer.println("numPerdidas con "+MainPruebas.NUMBER_OF_TASKS+" clientes "
						+ "y ramp-up de "+MainPruebas.GAP_BETWEEN_TASKS+" : "
						+ numPerdidas);
				writer.close();
				}catch (Exception e){
					//:)
				}
		}
		
		
		
	}
	
}
