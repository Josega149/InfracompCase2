package cliente;


import uniandes.gload.core.*;

public class Generator 
{
	private LoadGenerator generator;
	

	
	public Generator()
	{
		Task work = createTask();
		int numberOfTasks = MainPruebas.NUMBER_OF_TASKS;
		int gapBetweenTasks = MainPruebas.GAP_BETWEEN_TASKS;
		generator = new LoadGenerator("Client - Server Load Test", numberOfTasks, work,gapBetweenTasks );
		generator.generate();
	}
	
	private Task createTask()
	{
		return new ClientServerTask();
	}
	
	

}
