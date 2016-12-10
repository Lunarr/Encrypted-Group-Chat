package hemming;

import java.io.*;
import java.net.*;

public class ChatServer 
{
	
/*////////////////////////////////////////
Server will be able to connect multiple clients
////////////////////////////////////////*/
	
	int count = 0;																			//inner class can use this
	
	DataInputStream 	    input[]	 = new DataInputStream[10];
	DataOutputStream output[]	 = new DataOutputStream[10];
	
	ClientHandler[] clientH = new ClientHandler[10];

/*////////////////////////////////////
 				main function
///////////////////////////////////*/
	public static void main (String args[])
	{
					
		ChatServer chatServer = new ChatServer();			//create chatserver object
		chatServer.ServerClients();										//call ServerClient
		
	}
	
/*///////////////////////////////
 Connects all Clients
//////////////////////////////*/
	public void ServerClients() 
	{
			
		try{
		
			ServerSocket server = new ServerSocket(8080);									//client sets up the socket, Server must be ran first, or else you'll get an error
		
			for (int i = 0; i <10; i++)
			{
				Socket socket = server.accept();															//waits for client(s) to connect, sets up socket(s) once connected
				
				input	  	[i] = new DataInputStream(socket.getInputStream());					//input and output streams go through socket(s)
				output	[i] = new DataOutputStream(socket.getOutputStream());			
				
				clientH	[i]	 = new ClientHandler(socket);		
				
			}
	
		}catch(Exception e){e.printStackTrace();}

	}
	
	
	
/*/////////////////////////////////////////////////////////////////
 Inner Class, has access to declarations/instance variables of outer class
 Class within a class, you can make objects here as well
 /////////////////////////////////////////////////////////////////*/
	

public class ClientHandler implements Runnable
{
	
	DataInputStream 	    input;	 
	DataOutputStream output;
	int n;										//we're using n to choose the different sockets, to easily refer to them. It's like we're giving...
												//...clients an ID (just Change n to ID?)
		
/*/////////////////////////////////////////////////////////////////
 Give me a socket that connects me to the client, i'll setup the streams
////////////////////////////////////////////////////////////////*/
		
	public ClientHandler(Socket socket) throws IOException
	{
		
		n = count++;
		
		input  	=	 new DataInputStream(socket.getInputStream());					//set up input/output streams
		output	=    new DataOutputStream(socket.getOutputStream());
		Thread t = new Thread(this);															//ok so each client will have its own thread
		t.start();																					//remember, they all wait to receive messages, but we don't have to stop doing everything
		
	}
		
/*/////////////////////////////
Recieves and sends messages 
////////////////////////////*/
	public void run()
	{
		
		while (true)
		{
			
			try
			{	
					
				String message = input.readUTF();				//we're going to send message to all our clients, so recieve message...			
			
				for (int i = 0; i < count; i++)
				{
					
					if (i!=n)
					
					{
						clientH[i].output.writeUTF(message);			//....and send it to all clients, except the one that sent it
					}
				
				}
			
			}catch(Exception e){e.printStackTrace();}				//in for loop?....will it matter?
				
		}
		//end of while
			
	}
	//end of run

}
//end of inner class
}
//end of outer
