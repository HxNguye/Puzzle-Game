package server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import client.PuzzleClient;


public class PuzzleServer extends Thread
{
	// use this for client connections
	public static HashMap<SelectionKey, PuzzleClient> clientMap = new HashMap<SelectionKey, PuzzleClient>();
	// get list of ports
	HashMap<String, Ports> listOfPorts = Ports.getListeningPortsList();

	private ServerSocketChannel ssc;
	private Selector selector;
	SelectionKey serverKey;

	/**
	 * Default Constructor class
	 */
	public PuzzleServer() throws IOException
	{

	}

// TODO: try to implement this method further

/**
 * Method to send file based on the port number
 */
public void beginListening(int socketDestination) throws IOException
{ 
	// for readiness selection 
	selector = Selector.open();
	for (Object value : listOfPorts.values())
	{
		if (!(value.equals(socketDestination)))
		{
			System.out.println("Port is not available");
		}
		else
		{
			// to listen for incoming connections
			ssc = ServerSocketChannel.open();
			// configure ssc as non-blocking
			ssc.configureBlocking(false);
			// we are only interested in accepted operations
			serverKey = ssc.register(selector, SelectionKey.OP_ACCEPT); 
			// binds ssc to an InetSocketAddress
			ssc.socket().bind(new InetSocketAddress(socketDestination));

			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() ->
			{
				try
				{
					loop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}, 0, 10000, TimeUnit.MILLISECONDS); // 10 seconds for the next execution is called
		}
	}
}// end of sendFileToPort method

/**
 * Helper Method to loop through the SelectionKey handling process used in the beginListening() method
 */
private void loop() throws Exception
{
	while(selector.isOpen())
	{
		try
		{
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
			while (selectedKeys.hasNext())
			{
				SelectionKey selectedKey = selectedKeys.next();

				if (selectedKey.isAcceptable())
				{
					SocketChannel acceptedSocketChannel = ((ServerSocketChannel) selectedKey.channel()).accept();
					// configure acceptedSocketChannel as non-blocking
					acceptedSocketChannel.configureBlocking(false);
					// we are only interested in read operations
					SelectionKey readKey = acceptedSocketChannel.register(selector, SelectionKey.OP_READ);
					// put any connected client in HashMap
					clientMap.put(readKey, new PuzzleClient(readKey, acceptedSocketChannel));
					acceptedSocketChannel.socket();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}



}

