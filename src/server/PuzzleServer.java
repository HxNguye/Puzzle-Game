package server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import client.PuzzleClient;


public class PuzzleServer extends Thread
{
	// use this to hold all the listening ports
	private ArrayList<Integer> ports = new ArrayList<Integer>();
	
	// use this for client connections
	private static HashMap<SelectionKey, PuzzleClient> clientMap = new HashMap<SelectionKey, PuzzleClient>();

	private ServerSocketChannel ssc;
	private Selector selector;
	SelectionKey serverKey;

	private FileInputStream fileInputStream = null;
	private BufferedInputStream bufferedInputStream = null;
	private OutputStream outputStream = null;

	
	/**
	 * Default Constructor class
	 */
	public PuzzleServer() throws IOException
	{

	}

	/**
	 * Method to add ports for the server to listen to
	 * @param int port
	 */
	public void addListeningPort(int port)
	{
		ports.add(port);
	}


	/**
	 * Method to get the list of all the ports listening on the server
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getListeningPortsList()
	{
		for (Integer i : ports)
		{
			System.out.println("Server listening on Port Number: " + i);
		}
		return ports;
	}

	
	/**
	 * Method to select a file to prepare to be transferred to the client
	 */
	public void selectFile(String filePath) throws IOException
	{
		try
		{
			File myFile = new File (filePath);
			byte [] mybytearray  = new byte [(int) myFile.length()];
			fileInputStream = new FileInputStream(myFile);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			bufferedInputStream.read(mybytearray, 0, mybytearray.length);
			
			// TODO: try to fix this
			outputStream = ssc.socket().accept().getOutputStream();
			
			System.out.println("Sending " + filePath + "(" + mybytearray.length + " bytes)");
			outputStream.write(mybytearray, 0, mybytearray.length);
			outputStream.flush();
			System.out.println("Done!");
		}
		finally 
		{
			if (bufferedInputStream != null) 
			{
				bufferedInputStream.close();
			}
			if (outputStream != null) 
			{
				outputStream.close();
			}
			if (ssc != null) 
			{
				ssc.close();
			}
		}
	}// end of selectFile method



// TODO: try to implement this method further
// TODO: Make this usable for multiple socket connections
/**
 * Method to send file based on the port number
 */
public void sendFileToPort(int socketDestination) throws IOException
{ 
	// for readiness selection 
	selector = Selector.open();
	for (int port : ports)
	{
		if (ports.get(port) != socketDestination)
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

//			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() ->
//			{
				try
				{
					loop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

//			}, 0, 10000, TimeUnit.MILLISECONDS); // 10 seconds for the next execution is called
		}
	}
}// end of sendFileToPort method

/**
 * Helper Method to loop through the SelectionKey handling process used in the sendFileToutputStreamocket method
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

