package clienteSinSeguridad;

import uniandes.gload.core.*;

public class Generator 
{
	private LoadGenerator generator;
	
	
	public Generator()
	{
		Task work = createTask();
		int numberOfTasks = 100;// 400, 200 y 80
		int gapBetweenTasks = 1000; // 20, 40 y 100
		generator = new LoadGenerator("Client - Server Load Test", numberOfTasks, work,gapBetweenTasks );
		generator.generate();
	}
	
	private Task createTask()
	{
		return new ClientServerTask();
	}
	
	public static void main(String[] args)
	{
		Generator gen = new Generator();
	}

}
