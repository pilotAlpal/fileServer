package p5;
/**
 * Clase Usuario: Guarda informaci�n para un usuario registrado en el sistema. Tendr� al
 * menos los siguientes atributos: identificador de usuario, direcci�n ip y lista de ficheros
 * compartidos. 

 * @author Propietario
 *
 */
public class Usuario {
	private String nombre;

	public String getName(){return nombre;}

	public Usuario(String nombreUsuario) {
		nombre=nombreUsuario;
	}

}
