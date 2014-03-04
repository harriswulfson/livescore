LSClient : LSInstrument
{
	var <>addr;

	connect
	{
		arg ip, port;
		
		addr=NetAddr.new(ip,port);
	}
}

