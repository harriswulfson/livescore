LiveScore : Alg
{
	var <>clients;
	

	init
	{
		super.init;
		clients=Array.new;
	}
	
	addClient
	{
		arg value;
		clients=clients.add(value);
	}
	
	initRoutine
	{
		routine=Routine(
		{
					
		});
	}
	
}