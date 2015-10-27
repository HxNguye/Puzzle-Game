package server;

import java.util.HashMap;

public class Ports 
{
	private int portNumber;
	
	// use this to hold all the listening ports
	private static HashMap<String, Ports> allListeningPorts;

	public Ports(int port)
	{
		portNumber = port;
		allListeningPorts = new HashMap<String, Ports>(); 
		allListeningPorts.put("4000", PortsFactory.addingsPorts(4000));
	}
	
	public Ports()
	{
		portNumber = 4000;
	}
	
	public int getPort()
	{
		return portNumber;
	}
	
	public static HashMap<String, Ports> getListeningPortsList()
	{
		for (String key : allListeningPorts.keySet())
		{
			System.out.println("Server listening on Port Number: " + key);
		}
		return allListeningPorts;

	}
	
	public Ports addPort(Ports p)
	{
		return PortsFactory.addingsPorts(portNumber);
	}
	
	
	public String toString()
	{
		return "Currently on port number: " + portNumber;

	}

}
