package hemming;

import java.net.*;
import java.awt.*;
import java.io.*;


public class ClientSender extends GuiChat
{
		private static final long serialVersionUID = 1L;
		
		
		Button 				send			 = new Button("send");
	
		
		
		
	public void setupConnection() throws IOException
	{

			socket 	=	 new Socket	("localhost", 8080);		//so localHost, cause i'm running program on same comp. use ip address of other comp if not local
																							//client sets up the socket, Server must be ran first, or else you'll get an error	
	}
	
/*//////////////////////
initialize components
//////////////////////*/
	
	public void init()
	{
		super.init();
		
		for (int i = 0; i < dTF.length; i++)
		{
			dTF[i]	   = new TextField();
			dTF[i].setBounds(10 + 50 * i,30,40,40);
			add(dTF[i]);
		}
		
		
		send		.setBounds	(230, 30,	50,	30);
		
		
		add(send);
		
		send.addActionListener(this);
	}
	
/*/////////////////////////////////////////
sets up parity bits and arrays, sends message
 /////////////////////////////////////////*/
	
	public void sendMessage()
	{
		
		String[] data = new String[4];
		
		for (int i = 0; i < dTF.length; i++)
		{
			data[i] = dTF[i].getText();		
		}
		
		bitsTF[2].setText(data[0]);
		bitsTF[4].setText(data[1]);
		bitsTF[5].setText(data[2]);
		bitsTF[6].setText(data[3]);
		
		displayData();
		//setting up the parity bits so I can send the message
		
		
		for (int i = 0; i < bitsTF.length; i++)
			try 
			{
				output.writeUTF(bitsTF[i].getText());
			} catch (IOException e) {e.printStackTrace();}
	}
	
/*/////////////////////////////////////////
 * finds the paritybits, displays the full code
//////////////////////////////////////////*/
	
	public void displayData()
	{
		if(isEven(bitsTF[2].getText(), bitsTF[4].getText(), bitsTF[6].getText())) 		bitsTF[0].setText("1");		
		else bitsTF[0].setText("0");
		
		if(isEven(bitsTF[2].getText(), bitsTF[5].getText(), bitsTF[6].getText())) 		bitsTF[1].setText("1");		
		else bitsTF[1].setText("0");
		
		if(isEven(bitsTF[4].getText(), bitsTF[5].getText(), bitsTF[6].getText())) 		bitsTF[3].setText("1");		
		else bitsTF[3].setText("0");
	}
	
/*/////////////////////////
draws the strings
//////////////////////////*/
	
	public void paint(Graphics g)
	{
		g.drawString(title, 10, 20);
		g.drawString(title2, 10, 85);
	}

	
	public void receiveMessage() {
		
		
	}
	
}
