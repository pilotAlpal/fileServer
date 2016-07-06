package p5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Clase OyenteServidor: Implementa el interfaz “Runnable” o hereda de la clase “Thread”,
y ser´a usada para llevar a cabo una escucha continua en el canal de comunicaci´on
con el servidor, en un hilo diferente, proporcion´andose as´ı concurrencia.

 * @author Propietario
 *
 */
public class OyenteServidor extends Thread{
	private Socket myS;
	private ObjectInputStream fIn;
	private ObjectOutputStream fOut;
	private String nombreCliente;
	
	public OyenteServidor(Socket s,String nc) {
		nombreCliente=nc;
		myS=s;
		try {
			fOut=new ObjectOutputStream(myS.getOutputStream());
			fIn=new ObjectInputStream(myS.getInputStream());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		MensajeConexion mConexion;
		try {
			mConexion = new MensajeConexion(InetAddress.getLocalHost().getHostName(), myS.toString(),nombreCliente);
			fOut.reset();
			fOut.writeObject(mConexion);
			fOut.flush();
		
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true){
			try {
				Mensaje m = (Mensaje) fIn.readObject();
				TipoMensaje type=m.getTipo();
				switch (type) {
				case ConfConexion:
					System.out.println(" connected.");
					//informar al cliente de que esta conectado
				break;
				case ConfListaUsuarios:
					//client.print(((MensajeConfListaUsuarios) m).getLista());
				break;
				case PetFichero:
					
				break;
				case PrepComCS:
				break;
				default:
				break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
