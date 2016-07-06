package p5;

import java.io.Serializable;

/**
 * Clase Mensaje (abstracta): Sirve como raíz de la jerarquía de mensajes que deberemos
diseñar. Tiene como atributos al tipo, origen y destino del mensaje en cuestión;
y declara al menos los siguientes métodos:
public int getTipo();
public String getOrigen();
public String getDestino();
 * @author PilotAlpal
 *
 */
public abstract class Mensaje implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract TipoMensaje getTipo();
	public abstract String getOrigen();
	public abstract String getDestino();

}
