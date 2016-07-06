package p5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EmisorFichero extends Thread {
	
	private PrintWriter printer;
	@Override
	public void run(){
		try {
			ServerSocket ss=new ServerSocket(333);
			Socket s=ss.accept();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
