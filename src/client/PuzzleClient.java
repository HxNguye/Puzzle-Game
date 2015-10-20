package client;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;



public class PuzzleClient 
{
	
	private String serverName;
	private String fileName;
	private int portNumber;
	
	SelectionKey selectionKey;
	SocketChannel socketChannel;
	ByteBuffer byteBuffer;
	
	/**
	 * Constructor class
	 */
	public PuzzleClient(SelectionKey newSelectionKey, SocketChannel newSocketChannel) throws Exception
	{
		newSelectionKey = selectionKey;
		newSocketChannel = (SocketChannel) newSocketChannel.configureBlocking(false);// non-blocking
		byteBuffer = ByteBuffer.allocate(1024);
	}
	
	// TODO: Make this usable for multiple socket connections
//	
//	/**
//	 * Method to disconnect from server port
//	 */
//	public void disconnect()
//	{
//		PuzzleServer.clientMap.remove(selectionKey);
//		try
//		{
//			if (selectionKey != null)
//			{
//				selectionKey.cancel();
//			}
//			
//			if (socketChannel == null)
//			{
//				return;
//			}
//			System.out.println("Au revoir...à bientôt... " + socketChannel.getRemoteAddress());
//		}
//		catch (Exception e)
//		{
//			// move along
//		}
//	}
//	
//	/**
//	 * Method to read from the server
//	 */
//	public void read()
//	{
//		try
//		{
//			int count = -1;
//			
//			try
//			{
//				count  = socketChannel.read((ByteBuffer) byteBuffer.clear());
//			}		
//			catch (Exception e)
//			{
//				// move along
//			}
//			if (count == -1)
//			{
//				System.out.println("No more connections...");
//				System.out.println("Preparing to disconnect...");
//				disconnect();
//			}
//			
//			else if (count == 0)
//			{
//				System.out.println("no bytes were read!");
//				return;
//			}
//			else
//			{
//				System.out.println("Sending: " + byteBuffer.position() + "bytes");
//			}
//			
//			byteBuffer.flip();
//			socketChannel.write(byteBuffer);
//			
//		}
//		catch (Exception e)
//		{
//			System.out.println("Preparing to disconnect...");
//			disconnect();
//			e.printStackTrace();
//		}
//	}
//	
//	public void run()
//	{
//		boolean processing = true;
//		BufferedInputStream in = null;
//		while (processing)
//		{
//			try
//			{
//				byte[] byteBuffer = new byte[1024];
//				FileOutputStream fis = new FileOutputStream("Puppy.jpg");
//				in = new BufferedInputStream(in);
//				int count;
//				while ((count = in.read(byteBuffer)) > 0)
//				{
//					fis.write(byteBuffer, 0 , count);
//					fis.flush();
//				}
//				processing = false;
//				fis.close();
//			}
//			
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
	
	

}
