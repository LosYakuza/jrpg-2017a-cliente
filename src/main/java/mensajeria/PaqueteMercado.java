package mensajeria;

import java.io.Serializable;

public class PaqueteMercado extends Paquete implements Serializable, Cloneable {
	private PaquetePersonaje personaje;
	private int id;
	private boolean quiereIntercambiar;

	public PaqueteMercado(){
		setComando(Comando.MERCADO);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PaquetePersonaje getPaqPersonaje() {
		return personaje;
	}

	public void setPaqPersonaje(PaquetePersonaje personaje) {
		this.personaje = personaje;
	}

	public boolean getQuiereIntercambiar() {
		return quiereIntercambiar;
	}

	public void setQuiereIntercambiar(boolean quiereIntercambiar) {
		this.quiereIntercambiar = quiereIntercambiar;
	}
}
