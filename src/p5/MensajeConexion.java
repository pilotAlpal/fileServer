package p5;

public class MensajeConexion extends Mensaje {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String origen;
	private String destino;
	private String usuario;
	
	
	public  MensajeConexion(String o,String d, String user) {
		origen=o;
		destino=d;
		usuario=user;
	}
	
	public String getUserName(){return usuario;}
	
	@Override
	public TipoMensaje getTipo() {
		
		return TipoMensaje.Conexion;
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
