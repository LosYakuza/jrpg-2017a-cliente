package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;


import chat.ArchivoDePropiedades;
import comandos.ComandoCliente;
import frames.*;
import juego.Juego;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class Cliente extends Thread {

	private Socket cliente;
	private String miIp;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;

	// Paquete usuario y paquete personaje
	private PaqueteUsuario paqueteUsuario;
	private PaquetePersonaje paquetePersonaje;

	// Acciones que realiza el usuario
	private int accion;

	// Ip y puerto
	private String ip;
	private int puerto;
	
	private boolean running; 

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	private Juego wome;
	private MenuCarga menuCarga;
	
	public void parar() { 
		running = false; 
	} 
	
	public boolean estaEjecutando() { 
		return running; 
	} 

	public Cliente() {

		ArchivoDePropiedades adp = new ArchivoDePropiedades("config.properties");
		adp.lectura();
		ip = adp.getIP();
		puerto = adp.getPuertoJuego();

		try {
			cliente = new Socket(ip, puerto);
			miIp = cliente.getInetAddress().getHostAddress();
			entrada = new ObjectInputStream(cliente.getInputStream());
			salida = new ObjectOutputStream(cliente.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo al iniciar la aplicación. Revise la conexión con el servidor"); 
			e.printStackTrace();
		}
	}

	public void run() {
		synchronized (this) {
			try {

				// Creo el paquete que le voy a enviar al servidor
				paqueteUsuario = new PaqueteUsuario();

				while (!paqueteUsuario.isInicioSesion()) {

					// Muestro el menú principal
					new MenuJugar(this).setVisible(true);

					// Creo los paquetes que le voy a enviar al servidor
					paqueteUsuario = new PaqueteUsuario();
					paquetePersonaje = new PaquetePersonaje();

					// Espero a que el usuario seleccione alguna accion
					wait();

					switch (getAccion()) {

					case Comando.REGISTRO:
						paqueteUsuario.setComando(Comando.REGISTRO);
						break;
					case Comando.INICIOSESION:
						paqueteUsuario.setComando(Comando.INICIOSESION);
						break;
					case Comando.SALIR:
						paqueteUsuario.setIp(getMiIp());
						paqueteUsuario.setComando(Comando.SALIR);
						break;
					}

					// Le envio el paquete al servidor
					salida.writeObject(paqueteUsuario.getJson());

					// Recibo el paquete desde el servidor
					String cadenaLeida = (String) entrada.readObject();
					Paquete paquete = Paquete.loadJson(cadenaLeida);
					ComandoCliente cc = (ComandoCliente)paquete.getComandoObj(ComandoCliente.PACKAGEO);
					cc.setCliente(this);
					cc.ejecutar();

				}
					 
				// Creo un paquete con el comando mostrar mapas
				paquetePersonaje.setComando(Comando.MOSTRARMAPAS);

				// Abro el menu de eleccion del mapa
				MenuMapas menuElegirMapa = new MenuMapas(this);
				menuElegirMapa.setVisible(true);

				// Espero a que el usuario elija el mapa
				wait();

				// Establezco el mapa en el paquete personaje
				paquetePersonaje.setIp(miIp);

				// Le envio el paquete con el mapa seleccionado
				salida.writeObject(paquetePersonaje.getJson());

				// Instancio el juego y cargo los recursos
				wome = new Juego("World Of the Middle Earth", 800, 600, this, paquetePersonaje);

				// Muestro el menu de carga
				menuCarga = new MenuCarga(this);
				menuCarga.setVisible(true);

				// Espero que se carguen todos los recursos
				wait();

				// Inicio el juego
				wome.start();

				// Finalizo el menu de carga
				menuCarga.dispose();

			} catch (IOException | InterruptedException | ClassNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor durante el inicio de sesión.");
				System.exit(1);
			}
		}

	}

	public Socket getSocket() {
		return cliente;
	}

	public void setSocket(Socket cliente) {
		this.cliente = cliente;
	}

	public String getMiIp() {
		return miIp;
	}

	public void setMiIp(String miIp) {
		this.miIp = miIp;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public PaquetePersonaje getPaquetePersonaje() {
		return paquetePersonaje;
	}
	
	public void setPaquetePersonaje(PaquetePersonaje p) {
		 paquetePersonaje = p;
	}

	public Juego getJuego() {
		return wome;
	}

	public MenuCarga getMenuCarga() {
		return menuCarga;
	}
}
