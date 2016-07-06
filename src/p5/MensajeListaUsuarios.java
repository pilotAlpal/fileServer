package p5;

public class MensajeListaUsuarios extends Mensaje {

	/**
	 * 
	 */
	private String origen;
	private String destino;
	private static final long serialVersionUID = 1L;

	public MensajeListaUsuarios(String ori,String des) {
		origen=ori;
		destino=des;
	}
	@Override
	public TipoMensaje getTipo() {
		return TipoMensaje.ListaUsuarios;
	}

	@Override
	public String getOrigen() {
		
		return origen;
	}

	@Override
	public String getDestino() {
		
		return destino;
	}
	
	
}
