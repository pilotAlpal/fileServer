package p5;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
/*
 * Acciones b�sicas de la aplicaci�n servidor:
 * 
 * Al iniciarse, leer�a de un fichero �users.txt� la informaci�on de los usuarios registrados
 * en el sistema y todos aquellos datos relativos a �estos que se consideren oportunos.
 * 
 * El servidor atiende de forma concurrente todas las peticiones que realizan los clientes
 * conectados al sistema, en particular:
 * 
 *  � Solicitud de b�squeda de usuarios conectados: 
 *  El servidor realiza una b�squeda en su base de datos y devuelve los resultados obtenidos.
 *	� Solicitud de descarga de un fichero: El servidor se comunica con los dos clientes
	en cuesti�on, gestionando el inicio de la comunicaci�on p2p entre ellos. Una vez
	los clientes establecen conexi�on el servidor se desentiende de la comunicaci�on
	p2p.
	� Fin de sesi�on: Se actualiza apropiadamente la bases de datos (en realidad son
	estructuras de datos).
	
	Clase Servidor : Clase principal de la aplicaci�on servidor. Tendr�a como atributo
principal a una lista de usuarios (instancias de la clase Usuario). El servidor espera
la llegada de peticiones de inicio de sesi�on, y asocia un hilo de ejecuci�on con cada
usuario.

	El servidor tendr� una lista (tabla) con todos los usuarios registrados
 * en el sistema (instancias de la clase Usuario).

	Clase OyenteCliente: Implementa el interfaz �Runnable� o hereda de la clase �Thread�,
y es usada para proporcionar concurrencia respecto a las sesiones de cada
usuario con el servidor. El m�etodo �run()� se limita a hacer lecturas del flujo de
entrada correspondiente, realizar las acciones oportunas, y devolver los resultados
en forma de mensajes que ser�an enviados al usuario o usuarios involucrados.


 * */
public class Servidor {
	private static InetAddress ipServer;
	private static final int portServer=999;
	private static ServerSocket listen;
	private static BufferedReader fServer;
	private static PrintWriter tServer;
	private static Socket s;
	private static Hashtable<String, Usuario> tUsuarios;
	private static Hashtable<String, ObjectOutputStream> tCanales ;
	
	
	
	public Servidor(){
		tUsuarios=new Hashtable<String,Usuario>();
		tCanales=new Hashtable<String,ObjectOutputStream>();
	}
	public static void main(String args[]){
		Servidor ser=new Servidor();
		try {
			listen=new ServerSocket(portServer);
			ipServer=listen.getInetAddress();
		/*	s = listen.accept();
			fServer=new BufferedReader(new InputStreamReader(s.getInputStream()));
			String st = fServer.readLine();//para que sirve este String?
			tServer = new PrintWriter(s.getOutputStream());
			tServer.println("Hola");*/
			while(true){
				s=listen.accept();
				(new OyenteCliente(s,ser)).start();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InetAddress getInetAdress(){
		return ipServer;
	}
	
	public synchronized void guardaFlujo(Usuario usr, ObjectOutputStream fOut) {
		tCanales.put(usr.getName(), fOut);
		
	}
	public synchronized void registra(Usuario usr) {
		tUsuarios.put(usr.getName(), usr);
	}
	public void enviaListaUsuarios(String nombreCliente,String des) {
		Enumeration<String> l = tUsuarios.keys();
		ObjectOutputStream oOs=tCanales.get(nombreCliente);
		MensajeConfListaUsuarios m=new MensajeConfListaUsuarios("ACER", des,l);
		try {
			oOs.flush();
			oOs.writeObject(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
