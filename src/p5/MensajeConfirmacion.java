package p5;

public class MensajeConfirmacion extends Mensaje {
	
	private String origen;
	private String destino;

	public MensajeConfirmacion(String ori, String des) {
		origen=ori;
		destino=des;
	}

	@Override
	public TipoMensaje getTipo() {
		return TipoMensaje.ConfConexion;
	}

	@Override
	public String getOrigen() {
		// TODO Auto-generated method stub
		return origen;
	}

	@Override
	public String getDestino() {
		// TODO Auto-generated method stub
		return destino;
	}

}
