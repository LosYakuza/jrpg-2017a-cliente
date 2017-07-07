package chat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class ArchivoDePropiedades {

	private Properties propiedad;
	private String ip ;
	private int puertoChat;
	private int puertoJuego;
	private String archivo;
	
	public ArchivoDePropiedades(String archivo) {
		propiedad = new Properties();
		ip = "";
		puertoChat = 0;
		this.archivo = archivo;
	}
	
	public void lectura() {
		try {
			propiedad.load(new FileInputStream(archivo));
			ip = propiedad.getProperty("IP");
			puertoJuego = Integer.parseInt(propiedad.getProperty("PUERTO_JUEGO"));
			puertoChat = Integer.parseInt(propiedad.getProperty("PUERTO_CHAT"));
		} catch (IOException e) {
			ip = "localhost";
			puertoJuego = 80;
			puertoChat = 443;
		}
	}
	
	public void escritura(String ip, int puerto) {
		try {
			propiedad.setProperty("IP", ip);
			propiedad.setProperty("PUERTO", "" + puerto);
			
			propiedad.store(new FileOutputStream(archivo), null);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}
	
	public String getIP() {
		return ip;
	}
	
	public int getPuertoChat() {
		return puertoChat;
	}
	
	public int getPuertoJuego() {
		return puertoJuego;
	}

}