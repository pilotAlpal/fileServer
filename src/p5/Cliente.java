package p5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	/*
	 * Implementa una aplicación capaz de gestionar y llevar a cabo el intercambio de ficheros
	 * entre máquinas remotas. 
	 * Se trata de un híbrido entre arquitectura cliente-servidor y peer-to-peer, 
	 * es decir, el intercambio de ficheros se realiza directamente entre los propios clientes,
	 * y el servidor únicamente actúa proporcionando información acerca de qué ficheros
	 * hay disponibles en el sistema y quiénes son los propietarios.
	 *  
	 * Clase Cliente: Clase principal de la aplicación cliente. 
	 * Tendrá al menos los siguientes
	 * atributos: nombre de usuario, dirección ip de la máquina. Por comodidad, conviene
	 * tener también como atributos a los objetos que proporcionan la comunicación con el
	 * servidor (socket y flujos). Es responsable de llevar a cabo todas las comunicaciones
	 * con el servidor, y cuando sea necesario ejecutar el env´ıo o recepci´on de ficheros.
	 * Además ofrece el soporte para la interacci´on con el usuario del sistema.

	 * */
	
	private static String nombre;
	private InetAddress ipCliente;
	private  Socket s;
	private  BufferedReader tCliente;
	private  PrintWriter fCliente;
	private static Usuario usr;
	
		public Cliente(String n){
			nombre=n;
			usr=new Usuario(nombre);
			try {
				s= new Socket("ACER", 999);//¿Cómo obtengo la ip del Servidor?  192.168.1.101
				fCliente=new PrintWriter(s.getOutputStream());
				tCliente=new BufferedReader(new InputStreamReader(s.getInputStream()));
				ipCliente=InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	private Socket getSocket(){return s;}
	
	private void sendMessage(Mensaje m){
		fCliente.flush();
		fCliente.println(m);
	}

	public static void main(String args[]){
			
//		 Al iniciar la aplicación se pregunta al usuario por su nombre
			Scanner teclado=new Scanner(System.in);
			System.out.println("Cual es tu nombre de usuario?");
			String nom=teclado.nextLine();		
			try {
				String origen=InetAddress.getLocalHost().getHostName();
				String destino="ACER";
			
				Cliente c=new Cliente(nom);
				//ATENDER PETICIONES DEL SERVIDOR
				(new OyenteServidor(c.getSocket(),nom)).start();
				//por qué no guardar referencia al OyenteServidor?
				//¿Cómo lo podría usar para pasar mensajes sin esa referencia?
			//	System.out.println(nom+" connected.");
				while (true){
				// Una vez iniciada la sesion, el cliente  puede realizar 2 tipos de consultas   
					System.out.println("Que deseas hacer?");
				//conocer el nombre	de todos los usuarios conectados
					System.out.println("1) Mostrar lista usuarios");
				//o descargar ficheros.
					System.out.println("2) Pedir fichero");
					System.out.println("3) Cerrar conexión");	
					int op = teclado.nextInt();
					switch (op) {
					case 1://mostrar lista usuarios
						c.sendMessage(new MensajeListaUsuarios(origen, destino));
					break;
					case 2:
 /* Una vez el usuario elija el fichero a descargar, comenzará el proceso de descarga
 *(en realidad se descarga directamente de la máquina del usuario propietario) de tal
 *forma que el programa cliente siga su curso natural, y en particular permitiendo que
 *se realicen otras consultas e incluso otras descargas mientras continúa la descarga
 *del primer fichero (concurrencia).*/
						System.out.println("Que fichero quieres?");
						String nombreFichero = teclado.nextLine();
					break;
					case 3:
						c.getSocket().close();
					break ;
					default:
						System.err.println("Opción inválida");
					break;
					}
				}
			} 
			catch (IOException e) {
				
				e.printStackTrace();
			}

			/* Al margen de la voluntad del usuario, el programa cliente puede actuar como emisor
			 * de cualquiera de sus archivos compartidos, como propietario de un fichero que otro
			 * cliente solicite. Esta acci´on ser´a llevada a cabo en un segundo plano permitiendo al
			 * usuario continuar con el uso normal de la aplicación.*/
			
			
		 /* Al terminar la aplicación se deberá comunicar el fin de sesión al servidor, 
		 * y permitir así que éste actualice apropiadamente su base de datos.*/
		teclado.close();
	}

	public String getName(){return nombre;}
	
	public String getIp(){return ipCliente.getHostAddress();}

	public void print(String string) {
		System.out.println(string);
	}

}
