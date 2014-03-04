MIDIManager : Object
{
	classvar instance;
	
	var <>mout;
	var interfaceMatch;
	var midiCh;
	var pedal;
	
	*new
	{
		var object;
		
		if(instance == nil,
		{
			object=super.new;
			object.init;
			instance=object;
		});
		^instance;
	}
	
	*mout
	{
		^this.new.mout;
	}

	init
	{
		interfaceMatch="IAC";
		pedal=0;
		this.initMidi;
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
		midiCh=0;
	}
	
	note
	{
		arg ch, pitch, velocity, dur, delay=0;
		
		SystemClock.sched(delay,
		{
			mout.noteOn(ch,pitch,velocity);
		});
		SystemClock.sched(delay + dur,
		{
			mout.noteOff(ch,pitch,0);
		});
		
	}
	
	chord
	{
		arg ch, field, vel, dur, distribute=false, flam=false;
		
		field.do(
		{
			arg pitch, idx;
			var channel;
			var offset=0;
			
			if(distribute, { channel = idx + ch; }, { channel = ch; });
			if(flam == true,
			{
				offset=rrand(0.005,0.02);
				wait(offset);		
			});
			this.note(channel, pitch, vel, dur);
		});
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



	//temp. convenience to set up the ensemble in MIDI
	ncp
	{
		var orch;
		
		orch=[56,57,60,68,73,71,70,40,40,41,42,43,11,9,14];
		
		orch.do(
		{
			arg prog, idx;
			mout.program(idx,prog);
		});
	
	}
	
	off //panic button. all notes of on all channels.
	{
		16.do(
		{
			arg ch;
			
			100.do(
			{
				arg idx;
				var pitch;
				pitch=idx+20;
				mout.noteOff(ch,pitch,100);
			});
		});
	}


}