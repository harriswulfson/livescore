
//Run this twice to initiate LiveScore
l=LSDifficult.new;
l=LSDifficult.new;

//Add a client by name
//Run this to connect client. Each Client needs a different voice and address (which is posted on the clients).
//Note. If you send something twice (name or address) you double the notes sent (bad bug)


l.addClient(LSClient.forName("Violin 1"));
l.clients.at(0).connect("160.39.220.141",57110);
l.clients.at(0).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(2).clef);

l.addClient(LSClient.forName("Clarinet"));
l.clients.at(1).connect("209.2.216.34",57110);
l.clients.at(1).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(2).clef);
//
// l.addClient(LSClient.forName("Cello 1"));
// l.clients.at(2).connect("192.168.1.102",57110);
// l.clients.at(2).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(2).clef);
//
// l.addClient(LSClient.forName("Piano 1"));
// l.clients.at(3).connect("192.168.1.102",57110);
// l.clients.at(3).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(2).clef);



//christine
// l.addClient(LSClient.forName("Flute"));
// l.clients.at(0).connect("10.0.0.2",57110);
// l.clients.at(0).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(0).clef);

//casey
// l.addClient(LSClient.forName("Soprano Saxophone"));
// l.clients.at(1).connect("192.168.1.108",57110);
// l.clients.at(1).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(1).clef);

//orin
// l.addClient(LSClient.forName("Violin 1"));
// l.clients.at(2).connect("192.168.1.102",57110);
// l.clients.at(2).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(2).clef);

//eric
// l.addClient(LSClient.forName("Violin 2"));
// l.clients.at(3).connect("192.168.1.104",57110);
// l.clients.at(3).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(3).clef);

//ezra
// l.addClient(LSClient.forName("Viola 1"));
// l.clients.at(4).connect("192.168.1.106",57110);
// l.clients.at(4).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(4).clef);

//heather
// l.addClient(LSClient.forName("Viola 2"));
// l.clients.at(5).connect("192.168.1.107",57110);
// l.clients.at(5).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(5).clef);

//l.addClient(LSClient.forName("Guitar 1"));
//l.addClient(LSClient.forName("Guitar 2"));
//l.addClient(LSClient.forName("Trumpet"));
//l.addClient(LSClient.forName("Sax"));
//l.addClient(LSClient.forName("Violin 1"));
//l.addClient(LSClient.forName("Piano"));

//l.clients.at(0).connect("192.168.1.30",57110);
//l.clients.at(0).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(0).clef);



//Add a client by putting anything in
//Run this to connect client. Each Client needs a different voice and address (which is posted on the clients).
//Note. If you send something twice (name or address) you double the notes sent (bad bug)
//name, transposition=0, clef, lines(5), program (60), low, high, comfyLow, comfyHigh;
// l.addClient(LSClient.new("Flute",0,LSClef.treble,5,74,59,96,60,93));
// l.clients.at(0).connect("192.168.1.4",57110);
// l.clients.at(0).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(0).clef);

//Start
l.start;

//Stop
l.stop;

//Harris had this to initiate fields
l.fields;
l.numFields=10;
l.fieldSize=3;
l.densityCenter=1.9;
l.densityWidth=0.8;
l.durationCenter=0.3;
l.durationWidth=0.8;
l.togetherness=0.0;
l.fieldWidth=24; // 2-20
l.fieldCenter=65; // 60-70
l.generateFields;
l.quantize=0.0;
l.dynamic.put(2,2);
l.densityCenter

//This will make the dynamics work with different numbers of instruments
l.dynamic = Array.fill(5,2);


//for screenshots
//local
l.addClient(LSClient.forName("Violin 1"));
l.clients.at(0).connect("192.168.1.64",57110);
l.clients.at(0).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(0).clef);

//Harris had this to initiate fields
l.fields;
l.numFields=10;
l.fieldSize=3;
l.densityCenter=1.9;
l.densityWidth=0.8;
l.durationCenter=0.2;
l.durationWidth=15;
l.togetherness=0.0;
l.fieldWidth=12; // 2-20
l.fieldCenter=65; // 60-70
l.generateFields;
l.quantize=0.0;
l.dynamic.put(2,2);

l.clients.at(0).addr.sendMsg("/livescore",\cmd,\title,\title,"title",\titleSize,test);


