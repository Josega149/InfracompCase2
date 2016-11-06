package prom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;



public class Main {
	
	public final static int NUMBER_OF_TASKS=400; //400, 200  y 80

	public final static int GAP_BETWEEN_TASKS=20; //20, 40 y 100
	

	private static int numPerdidas;
	
	private static long tiempoAutenticacion;
	
	private static long tiempoRespuesta;
	
	
	public static void main(String[] args)
	{
		generarResultados();
		escribirResultados();
	}
	
	private static void generarResultados (){
		long sumaAut = 0;
		long num = NUMBER_OF_TASKS;
		long sumaCons = 0;
		try{
			//Leo los tiempos
			FileReader fr = new FileReader("../ClienteSinSeguridad/data/tiempos");
			BufferedReader br = new BufferedReader(fr);
			String banana = br.readLine();
			while (banana!= null){
				String [] arreglito = banana.split(":");
				if (arreglito[0].equals("tAutenticación"))
					sumaAut+=Integer.parseInt(arreglito[1]);
				else if (arreglito[0].equals("falla"))
					numPerdidas++;
				else
					sumaCons+=Integer.parseInt(arreglito[1]);
				banana = br.readLine();
			}
			tiempoAutenticacion = num!=numPerdidas? sumaAut / (num-numPerdidas):-1;
			tiempoRespuesta = num!=numPerdidas? sumaCons/(num-numPerdidas):-1;
			br.close();
		}catch(Exception e){
			
		}
	}
				
	private static void escribirResultados (){
		try{
			File res = new File("../ClienteSinSeguridad/data/resultados");
			PrintWriter writer = new PrintWriter(new FileWriter(res,true));
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas);
			writer.close();
			}catch (Exception e){
				//:)
			}
	}


}
