// ************************************
// Enter in IP addresses from musicians' clients here
violinIP = "160.39.220.141";
clarinetIP = "160.39.220.141";
celloIP = "160.39.220.141";
pianoIP = "160.39.220.141";
// ************************************


// ************************************
// Run this twice to initiate LiveScore
l = LSDifficult.new;
l = LSDifficult.new;
// ************************************


l.addClient(LSClient.forName("Violin 1"));
l.clients.at(0).connect(violinIP, 57110);
l.clients.at(0).addr.sendMsg("/livescore", \cmd, \clear, \clef, l.clients.at(2).clef);

l.addClient(LSClient.forName("Clarinet"));
l.clients.at(1).connect(clarinetIP, 57110);
l.clients.at(1).addr.sendMsg("/livescore", \cmd, \clear, \clef, l.clients.at(2).clef);

l.addClient(LSClient.forName("Cello 1"));
l.clients.at(2).connect(celloIP, 57110);
l.clients.at(2).addr.sendMsg("/livescore", \cmd, \clear, \clef, l.clients.at(2).clef);

l.addClient(LSClient.forName("Piano 1"));
l.clients.at(3).connect(pianoIP, 57110);
l.clients.at(3).addr.sendMsg("/livescore", \cmd, \clear, \clef, l.clients.at(2).clef);


// ************************************
// Start
l.start;
// ************************************




// Stop
l.stop;

// Harris had this to initiate fields
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

// This will make the dynamics work with different numbers of instruments
l.dynamic = Array.fill(5,2);


// for screenshots
// local
l.addClient(LSClient.forName("Violin 1"));
l.clients.at(0).connect("192.168.1.64",57110);
l.clients.at(0).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(0).clef);

// Harris had this to initiate fields
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
