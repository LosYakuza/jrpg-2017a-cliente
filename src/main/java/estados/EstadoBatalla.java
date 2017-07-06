package estados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import dominio.Asesino;
import dominio.Casta;
import dominio.Elfo;
import dominio.Guerrero;
import dominio.Hechicero;
import dominio.Humano;
import dominio.Item;
import dominio.Orco;
import dominio.Personaje;
import interfaz.EstadoDePersonaje;
import interfaz.MenuBatalla;
import interfaz.MenuInfoPersonaje;
import juego.Juego;
import juego.Pantalla;
import mensajeria.Comando;
import mensajeria.PaqueteAtacar;
import mensajeria.PaqueteBatalla;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaquetePersonaje;
import mundo.Mundo;
import recursos.Recursos;

public class EstadoBatalla extends Estado {

	private Mundo mundo;
	private Personaje personaje;
	private Personaje enemigo;
	private int[] posMouse;
	private PaquetePersonaje paquetePersonaje;
	private PaquetePersonaje paqueteEnemigo;
	private PaqueteAtacar paqueteAtacar;
	private PaqueteFinalizarBatalla paqueteFinalizarBatalla;
	private boolean miTurno;
	
	private boolean haySpellSeleccionada;
	private boolean seRealizoAccion;

	private BufferedImage miniaturaPersonaje;
	private BufferedImage miniaturaEnemigo;
	
	private MenuBatalla menuBatalla;
	
	public EstadoBatalla(Juego juego, PaqueteBatalla paqueteBatalla) {
		super(juego);
		mundo = new Mundo(juego, "recursos/mundoBatalla.txt", "recursos/mundoBatallaCapaDos.txt");
		miTurno = paqueteBatalla.isMiTurno();

		paquetePersonaje = juego.getPersonajesConectados().get(paqueteBatalla.getId());
		paqueteEnemigo = juego.getPersonajesConectados().get(paqueteBatalla.getIdEnemigo());

		crearPersonajes();
		
		menuBatalla = new MenuBatalla(miTurno, personaje);
		
		miniaturaEnemigo = Recursos.personaje.get(enemigo.getNombreRaza()).get(5)[0];
		miniaturaPersonaje = Recursos.personaje.get(personaje.getNombreRaza()).get(5)[0];
	
		paqueteFinalizarBatalla = new PaqueteFinalizarBatalla();
		paqueteFinalizarBatalla.setId(personaje.getIdPersonaje());
		paqueteFinalizarBatalla.setIdEnemigo(enemigo.getIdPersonaje());
		
		// por defecto batalla perdida
		juego.getEstadoJuego().setHaySolicitud(true, juego.getPersonaje(), MenuInfoPersonaje.menuPerderBatalla);
		
		// limpio la accion del mouse
		juego.getHandlerMouse().setNuevoClick(false);
		
	}

	@Override
	public void actualizar() {
		
		juego.getCamara().setxOffset(-350);
		juego.getCamara().setyOffset(150);
		
		seRealizoAccion = false;
		haySpellSeleccionada = false;

		if (miTurno) {
			
			if (juego.getHandlerMouse().getNuevoClick()) {
				posMouse = juego.getHandlerMouse().getPosMouse();

				if (menuBatalla.clickEnMenu(posMouse[0], posMouse[1])) {

					if (menuBatalla.getBotonClickeado(posMouse[0], posMouse[1]) == 1) {
						if(personaje.puedeAtacar()){
							seRealizoAccion = true;
							personaje.habilidadRaza1(enemigo);
						}
						haySpellSeleccionada = true;
					}

					if (menuBatalla.getBotonClickeado(posMouse[0], posMouse[1]) == 2) {
						if(personaje.puedeAtacar()){
							seRealizoAccion = true;
							personaje.habilidadRaza2(enemigo);
						}
						haySpellSeleccionada = true;
					}

					if (menuBatalla.getBotonClickeado(posMouse[0], posMouse[1]) == 3) {
						if(personaje.puedeAtacar()){
							seRealizoAccion = true;
							personaje.habilidadCasta1(enemigo);
						}
						haySpellSeleccionada = true;
					}

					if (menuBatalla.getBotonClickeado(posMouse[0], posMouse[1]) == 4) {
						if(personaje.puedeAtacar()){
							seRealizoAccion = true;
							personaje.habilidadCasta2(enemigo);
						}
						haySpellSeleccionada = true;
					}

					if (menuBatalla.getBotonClickeado(posMouse[0], posMouse[1]) == 5) {
						if(personaje.puedeAtacar()){
							seRealizoAccion = true;
							personaje.habilidadCasta3(enemigo);
						}
						haySpellSeleccionada = true;
					}

					if (menuBatalla.getBotonClickeado(posMouse[0], posMouse[1]) == 6) {
						seRealizoAccion = true;
						personaje.serEnergizado(20);
						haySpellSeleccionada = true;
					}
				}


				if (haySpellSeleccionada && seRealizoAccion) {
					if (!enemigo.estaVivo()) {
						juego.getEstadoJuego().setHaySolicitud(true, juego.getPersonaje(), MenuInfoPersonaje.menuGanarBatalla);
						if(personaje.ganarExperiencia(enemigo.getNivel() * 40)){
							juego.getPersonaje().setNivel(personaje.getNivel());
							juego.getEstadoJuego().setHaySolicitud(true, juego.getPersonaje(), MenuInfoPersonaje.menuSubirNivel);
						}
						winItem(personaje);
						finalizarBatalla();
						Estado.setEstado(juego.getEstadoJuego());
					} else {
						paqueteAtacar = new PaqueteAtacar(paquetePersonaje.getId(), paqueteEnemigo.getId(), personaje.getSalud(), personaje.getEnergia(), enemigo.getSalud(), enemigo.getEnergia());
						enviarAtaque(paqueteAtacar);
						miTurno = false;
						menuBatalla.setHabilitado(false);
					}
				} else if(haySpellSeleccionada && !seRealizoAccion){
					JOptionPane.showMessageDialog(null, "No posees la energ�a suficiente para realizar esta habilidad.");
				}

				juego.getHandlerMouse().setNuevoClick(false);
			}
		}
		
	}

	@Override
	public void graficar(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, juego.getAncho(), juego.getAlto());
		mundo.graficar(g);
		
		g.drawImage(Recursos.personaje.get(paquetePersonaje.getRaza()).get(3)[0], 0, 175, 256, 256, null);
		g.drawImage(Recursos.personaje.get(paqueteEnemigo.getRaza()).get(7)[0], 550, 75, 256, 256, null);
		
		mundo.graficarObstaculos(g);
		menuBatalla.graficar(g);
		
		g.setColor(Color.GREEN);
		
		EstadoDePersonaje.dibujarEstadoDePersonaje(g, 25, 5, personaje, miniaturaPersonaje);
		EstadoDePersonaje.dibujarEstadoDePersonaje(g, 550, 5, enemigo, miniaturaEnemigo);
		
	}
	
	private void crearPersonajes() {
		String nombre = paquetePersonaje.getNombre();
		int salud = paquetePersonaje.getSaludTope();
		int energia = paquetePersonaje.getEnergiaTope();
		int fuerza = paquetePersonaje.getFuerza();
		int destreza = paquetePersonaje.getDestreza();
		int inteligencia = paquetePersonaje.getInteligencia();
		int experiencia = paquetePersonaje.getExperiencia();
		int nivel = paquetePersonaje.getNivel();
		int id = paquetePersonaje.getId();
		LinkedList<Item> inventario = paquetePersonaje.getInventario();

		Casta casta = null;
		if (paquetePersonaje.getCasta().equals("Guerrero")) {
			casta = new Guerrero();
		} else if (paquetePersonaje.getCasta().equals("Hechicero")) {
			casta = new Hechicero();
		} else if (paquetePersonaje.getCasta().equals("Asesino")) {
			casta = new Asesino();
		}

		if (paquetePersonaje.getRaza().equals("Humano")) {
			personaje = new Humano(nombre, salud, energia, fuerza, destreza, inteligencia, casta, 
				experiencia, nivel, id, inventario);
		} else if (paquetePersonaje.getRaza().equals("Orco")) {
			personaje = new Orco(nombre, salud, energia, fuerza, destreza, inteligencia, casta, 
					experiencia, nivel, id, inventario);
		} else if (paquetePersonaje.getRaza().equals("Elfo")) {
			personaje = new Elfo(nombre, salud, energia, fuerza, destreza, inteligencia, casta,
					experiencia, nivel, id, inventario);
		}

		nombre = paqueteEnemigo.getNombre();
		salud = paqueteEnemigo.getSaludTope();
		energia = paqueteEnemigo.getEnergiaTope();
		fuerza = paqueteEnemigo.getFuerza();
		destreza = paqueteEnemigo.getDestreza();
		inteligencia = paqueteEnemigo.getInteligencia();
		experiencia = paqueteEnemigo.getExperiencia();
		nivel = paqueteEnemigo.getNivel();
		id = paqueteEnemigo.getId();
		inventario = paqueteEnemigo.getInventario();

		casta = null;
		if (paqueteEnemigo.getCasta().equals("Guerrero")) {
			casta = new Guerrero();
		} else if (paqueteEnemigo.getCasta().equals("Hechicero")) {
			casta = new Hechicero();
		} else if (paqueteEnemigo.getCasta().equals("Asesino")) {
			casta = new Asesino();
		}

		if (paqueteEnemigo.getRaza().equals("Humano")) {
			enemigo = new Humano(nombre, salud, energia, fuerza, destreza, inteligencia, casta,
					experiencia, nivel, id, inventario);
		} else if (paqueteEnemigo.getRaza().equals("Orco")) {
			enemigo = new Orco(nombre, salud, energia, fuerza, destreza, inteligencia, casta,
					experiencia, nivel, id, inventario);
		} else if (paqueteEnemigo.getRaza().equals("Elfo")) {
			enemigo = new Elfo(nombre, salud, energia, fuerza, destreza, inteligencia, casta,
					experiencia, nivel, id, inventario);
		}
	}

	public void enviarAtaque(PaqueteAtacar paqueteAtacar) {
		try {
			juego.getCliente().getSalida().writeObject(paqueteAtacar.getJson());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexion con el servidor.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void finalizarBatalla() {
		try {
			juego.getCliente().getSalida().writeObject(paqueteFinalizarBatalla.getJson());

			paquetePersonaje.setSaludTope(personaje.getSaludTopeBase());
			paquetePersonaje.setEnergiaTope(personaje.getEnergiaTopeBase());
			paquetePersonaje.setNivel(personaje.getNivel());
			paquetePersonaje.setExperiencia(personaje.getExperiencia());
			paquetePersonaje.setDestreza(personaje.getDestrezaBase());
			paquetePersonaje.setFuerza(personaje.getFuerzaBase());
			paquetePersonaje.setInteligencia(personaje.getInteligenciaBase());
			paquetePersonaje.setInventario(personaje.getInventario());
			
			paqueteEnemigo.setSaludTope(enemigo.getSaludTopeBase());
			paqueteEnemigo.setEnergiaTope(enemigo.getEnergiaTopeBase());
			paqueteEnemigo.setNivel(enemigo.getNivel());
			paqueteEnemigo.setExperiencia(enemigo.getExperiencia());
			paqueteEnemigo.setDestreza(enemigo.getDestrezaBase());
			paqueteEnemigo.setFuerza(enemigo.getFuerzaBase());
			paqueteEnemigo.setInteligencia(enemigo.getInteligenciaBase());
			paqueteEnemigo.setInventario(enemigo.getInventario());
			
			paquetePersonaje.setComando(Comando.ACTUALIZARPERSONAJE);
			paqueteEnemigo.setComando(Comando.ACTUALIZARPERSONAJE);
			
			juego.getCliente().getSalida().writeObject(paquetePersonaje.getJson());
			juego.getCliente().getSalida().writeObject(paqueteEnemigo.getJson());
			
			Pantalla.visibleChat();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public PaquetePersonaje getPaquetePersonaje() {
		return paquetePersonaje;
	}

	public PaquetePersonaje getPaqueteEnemigo() {
		return paqueteEnemigo;
	}

	public void setMiTurno(boolean b) {
		miTurno = b;
		menuBatalla.setHabilitado(b);
		juego.getHandlerMouse().setNuevoClick(false);
	}

	public Personaje getPersonaje() {
		return personaje;
	}

	public Personaje getEnemigo() {
		return enemigo;
	}

	private void winItem(Personaje personaje) {
		personaje.guardarItemEnInventario(new Item(personaje.getRandomGenerator()));
	}
}
