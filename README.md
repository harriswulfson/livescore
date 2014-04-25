# LiveScore

by Harris Wulfson

maintained by Michael Winter

## Instructions

### Set up server

    git clone git@github.com:harriswulfson/livescore.git
    cd livescore
    ln -s LiveScore_SC_Classes /Applications/SuperCollider/SuperCollider.app/Contents/Resources/SCClassLibrary


### Set up controller

TODO

### Configuration

For each instrument, put a block like the following in livescore_server.scd:

    l.addClient(LSClient.forName("<Instrument Name>"));
    l.clients.at(<Instrument number>).connect("<IP Address>",57110);
    l.clients.at(<Instrument number>).addr.sendMsg("/livescore",\cmd,\clear,\clef,l.clients.at(<Clef Type>).clef);

Instrument Name: Look in LiveScore_SC_Classes/LSInstrument.sc to see a list of predefined instruments and get their names.
Instrument Number: Counting up from 0
IP Address: From the client on the musician's computer
Clef Type: Look in LiveScore_SC_Classes/LSClef.sc to see a list of predefined clefs and get their type numbers.

### Set up computers

1. Connect all performers' computers and the serving computer to a router
2. Copy the livescore_client.jar file to each of the performers' computers
3. Open livescore_server.scd on the serving computer
4. Start livescore_client.jar on the performers' computers

### Run

To execute a command in SuperCollider, highlight the lines in the SuperCollider editor window and press cmd-enter. Execute lines in this order:

1.
    l=LSDifficult.new;
    l=LSDifficult.new;

2. Each instrument line block

3.
    l.start;
