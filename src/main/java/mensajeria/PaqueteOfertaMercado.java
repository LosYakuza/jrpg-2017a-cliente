package mensajeria;

import java.io.Serializable;
import java.util.LinkedList;

import dominio.OfertaMercado;

public class PaqueteOfertaMercado extends Paquete implements Serializable, Cloneable  {
	private LinkedList<OfertaMercado> ofertas;

	public PaqueteOfertaMercado(){
		ofertas = new LinkedList<OfertaMercado>();
	}

	public void addOferta(int id, int idItem, String nameRequerido, int idPersonaje) {
		ofertas.add(new OfertaMercado(id, idItem, nameRequerido, idPersonaje));
	}

	public LinkedList<OfertaMercado> getOfertas() {
		return ofertas;
	}
}
