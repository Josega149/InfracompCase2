package clienteSinSeguridad;

import uniandes.gload.core.*;

public class Generator 
{
	private LoadGenerator generator;
	
	
	public Generator()
	{
		Task work = createTask();
		int numberOfTasks = 100;
		int gapBetweenTasks = 1000;
		generator = new LoadGenerator("Client - Server Load Test", numberOfTasks, work,gapBetweenTasks );
		generator.generate();
	}
	
	private Task createTask()
	{
		return ClientServerTask();
	}
	
	public static void main(String[] args)
	{
		Generator gen = new Generator();
	}

}
