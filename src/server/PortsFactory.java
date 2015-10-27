package server;

import java.util.HashMap;

import exceptions.InvalidPortNumberException;

public class PortsFactory 
{
	// use this to hold all the listening ports
	private static HashMap<String, Ports> allListeningPorts = new HashMap<String, Ports>();
	
	public static Ports addingsPorts(String portNumberString) throws InvalidPortNumberException
	{
		int port = PortsFactory.stringToIntConverter(portNumberString);
		return PortsFactory.addingsPorts(port);
		
	}
	
	public static Ports addingsPorts(int portNumber)
	{
		return helperPort(portNumber + "", portNumber);
		
	}
	
	public static int stringToIntConverter(String s) throws InvalidPortNumberException
	{
		if (s == null || s.isEmpty())
		{
			throw new InvalidPortNumberException("Invalid port number: Port cannot be null or 0");
		}
		
		int converted = Integer.parseInt(s);
		return converted;
		
	}
	
	public static Ports helperPort(String portNumberString, int portNumber)
	{
		if (allListeningPorts.containsKey(portNumberString))
		{
			return allListeningPorts.get(portNumberString);
		}
		else
		{
			Ports newPort = new Ports();;
			allListeningPorts.put(portNumberString, newPort);
			return newPort;
		}
		
	}

}
