package mensajeria;

import java.io.Serializable;
import java.util.LinkedList;

import dominio.OfertaMercado;

public class PaqueteMercado extends Paquete implements Serializable, Cloneable {
	private PaquetePersonaje personaje;
	private int id;
	private boolean quiereIntercambiar;
	private LinkedList<OfertaMercado> ofertas;

	public PaqueteMercado(){
		setComando(Comando.MERCADO);
		ofertas = new LinkedList<OfertaMercado>();
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

	public void addOferta(int id, int idItem, String nameRequerido, String nameOfertado, int idPersonaje) {
		ofertas.add(new OfertaMercado(id, idItem, nameRequerido, nameOfertado, idPersonaje));
	}

	public LinkedList<OfertaMercado> getOfertas() {
		return ofertas;
	}
}
