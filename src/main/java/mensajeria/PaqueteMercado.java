package mensajeria;

import java.io.Serializable;

public class PaqueteMercado extends Paquete implements Serializable, Cloneable {
	private int id;
	private int idEnemigo;

	public PaqueteMercado(){
		setComando(Comando.MERCADO);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEnemigo() {
		return idEnemigo;
	}

	public void setIdEnemigo(int idEnemigo){
		this.idEnemigo = idEnemigo;
	}
}
