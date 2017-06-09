package mensajeria;

import java.io.Serializable;
import java.util.LinkedList;

import dominio.Item;
import dominio.ModificadorSegunItem;
import dominio.Operacion;
import estados.Estado;

public class PaquetePersonaje extends Paquete implements Serializable, Cloneable {

	private int id;
	private int idMapa;
	private int estado;
	private String casta;
	private String nombre;
	private String raza;
	private int saludTope;
	private int energiaTope;
	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int nivel;
	private int experiencia;
	private LinkedList<Item> inventario = new LinkedList<Item>();

	public PaquetePersonaje() {
		estado = Estado.estadoOffline;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public int getMapa(){
		return idMapa;
	}
	
	public void setMapa(int mapa){
		idMapa = mapa;
	}
	
	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCasta() {
		return casta;
	}


	public void setCasta(String casta) {
		this.casta = casta;
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRaza() {
		return raza;
	}


	public void setRaza(String raza) {
		this.raza = raza;
	}


	public int getSaludTope() {
		return saludTope;
	}


	public void setSaludTope(int saludTope) {
		this.saludTope = saludTope;
	}


	public int getEnergiaTope() {
		return energiaTope;
	}


	public void setEnergiaTope(int energiaTope) {
		this.energiaTope = energiaTope;
	}


	public int getFuerza() {
		return fuerza;
	}


	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}


	public int getDestreza() {
		return destreza;
	}


	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}


	public int getInteligencia() {
		return inteligencia;
	}


	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}

	public LinkedList<Item> getInventario() {
		return inventario;
	}

	public void setInventario(LinkedList<Item> inventario) {
		this.inventario = inventario;
	}

	// TODO: Habría que armar un PaqueteItem o algo así para que el personaje se lo equipe solo. Acá rompe el encapsulamiento.
	public void equiparItem(int valorSalud, int opSalud, int valorFuerza, int opFuerza, 
			int valorDestreza, int opDestreza, int valorInteligencia, int opInteligencia,
			int valorEnergia, int opEnergia, int id) {
		ModificadorSegunItem modSalud = new ModificadorSegunItem(valorSalud, Operacion.operacionSegunId(opSalud));
		ModificadorSegunItem modFuerza = new ModificadorSegunItem(valorFuerza, Operacion.operacionSegunId(opFuerza));
		ModificadorSegunItem modDestreza = new ModificadorSegunItem(valorDestreza, Operacion.operacionSegunId(opDestreza));
		ModificadorSegunItem modInteligencia = new ModificadorSegunItem(valorInteligencia, Operacion.operacionSegunId(opInteligencia));
		ModificadorSegunItem modEnergia= new ModificadorSegunItem(valorEnergia, Operacion.operacionSegunId(opEnergia));
		
		Item item = new Item(modSalud, modFuerza, modDestreza, modInteligencia, modEnergia);
		item.setIdItem(id);
		inventario.add(item);
	}

	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}
}
