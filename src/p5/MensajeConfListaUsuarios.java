package p5;

import java.util.Enumeration;

public class MensajeConfListaUsuarios extends Mensaje {

	/**
	 * 
	 */
	private String origen;
	private String destino;
	Enumeration<String> listaUsuarios;
	private static final long serialVersionUID = 1L;

	public MensajeConfListaUsuarios(String ori,String des, Enumeration<String> l) {
		origen=ori;
		destino=des;
		listaUsuarios=l;
	}
	@Override
	public TipoMensaje getTipo() {
		return TipoMensaje.ConfListaUsuarios;
	}

	@Override
	public String getOrigen() {
		return origen;
	}
	
	public String getLista(){
		return listaUsuarios.toString();
	}


	@Override
	public String getDestino() {
		return destino;
	}

}
