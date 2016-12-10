package hemming;

import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public abstract class GuiChat extends Applet implements ActionListener, Runnable
{
	private static final long serialVersionUID = 1L;
	
	
	//Applet stuff

	//Data Bits
	TextField[] bitsTF = new TextField[7];
	TextField[] dTF	  =	 new TextField[4];
	
	
	TextField d1 = new TextField();
	TextField d2 = new TextField();
	TextField d3 = new TextField();
	TextField d4 = new TextField();
	String title = "Message";
	String title2 = "Data";
	
	
	
	//Net Stuff
	DataInputStream input;				//gets input, receives data
	DataOutputStream output;			//sends your data
	Socket 	socket;								//Clients connect through a Socket, in which the stream travels through
	
	
	
/*//////////////////////////////////////////////////////
when making things onto a screen, we have to initialize them	
//////////////////////////////////////////////////////*/	

	public abstract void setupConnection() throws IOException;	//abstract function declares, but has no body. It's up to class extending this to decide what's in it
	
	
	public void init()
	{
		
		
		try
		{
			
			//Since server and client has different ways of setting up connection...
			setupConnection();
			
			input 	= new DataInputStream 	(socket.getInputStream());				//where do i get the date from? the socket!
			output  = new DataOutputStream	(socket.getOutputStream());
			
		}catch(Exception e){e.printStackTrace();}
		
		setLayout(null);																				//I'm telling the OS, "HEY, let me control my own layout, i'll manually place where things go"
		
		
		for (int i = 0; i < dTF.length; i++)
		{
			dTF[i]	   = new TextField();
		}
		
		//setting the bounds...
		for(int i = 0; i < bitsTF.length; i++)
		{
			
			bitsTF[i] = new TextField();
			bitsTF[i].setBounds(10 + 50 * i, 90, 40, 40);
			add(bitsTF[i]);
		}
		
		
		
		Thread t = new Thread(this);							//add a new thread, to where? to this (guiChat) guiChat is a runnable applet.
		t.start();															//remember, t is a reference to a thread, "new Thread" is what creates the Thread object
																				//...which has methods, like start(), 

	}

/*//////////////////////////////////////////////////////
 interface only declares method, we must say what it does.
 Thread runs this function
 /////////////////////////////////////////////////////*/
	public void run()
	{
		while(true)
		{
			
			receiveMessage();		
		
		}
	
	}
/*///////////////////////////////////////////////////
 So when Send button is clicked, OS calls this function
 //////////////////////////////////////////////////*/
	public void actionPerformed(ActionEvent e) 
	{
		
		sendMessage();
		
	}
	
/*/////////////////////////////////
 				Sends message
 /////////////////////////////////*/
	public abstract void sendMessage();
	

/*////////////////////////////
		Receives Message
///////////////////////////*/
	public abstract void receiveMessage();

	
	public boolean isEven(String a, String b, String c)
	{
		
		if ((Integer.parseInt(a) + Integer.parseInt(b) + Integer.parseInt(c)) % 2 == 0) return true;
		else return false;
	}
	
	public boolean isEven(String a, String b, String c, String d)
	{
		
		if ((Integer.parseInt(a) + Integer.parseInt(b) + Integer.parseInt(c) + Integer.parseInt(d)) % 2 == 0) return true;
		else return false;
	}
	
}
