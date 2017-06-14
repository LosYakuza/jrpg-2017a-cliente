package comandos;

import java.util.Map;

import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteMovimiento;


public class Movimiento extends ComandoCliente {

	@Override
	public void ejecutar() {
		juego.setUbicacionPersonajes((Map<Integer, PaqueteMovimiento>) ((PaqueteDeMovimientos)paquete).getPersonajes());
	}

}
