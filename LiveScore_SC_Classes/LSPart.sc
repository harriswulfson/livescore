LSPart
{

	var <>score; //LSServer subclass
	var <>instrument;
	var <>host;
	var <>port;
	var <>osc; //NetAddr connection
	var <>channel; //midi channel
	
	*new
	{
		arg score, instrument, host;
		
		var obj;
		obj = super.new;
		
		//defaults
		obj.port=57110;
		obj.channel=0;
		
		//constructor args
		obj.score = score;
		obj.instrument = instrument;
		obj.host=host;
		obj.connect;
		^obj;
	}
	
	connect
	{
		//arg host, port=57110;
		osc = NetAddr.new(host, port);
	}
	
	
	//Comands
	
	notate
	{
		arg time, pitch=0, dur=0, dynamic=0, instruction="", text="";

		osc.sendMsg("/livescore",\cmd,\notate,\time,time,\pitch,pitch+instrument.transposition,
		\duration,dur,\dynamic,dynamic,\text,text,\instruction,instruction,\part,0);

		score.scoreOsc.sendMsg("/livescore",\cmd,\notate,\time,time,\pitch,pitch+instrument.transposition,
		\duration,dur,\dynamic,dynamic,\text,text,\instruction,instruction,\part,score.parts.indexOf(this));


	}

	clear
	{
		arg clef, lines;
		osc.sendMsg("/livescore",\cmd,\clear,\clef,clef,\lines,lines);
	}

	conductor //move conductor bar to a time. used for staying in sync
	{
		arg time;
		
		osc.sendMsg("/livescore",\cmd,\conductor,\time,time);
	}	

	textBox
	{
		arg header, text;

		osc.sendMsg("/livescore",\cmd,\textbox,\header,header,\text,text);
	}
	
	clearTextBox
	{
		osc.sendMsg("/livescore",\cmd,\cleartextboxes);	
	}
	
	title
	{
		arg title, titleSize=30;
		
		osc.sendMsg("/livescore",\cmd,\title,\title,title,\titleSize,titleSize); //TODO
		score.scoreOsc.sendMsg("/livescore",\cmd,\title,\title,title,\titleSize,titleSize);
	}
	
	asString
	{
		^this.instrument.name;
	}
	

}

