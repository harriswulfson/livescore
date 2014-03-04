LSDifficult : LiveScore
{

	var <>fieldSize;
	var <>fieldCenter;
	var <>fieldWidth;
	var <>stasis;
	var <>noteMulti;
	var <>numFields;
	var <>togetherness;
	var <>densityCenter;
	var <>densityWidth;
//	var <>pulseRate;
//	var <>pulseness;
	var <>quantize;
	var <>durationCenter;
	var <>durationWidth;
	var <>dynamic;
	
	var <>fields;
	var <>min;
	
	
		
	init
	{
		super.init;
		fieldSize=6;
		fieldCenter=67;
		fieldWidth=20;
		numFields=4;
		densityCenter=7.0;
		densityWidth=0.5;
		togetherness=0.4;
//		pulseRate=0.2;
//		pulseness=0.0;
		quantize=0.0;
		durationCenter=1.0;
		durationWidth=0.5;
		dynamic=[2,2,2,2,2,2];

		stasis=0.5;
		noteMulti=2;
		
		//init MIDI input
		MIDIClient.init(2,2);
		2.do({ arg i; 
			MIDIIn.connect(i, MIDIClient.sources.at(i));
		});
		this.startMIDI;
		
		fields=Array.new;
		this.initClients;
	}

	initRoutine
	{
		postln("in method");
		routine=Routine(
		{
			var window;
			var pitches;
			var onsets;
			var durations;
			var currentDynamic=[2,2,2,2,2,2];

			var currentFieldSize;
			var currentFieldCenter;
			var currentFieldWidth;
			var lastFieldUpdate=0;


			postln("in routine");
			while({ stopping==0; },
			{
				if((((fields.size != numFields) ||
					(fieldSize != currentFieldSize) ||
					(fieldCenter != currentFieldCenter) ||
					(fieldWidth != currentFieldWidth)) &&
						(lastFieldUpdate < (SystemClock.seconds - 0.5))),
				{
					this.generateFields;
					lastFieldUpdate = SystemClock.seconds;
					currentFieldSize=fieldSize;
					currentFieldCenter=fieldCenter;
					currentFieldWidth=fieldWidth;
				});
				window=this.centerWidth(densityCenter,densityWidth);
				onsets=Array.fill(clients.size, { rrand(0.0, window * togetherness).round(quantize); });
				durations=Array.fill(clients.size, { this.centerWidth(durationCenter, durationWidth); });
				
				pitches=LSInstrument.voicing(fields.at(rand(fields.size)),clients);
				postln(onsets);
				onsets.do(
				{
					arg onset, idx;
					var dyn=0;
					
					if(dynamic.at(idx) != currentDynamic.at(idx),
					{
						currentDynamic.put(idx,dynamic.at(idx));
						dyn=dynamic.at(idx);
					});
					SystemClock.sched(onset,
					{
					    // midi.note(idx, pitches.at(idx), 90, durations.at(idx));
						if(clients.at(idx).addr != nil,
							{
							//this one line below is a temporary solution for syncing.
							clients.at(idx).addr.sendMsg("/livescore",\cmd,\conductor,\time,SystemClock.seconds - startTime);
//								clients.at(idx).addr.sendMsg("/score/notate","time",
//								SystemClock.seconds - startTime,"duration",durations.at(idx),"pitch",pitches.at(idx) +
//									clients.at(idx).transposition,"dynamic",dyn);
									
								clients.at(idx).addr.sendMsg("/livescore",\cmd,\notate,"time",
								SystemClock.seconds - startTime + 6,"duration",durations.at(idx),"pitch",pitches.at(idx) +
									clients.at(idx).transposition,"dynamic",dyn);
							});
					});
				});
				wait(window);
				
			});				
			
		});
	}

	start
	{
		var lag;

		super.start;
		//should have multiple threads, instead of sticking this here
		
		clients.do(
			{
				arg aClient;
				if(aClient.addr != nil,
				{
					//aClient.addr.sendMsg("/score/conduct");
					aClient.addr.sendMsg("/livescore",\cmd,\clear);
				});
			});

		lag=0;
		Routine(
		{
			wait(lag);
			clients.do(
			{
				arg aClient;
				if(aClient.addr != nil,
				{
					//aClient.addr.sendMsg("/score/conduct");
					aClient.addr.sendMsg("/livescore",\cmd,\conductor,\time,SystemClock.seconds - startTime - lag);
				});
			});
		
			while({ stopping == 0 },
			{
				clients.do(
				{
					arg aClient;
					if(aClient.addr != nil,
					{
						//aClient.addr.sendMsg("/score/sync","time", SystemClock.seconds - startTime - lag);
						wait(1);
					});
				});
			});
			
		}).play;

	}

	generateFields
	{
		postln("new fields");
		fields=Array.fill(numFields,
		{
			Array.fill(fieldSize,
			{
				rrand(fieldCenter - (fieldWidth/2),fieldCenter + (fieldWidth/2));
			});
		});
	}
	
	centerWidth
	{
		arg center, width;
		^(rrand(center - (center * width), center + (center * width)));
	}

	initClients
	{
		//this.addClient(LSClient.forName("Violin"));
		//this.addClient(LSClient.forName("Viola"));
		//this.addClient(LSClient.forName("Flute"));
		//this.addClient(LSClient.forKey("Guitar"));
	}

	startMIDI
	{
	postln("midi started");
		MIDIIn.control = 
		{
			arg src,chan,ctrl,value;
			
			postln([src,chan,ctrl,value]);
	
			if(ctrl == 81, { this.procDensityCenter(value); });
			if(ctrl == 82, { this.procDensityWidth(value); });
			if(ctrl == 83, { this.procFieldSize(value); });
			if(ctrl == 84, { this.procNumFields(value); });
			if(ctrl == 85, { this.procTogetherness(value); });
			if(ctrl == 86, { this.procFieldCenter(value); });
			if(ctrl == 87, { this.procFieldWidth(value); });
			if(ctrl == 88, { this.procQuantize(value); });
			if(ctrl == 89, { this.procDurationCenter(value); });
			if(ctrl == 90, { this.procDurationWidth(value); });

			if(ctrl == 92, { this.procDynamic(0, value); });
			if(ctrl == 93, { this.procDynamic(1, value); });
			if(ctrl == 94, { this.procDynamic(2, value); });
			if(ctrl == 95, { this.procDynamic(3, value); });
			if(ctrl == 96, { this.procDynamic(4, value); });
			if(ctrl == 97, { this.procDynamic(5, value); });
			if(ctrl == 98, { this.procDynamic(6, value); });
			if(ctrl == 92, { this.procDynamic(7, value); });
			//if(ctrl == 93, { this.procDynamic(8, value); });
			//if(ctrl == 94, { this.procDynamic(9, value); });

		};

	}

	stopMIDI
	{
		MIDIIn.control = {};
	}

	procDensityCenter
	{
		arg value;

		//densityCenter=[1.5, 14, 'lin',0.0,6].asSpec.map(value/127);
		densityCenter=(value/127) * 12.5 + 1;
	}
	procDensityWidth
	{
		arg value;

		densityWidth=(value/127) * 0.8;
	}
	procFieldSize
	{
		arg value;

		fieldSize=(value/127) * 10 + 2;
	}
	procNumFields
	{
		arg value;

		numFields=((value/127) * 9 + 1).round.asInteger;
	}
	procTogetherness
	{
		arg value;

		togetherness=(value/127) * 0.8;
	}
	procFieldCenter
	{
		arg value;

		fieldCenter=((value/127) * 10 + 60).round.asInteger;
	}
	procFieldWidth
	{
		arg value;

		fieldWidth=((value/127) * 22 + 2).round.asInteger;
	}
	procDurationCenter
	{
		arg value;

		durationCenter=(value/127) * 10.0 + 0.1;
	}
	procDurationWidth
	{
		arg value;

		durationWidth=(value/127) * 0.75;
	}
	procDynamic
	{
		arg voice, value;

		var dyn;
		
		dyn=(value/127 * 6).trunc.asInteger + 1;
		dynamic.put(voice,dyn);
	}

}


