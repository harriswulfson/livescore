LSInstrument
{

	classvar <lookup;

	var <>name;
	var <>transposition;
	var <>clef;
	var <>lines; //for speakers
	var <>program; //midi program #

	var <>low;
	var <>high;
	var <>comfyLow;
	var <>comfyHigh;

	*new
	{
		arg name, transposition=0, clef, lines, program, low, high, comfyLow, comfyHigh;
		var obj;

		obj=super.new;
		obj.name=name;
		obj.transposition=transposition;
		obj.clef=clef;
		obj.lines=lines;
		obj.program=program;
		obj.low=low;
		obj.high=high;
		obj.comfyLow=comfyLow;
		obj.comfyHigh=comfyHigh;

		//always add to lookup table
		if(lookup == nil, { lookup=Dictionary.new; });
		lookup.put(name, obj);

		^obj;
	}

	*forName
	{
		arg name;

		if(lookup == nil, { this.initLookup; });
		^lookup.at(name);
	}

	*initLookup
	{

		if(lookup == nil, { lookup=Dictionary.new; });

		//TODO: add greater registral detail (comfortable, extreme)
		this.new("Flute",0,LSClef.treble,5,74,59,96,60,93); //59,98
		this.new("Oboe",0,LSClef.treble,5,69,58,91,58,88);
		this.new("Clarinet",2,LSClef.treble,5,71,50,84,50,84);
		this.new("Sax",14,LSClef.treble,5,71,50,84,50,84);
		this.new("Bassoon",0,LSClef.bass,5,71,34,75,34,70);

		this.new("Horn",7,LSClef.bass,5,61,31,77,41,77); //TODO correct values?
		this.new("Trumpet",2,LSClef.treble,5,56,52,80,58,80);
		this.new("Trombone",0,LSClef.bass,5,57,40,70,40,67);
		this.new("Bass Trombone",0,LSClef.bass,5,58,34,70,34,67);
		this.new("Tuba",0,LSClef.bass,5,59,24,67,24,67);

		this.new("Celesta",0,LSClef.treble,5,9,48,96,48,96+12);
		this.new("Crotales",-12,LSClef.treble,5,10,72,96,72,96); //TODO correct values
		this.new("Vibraphone",0,LSClef.treble,5,12,53,89,53,89);
		this.new("Tubular Bells",0,LSClef.treble,5,15,60,77,60,77);

		this.new("Violin 1",0,LSClef.treble,5,41,55,100,55,88); //84 was 96
		this.new("Violin 2",0,LSClef.treble,5,41,55,100,55,88); //84 was 96
		this.new("Violin 3",0,LSClef.treble,5,41,55,100,55,88); //84 was 96
		this.new("Violin 4",0,LSClef.treble,5,41,55,100,55,88); //84 was 96
		this.new("Viola 1",0,LSClef.alto,5,42,48,93,48,88); //A6 too high?
		this.new("Viola 2",0,LSClef.alto,5,42,48,93,48,88); //A6 too high?
		this.new("Viola 3",0,LSClef.alto,5,42,48,93,48,88); //A6 too high?
		this.new("Viola 4",0,LSClef.alto,5,42,48,93,48,88); //A6 too high?
		this.new("Cello 1",0,LSClef.bass,5,43,36,79,36,74);
		this.new("Cello 2",0,LSClef.bass,5,43,36,79,36,74);
		this.new("Cello 3",0,LSClef.bass,5,43,36,79,36,74);
		this.new("Cello 4",0,LSClef.bass,5,43,36,79,36,74);
		this.new("Double Bass 1",12,LSClef.bass,5,44,28,70,28,60); //too high. maybe 60
		this.new("Double Bass 2",12,LSClef.bass,5,44,28,70,28,60); //too high. maybe 60
		this.new("Double Bass 3",12,LSClef.bass,5,44,28,70,28,60); //too high. maybe 60
		this.new("Double Bass 4",12,LSClef.bass,5,44,28,70,28,60); //too high. maybe 60

		this.new("Soprano Saxophone",2,LSClef.treble,5,62,56,87,56,87);
		this.new("Alto Saxophone",9,LSClef.treble,5,63,49,81,49,81);
		this.new("Tenor Saxophone",2,LSClef.treble8,5,64,44,77,44,77);
		this.new("Baritone Saxophone",9,LSClef.bass,5,65,36,69,36,69);

		this.new("Piano 1",0,LSClef.bass,5,0,36,67,36,67); //left hand
		this.new("Piano 2",0,LSClef.bass,5,0,36,67,36,67); //left hand
		this.new("Guitar 1",0,LSClef.treble8,5,24,40,76,40,76);
		this.new("Guitar 2",0,LSClef.treble8,5,24,40,76,40,76);

		this.new("Speaker",0,LSClef.perc,1,74,0,0,0,0); //no pitches
		this.new("Voice",0,LSClef.treble,5,74,55,80,60,80); //TODO program

	}

	*voiceMap
	{
		arg chord, instruments;
		var map, tries, failed=0;

		map=[]; tries=0;
		while({ (map.size < chord.size) && (tries < 1000) },
		{
			map=[];
			chord.do(
			{
				arg pitch;
				var usable, tries;

				usable=[];
				instruments.do(
				{
					arg anInstrument, iidx;
					if((anInstrument.inBounds(pitch)) && (map.includes(iidx) == false),
					{
						usable=usable.add(iidx);
					});
				});
				if(usable.size > 0,
				{
					map=map.add(usable.choose);
				},
				//else
				{
					map=map.add(nil);
					failed=failed+1;
				});
			});
			tries=tries+1;
		});
		if(failed>0,
		{
			postln("Could not voice " ++ failed ++ " of the " ++ chord.size ++ " pitches.");
		},
		{
			postln("Success");
		});
		^map;
	}

	*voicing
	{
		arg chord, instruments;
		var map, result;
		map=this.voiceMap(chord, instruments);
		result=Array.fill(instruments.size, { nil; });
		map.do(
		{
			arg iidx, idx;
			if(iidx != nil, { result.put(iidx,chord.at(idx)); });
		});
		^result;
	}

	inBounds
	{
		arg pitch;
		^((pitch >= low) && (pitch <= high));
	}

	inComfyBounds
	{
		arg pitch;
		^((pitch >= comfyLow) && (pitch <= comfyHigh));
	}

	randomRegistrate
	{
		//quick n' dirty here
		arg pc;

		var choices = [];
		var oct = 0;
		var pitch;

		10.do(
		{
			arg anOct;
			pitch = (anOct * 12) + pc;
			if(this.inComfyBounds(pitch),
			{
				choices = choices.add(pitch);
			});
		});
		^choices.choose;

	}


}