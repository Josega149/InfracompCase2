package clienteSinSeguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import uniandes.gload.core.*;

public class Generator 
{
	private LoadGenerator generator;
	
	private int numPerdidas;
	
	private long tiempoAutenticacion;
	
	private long tiempoRespuesta;
	

	
	public Generator()
	{
		Task work = createTask();
		int numberOfTasks = MainPruebas.NUMBER_OF_TASKS;
		int gapBetweenTasks = MainPruebas.GAP_BETWEEN_TASKS;
		generator = new LoadGenerator("Client - Server Load Test", numberOfTasks, work,gapBetweenTasks );
		generator.generate();
		generarResultados();
		escribirResultados();
	}
	
	private void generarResultados (){
		long sumaAut = 0;
		long num = MainPruebas.NUMBER_OF_TASKS;
		long sumaCons = 0;
		try{
			//Leo los tiempos
			FileReader fr = new FileReader("./data/tiempos");
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
	
	private void escribirResultados (){
		try{
			File res = new File("./data/resultados");
			PrintWriter writer = new PrintWriter(new FileWriter(res,true));
			writer.println("promAut:"+ tiempoAutenticacion);
			writer.println("promConsulta:"+ tiempoRespuesta);
			writer.println("numPerdidas:" + numPerdidas);
			writer.close();
			}catch (Exception e){
				//:)
			}
	}
	
	public void aumentarPerdidas (){
		numPerdidas++;
	}
	
	private Task createTask()
	{
		return new ClientServerTask();
	}
	
	

}
