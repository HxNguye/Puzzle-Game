package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

//TODO: continue to write more tests

public class PuzzzleServerTest
{
	public void test() throws Exception {
		// start up the server
		ServerRunnable sr = new ServerRunnable();
		Thread serverThread = new Thread(sr);
		serverThread.start();

		// connect the client to the port the server is listening on
		Socket client = new Socket("localhost", sr.getPort());

		// client socket interactions go here
		client.close();
	}


	class ServerRunnable implements Runnable 
	{
		private ServerSocket server;

		public ServerRunnable() throws IOException 
		{
			// 0 means listen on any free port
			server = new ServerSocket(0);
		}

		public void run() 
		{
			try {
				while (true) 
				{
					Socket sock = server.accept();

					// normally you will need to start a thread to handle
					// the new socket so that the server will be able to accept
					// new connections but this may not be necessary for
					// unit testing where only a single connection occurs.

					sock.close();
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		public int getPort() 
		{
			return server.getLocalPort();
		}

	}
}
