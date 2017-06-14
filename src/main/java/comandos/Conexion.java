package comandos;

import java.util.Map;

import mensajeria.PaqueteDePersonajes;
import mensajeria.PaquetePersonaje;

public class Conexion extends ComandoCliente {

	@Override
	public void ejecutar() {
		juego.setPersonajesConectados((Map<Integer, PaquetePersonaje>) ((PaqueteDePersonajes)paquete).getPersonajes());
	}

}
