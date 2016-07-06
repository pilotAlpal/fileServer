package p5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Clase OyenteCliente: Implementa el interfaz Runnable  o hereda de la clase Thread,y es usada para 
 * proporcionar concurrencia respecto a las sesiones de cada usuario con el servidor. 
 */
public class OyenteCliente extends Thread {
	private Servidor servidor;
	private Socket s; 	
	private ObjectInputStream fIn;
	private ObjectOutputStream fOut;
	private String nombreCliente;
	public OyenteCliente(Socket so,Servidor ser) {
		servidor=ser;
		s=so;
		try {
			fOut=new ObjectOutputStream(s.getOutputStream());
			fIn=new ObjectInputStream(s.getInputStream());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* El método  run()  se limita a hacer lecturas del flujo de entrada correspondiente,
	 *  realizar las acciones oportunas, y devolver los resultados
	 *  en forma de mensajes que serán enviados al usuario o usuarios involucrados.*/
	@Override
	public void run() {
		while (true){
			try {
				Mensaje m= (Mensaje) fIn.readObject();
				TipoMensaje type = m.getTipo();
				String o="",d="";
				switch (type) {
				case Conexion:
					Usuario usr= new Usuario(((MensajeConexion) m).getUserName());
					nombreCliente=usr.getName();
					//-Guardar en tUsuarios info nuevo usuario
					servidor.registra(usr);
					/* -Guardar en tCanales flujo de comunicacion   * */
					servidor.guardaFlujo(usr,fOut);
					o=m.getDestino();d=m.getOrigen();
					MensajeConfirmacion confirmacion = new MensajeConfirmacion(m.getDestino(),m.getOrigen());
					fOut.writeObject(confirmacion);
				break;
				case ListaUsuarios:
					/*
					 * -buscar info en tUsuarios
					 * -mandar msge conf lista usuarios
					 * */
					servidor.enviaListaUsuarios(nombreCliente,d);
				break;
				case EmitirFichero:
					/*
					 * -obtener info usuario que tiene fichero
					 * -mandar mensaje peticion fichero
					 * 
					 * */
				break;
				case PrepComCS:
					/*
					 * mandar mensaje preparar comunicacion SC
					 * */
				break;
				default:
					System.err.println(m);
				break;
				}	
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {	
				e.printStackTrace();
			}
		}
	}
}
