package hemming;

import java.net.*;
import java.util.Random;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.TextField;
import java.io.*;


public class ClientReceiver extends GuiChat
{
		private static final long serialVersionUID = 1L;
	


		Button 	correct	 = new Button("Correct Data & Message");
		

	public void setupConnection() throws IOException
	{

			socket 	=	 new Socket	("localhost", 8080);		//so localHost, cause i'm running program on same comp. use ip address of other comp if not local
																							//client sets up the socket, Server must be ran first, or else you'll get an error	
	}

/*////////////////////////
initialize gui components 
//////////////////////////*/
	
	public void init()
	{
		super.init();
		
		for (int i = 0; i < dTF.length; i++)
		{
			dTF[i]	   = new TextField();
			dTF[i].setBounds(10 + 50 * i,30,40,40);
			add(dTF[i]);
		}
			correct		.setBounds	(230, 30,	150,	30);
		
		
		add(correct);
		correct.addActionListener(this);
		
	}
	
/*//////////////////////////////////////
receives the message, makes a random error
and displays it
////////////////////////////////////*/
	
	public void receiveMessage()
	{
		//super.receiveMessage();
		Random error = new Random();
		int random = error.nextInt(7);
		String message = "";
		try 
		{
			
			for (int i = 0; i < bitsTF.length; i ++)
			{
				message = input.readUTF();
				bitsTF[i].setText(message);
			}
			

			//displaying the data
			String bit = (bitsTF[random].getText());
			
			if(bit.equals("0"))	
			{	
				System.out.println("Error, changing random index from 0 to 1");
				bitsTF[random].setText("1");			
			}
			
			if(bit.equals("1"))
			{
				bitsTF[random].setText("0");
				System.out.println("Error, changing random index from 1 to 0");
			}
						
		} catch (IOException e) {e.printStackTrace();}
		
	
	}
	
/*///////////////////////////////////
 Finds which index the error was made
 //////////////////////////////////*/
	
	public int correctCode()
	{
		int errorIndex = 0;
		if(isEven(bitsTF[0].getText(), bitsTF[2].getText(), bitsTF[4].getText(), bitsTF[6].getText())) 
		{
			errorIndex += 1;
		}
		
		if (isEven(bitsTF[1].getText(), bitsTF[2].getText(), bitsTF[5].getText(), bitsTF[6].getText())) 		
		{
				errorIndex += 2;
		}
	
		
		if(isEven(bitsTF[3].getText(), bitsTF[4].getText(), bitsTF[5].getText(),bitsTF[6].getText())) 
		{
			errorIndex += 4;
		}
		
		
		if (errorIndex != 0)
		{
		return errorIndex-=1;	//if errorIndex is 4, the 4th position is actually the 3rd index in an array. you must - 1. if no error, you return -1.
	
		}
		else
		return errorIndex;
	}
	
/*//////////////////////
paints the string 
//////////////////////*/
	
	public void paint(Graphics g)
	{
		g.drawString(title, 10,20);
		g.drawString(title2, 10, 80);
		
	}

/*///////////////////////////////////////////////////
Corrects the code..."send message" isn't the right name
for this...
 //////////////////////////////////////////////////*/
	
	public void sendMessage() {			//sendMessage isnt the right name for this....	
		///////////correcting the code
		int fixIndex = correctCode();
		
			if	(bitsTF[fixIndex].getText().equals("0"))
				{
					System.out.println("Found error at index " + fixIndex + ", changing it back to 1");
					bitsTF[fixIndex].setText("1");
				}
			else
			{
				
				bitsTF[fixIndex].setText("0");
				System.out.println("Found error at index " + fixIndex + ", changing it back to 0");
			}
		
		//extracting the message from data and displaying the correct message
		dTF[0].setText(bitsTF[2].getText());
		dTF[1].setText(bitsTF[4].getText());
		dTF[2].setText(bitsTF[5].getText());
		dTF[3].setText(bitsTF[6].getText());
		
		
		
	}
	

}
