package prom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;



public class Main {
	
	public final static int NUMBER_OF_TASKS=80; //400, 200  y 80

	public final static int GAP_BETWEEN_TASKS=100; //20, 40 y 100
	

	private static int numPerdidas1;
	private static int numPerdidas2;
	private static int numPerdidas3;
	private static int numPerdidas4;
	private static int numPerdidas5;
	private static int numPerdidas6;
	private static int numPerdidas7;
	private static int numPerdidas8;
	private static int numPerdidas9;
	private static int numPerdidas10;
	
	private static long tiempoAutenticacion1;
	private static long tiempoAutenticacion2;
	private static long tiempoAutenticacion3;
	private static long tiempoAutenticacion4;
	private static long tiempoAutenticacion5;
	private static long tiempoAutenticacion6;
	private static long tiempoAutenticacion7;
	private static long tiempoAutenticacion8;
	private static long tiempoAutenticacion9;
	private static long tiempoAutenticacion10;
	
	private static long tiempoRespuesta1;
	private static long tiempoRespuesta2;
	private static long tiempoRespuesta3;
	private static long tiempoRespuesta4;
	private static long tiempoRespuesta5;
	private static long tiempoRespuesta6;
	private static long tiempoRespuesta7;
	private static long tiempoRespuesta8;
	private static long tiempoRespuesta9;
	private static long tiempoRespuesta10;
	
	public static void main(String[] args)
	{
		generarResultados();
		escribirResultados();
	}
	
	private static void generarResultados (){
		long sumaAut1 = 0;
		long sumaAut2 = 0;
		long sumaAut3 = 0;
		long sumaAut4 = 0;
		long sumaAut5 = 0;
		long sumaAut6 = 0;
		long sumaAut7 = 0;
		long sumaAut8 = 0;
		long sumaAut9 = 0;
		long sumaAut10 = 0;
		int num = NUMBER_OF_TASKS;
		long sumaCons1 = 0;
		long sumaCons2 = 0;
		long sumaCons3 = 0;
		long sumaCons4 = 0;
		long sumaCons5 = 0;
		long sumaCons6 = 0;
		long sumaCons7 = 0;
		long sumaCons8 = 0;
		long sumaCons9 = 0;
		long sumaCons10 = 0;
		int n = 0;
		try{
			//Leo los tiempos
			FileReader fr = new FileReader("../ClienteSinSeguridad/data/tiempos");
			BufferedReader br = new BufferedReader(fr);
			String banana = br.readLine();
			while (banana!= null){
				String [] arreglito = banana.split(":");
				if (arreglito[0].equals("tAutenticación")){
					if(n<2*num)
						sumaAut1+=Integer.parseInt(arreglito[1]);
					else if (n<2*2*num)
						sumaAut2+=Integer.parseInt(arreglito[1]);
					else if (n<3*2*num)
						sumaAut3+=Integer.parseInt(arreglito[1]);
					else if (n<4*2*num)
						sumaAut4+=Integer.parseInt(arreglito[1]);
					else if (n<5*2*num)
						sumaAut5+=Integer.parseInt(arreglito[1]);
					else if (n<6*2*num)
						sumaAut6+=Integer.parseInt(arreglito[1]);
					else if (n<7*2*num)
						sumaAut7+=Integer.parseInt(arreglito[1]);
					else if (n<8*2*num)
						sumaAut8+=Integer.parseInt(arreglito[1]);
					else if (n<9*2*num)
						sumaAut9+=Integer.parseInt(arreglito[1]);
					else if (n<10*2*num)
						sumaAut10+=Integer.parseInt(arreglito[1]);
				}
					
				else if (arreglito[0].equals("falla")){
					if(n<2*num)
						numPerdidas1++;
					else if (n<2*2*num)
						numPerdidas2++;
					else if (n<3*2*num)
						numPerdidas3++;
					else if (n<4*2*num)
						numPerdidas4++;
					else if (n<5*2*num)
						numPerdidas5++;
					else if (n<6*2*num)
						numPerdidas6++;
					else if (n<7*2*num)
						numPerdidas7++;
					else if (n<8*2*num)
						numPerdidas8++;
					else if (n<9*2*num)
						numPerdidas9++;
					else if (n<10*2*num)
						numPerdidas10++;
				}
					
				else{
					if(n<2*num)
						sumaCons1+=Integer.parseInt(arreglito[1]);
					else if (n<2*2*num)
						sumaCons2+=Integer.parseInt(arreglito[1]);
					else if (n<3*2*num)
						sumaCons3+=Integer.parseInt(arreglito[1]);
					else if (n<4*2*num)
						sumaCons4+=Integer.parseInt(arreglito[1]);
					else if (n<5*2*num)
						sumaCons5+=Integer.parseInt(arreglito[1]);
					else if (n<6*2*num)
						sumaCons6+=Integer.parseInt(arreglito[1]);
					else if (n<7*2*num)
						sumaCons7+=Integer.parseInt(arreglito[1]);
					else if (n<8*2*num)
						sumaCons8+=Integer.parseInt(arreglito[1]);
					else if (n<9*2*num)
						sumaCons9+=Integer.parseInt(arreglito[1]);
					else if (n<10*2*num)
						sumaCons10+=Integer.parseInt(arreglito[1]);
				}
					
				n++;
				banana = br.readLine();
				
			}
			tiempoAutenticacion1 = num!=numPerdidas1? sumaAut1 / (num-numPerdidas1):-1;
			tiempoAutenticacion2 = num!=numPerdidas2? sumaAut2 / (num-numPerdidas2):-1;
			tiempoAutenticacion3 = num!=numPerdidas3? sumaAut3 / (num-numPerdidas3):-1;
			tiempoAutenticacion4 = num!=numPerdidas4? sumaAut4 / (num-numPerdidas4):-1;
			tiempoAutenticacion5 = num!=numPerdidas5? sumaAut5 / (num-numPerdidas5):-1;
			tiempoAutenticacion6 = num!=numPerdidas6? sumaAut6 / (num-numPerdidas6):-1;
			tiempoAutenticacion7 = num!=numPerdidas7? sumaAut7 / (num-numPerdidas7):-1;
			tiempoAutenticacion8 = num!=numPerdidas8? sumaAut8 / (num-numPerdidas8):-1;
			tiempoAutenticacion9 = num!=numPerdidas9? sumaAut9 / (num-numPerdidas9):-1;
			tiempoAutenticacion10 = num!=numPerdidas10? sumaAut10 / (num-numPerdidas10):-1;
			
			tiempoRespuesta1 = num!=numPerdidas1? sumaCons1 / (num-numPerdidas1):-1;
			tiempoRespuesta2 = num!=numPerdidas2? sumaCons2 / (num-numPerdidas2):-1;
			tiempoRespuesta3 = num!=numPerdidas3? sumaCons3 / (num-numPerdidas3):-1;
			tiempoRespuesta4 = num!=numPerdidas4? sumaCons4 / (num-numPerdidas4):-1;
			tiempoRespuesta5 = num!=numPerdidas5? sumaCons5 / (num-numPerdidas5):-1;
			tiempoRespuesta6 = num!=numPerdidas6? sumaCons6 / (num-numPerdidas6):-1;
			tiempoRespuesta7 = num!=numPerdidas7? sumaCons7 / (num-numPerdidas7):-1;
			tiempoRespuesta8 = num!=numPerdidas8? sumaCons8 / (num-numPerdidas8):-1;
			tiempoRespuesta9 = num!=numPerdidas9? sumaCons9 / (num-numPerdidas9):-1;
			tiempoRespuesta10 = num!=numPerdidas10? sumaCons10 / (num-numPerdidas10):-1;
			br.close();
		}catch(Exception e){
			
		}
	}
				
	private static void escribirResultados (){
		try{
			File res = new File("../ClienteSinSeguridad/data/resultados");
			PrintWriter writer = new PrintWriter(new FileWriter(res,false));
			writer.println("--------------------GRUPO 1------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion1);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta1);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas1);
			writer.println("--------------------GRUPO 2------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion2);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta2);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas2);
			writer.println("---------------------GRUPO 3-----------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion3);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta3);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas3);
			writer.println("-------------------GRUPO 4-------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion4);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta4);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas4);
			writer.println("--------------------GRUPO 5------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion5);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta5);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas5);
			writer.println("--------------------GRUPO 6------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion6);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta6);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas6);
			writer.println("--------------------GRUPO 7------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion7);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta7);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas7);
			writer.println("---------------------GRUPO 8-----------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion8);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta8);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas8);
			writer.println("------------------GRUPO 9--------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion9);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta9);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas9);
			writer.println("-------------------GRUPO 10-------------------------");
			writer.println("tAutenticación con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+  tiempoAutenticacion10);
			writer.println("tRespuesta con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ tiempoRespuesta10);
			writer.println("numPerdidas con "+NUMBER_OF_TASKS+" clientes "
					+ "y ramp-up de "+GAP_BETWEEN_TASKS+": "
					+ numPerdidas10);
			writer.close();
			}catch (Exception e){
				//:)
			}
	}


}
