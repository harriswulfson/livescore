Alg : Object
{
	var <>midi, <>mout, <>midiCh, <>routine, <>stopping;
	var <>interfaceMatch;
	var <>pedal; //not sure if this should be in the superclass, but ok.
	var <>startTime;
	var <>file; //added 4/26/2008. SimpleMIDIFile class.
	
	*new
	{
		var object;
		
		object=super.new;
		object.init; //might as well try
		object.initMidi;
		object.initRoutine;
		^object;
	}

	init
	{
		//misc initializations. may override in subclass;
		stopping=0;
		interfaceMatch="IAC";
		
		pedal=0;
	}
	
	startMidi //for convenience. launches SimpleSynth app for general midi.
	{
		Routine(
		{
			unixCmd("open /Applications/MusicSoftware/SimpleSynth.app");
			wait(3); //wait for SimpleSynth to open.
		}).play;
	}

	initMidi
	{
		/*
		arg destIndex;	
		
		if(destIndex == nil, //by default use the SimpleSynth app
		{
			MIDIClient.init;
			MIDIClient.destinations.do(
			{
				arg aDest;
				
				//postln(aDest.device);
				if(aDest.device.contains(interfaceMatch),
				{
					mout = MIDIOut(0, aDest.uid);
					//postln(aDest.device);
				});
			});
		},
		// else
		{
			mout = MIDIOut(0, MIDIClient.destinations.at(destIndex).uid);
		});
		*/
		midi=MIDIManager.new;
		mout=MIDIManager.mout;
		midiCh=0;
	
	}
	
	initRoutine
	{
		//override with Routine definition;
		routine=Routine(
		{
			4.do(
			{
				mout.noteOn(midiCh,60,40);
				wait(1.0);
				mout.noteOff(midiCh,60,40);
			});
		});

	}
	
	start
	{
		stopping=0;
		routine.reset;
		routine.play;
		startTime=SystemClock.seconds;
	}
	
	reset
	{
		routine.reset;
	}
	
	stop
	{
		stopping=1;
	}


	// pedal
	togglePedal
	{
		pedal=abs(pedal - 1);
		mout.control(midiCh, 64, pedal * 127); 
	}
	pedalOn
	{
		pedal=1;
		mout.control(midiCh, 64, pedal * 127); 	
	}
	pedalOff
	{
		pedal=0;
		mout.control(midiCh, 64, pedal * 127); 	
	}


}