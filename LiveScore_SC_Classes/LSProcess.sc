LSProcess
{

	var <>score;
	var <>routine;
	
	*new
	{
		arg score;
		
		var obj;
		obj = super.new;
		
		//constructor args
		obj.score = score;
		^obj;
	}

	init //override
	{
		routine=Routine(
		{
			wait(10);
		});
	}
	
	start
	{
		routine.play;
	}

	stop
	{
		routine.stop;
		routine.reset;
	}

}