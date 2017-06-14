package comandos;

import estados.Estado;
import estados.EstadoBatalla;
import mensajeria.PaqueteBatalla;

public class Batalla extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteBatalla paqueteBatalla = (PaqueteBatalla)paquete;
		juego.getPersonaje().setEstado(Estado.estadoBatalla);
		Estado.setEstado(null);
		juego.setEstadoBatalla(new EstadoBatalla(juego, paqueteBatalla));
		Estado.setEstado(juego.getEstadoBatalla());

	}

}
