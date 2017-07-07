package mensajeria;

import java.io.Serializable;

public class PaqueteFinalizarMercado extends Paquete implements Serializable, Cloneable  {

	private int id;

	public PaqueteFinalizarMercado(){
		setComando(Comando.FINALIZARMERCADO);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
