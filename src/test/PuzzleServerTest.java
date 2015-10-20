package test;

import server.PuzzleServer;

public class PuzzleServerTest 
{

	public static void main(String[] args) throws Exception 
	{
		PuzzleServer pz = new PuzzleServer();
		pz.addListeningPort(4000);
		pz.addListeningPort(4001);
		pz.addListeningPort(6000);

		pz.getListeningPortsList();
		
		for (Integer i : pz.getListeningPortsList())
		{
			if (i == 4000)
			{
				pz.selectFile("Puppy.jpg");
			}
		}
		
		
		

	}

}
