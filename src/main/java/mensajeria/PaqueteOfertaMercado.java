package mensajeria;

import java.io.Serializable;
import java.util.LinkedList;

import dominio.OfertaMercado;

public class PaqueteOfertaMercado extends Paquete implements Serializable, Cloneable  {
	private LinkedList<OfertaMercado> ofertas;
	private int idPjQueQuiereElItem;

	public PaqueteOfertaMercado(){
		ofertas = new LinkedList<OfertaMercado>();
	}

	public void addOferta(int id, int idItem, String nameRequerido, String nameOfrecido, int idPersonaje) {
		ofertas.add(new OfertaMercado(id, idItem, nameRequerido, nameOfrecido, idPersonaje));
	}

	public LinkedList<OfertaMercado> getOfertas() {
		return ofertas;
	}

	public void setOfertas(LinkedList<OfertaMercado> ofertas) {
		this.ofertas = ofertas;
	}

	public int getIdPjQueQuiereElItem() {
		return idPjQueQuiereElItem;
	}

	public void setIdPjQueQuiereElItem(int idPjQueQuiereElItem) {
		this.idPjQueQuiereElItem = idPjQueQuiereElItem;
	}
}
