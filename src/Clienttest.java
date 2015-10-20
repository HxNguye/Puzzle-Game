import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Clienttest {

	String servername, file_name;
	int port_number;
	public Clienttest(String servername, int port_number, String file_name)
	{
		this.servername = servername;
		this.file_name = file_name;
		this.port_number = port_number;
	}

  public static void main (String [] args ) throws IOException {
	  FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try
		{
			InetAddress server = InetAddress.getByName(servername);
			Socket server_socket = new Socket( server, port_number);
			System.out.println("Connecting to port " + port_number + " and fetching file: " + file_name);
			DataOutputStream os = new DataOutputStream (server_socket.getOutputStream());
			DataInputStream is = new DataInputStream(server_socket.getInputStream());
			os.writeUTF(file_name);

			int size = (int) is.readLong();
			byte [] mybytearray  = new byte [size];
			fos = new FileOutputStream(file_name);
			bos = new BufferedOutputStream(fos);
			int bytesRead = is.read(mybytearray,0,mybytearray.length);
			int current = bytesRead;

			do {
				bytesRead =
						is.read(mybytearray, current, (mybytearray.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(current < size);

			bos.write(mybytearray, 0 , current);
			bos.flush();
			System.out.println("File " + file_name
					+ " downloaded (" + current + " bytes read)");
			bos.close();
			server_socket.close();
		}
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
  }

}