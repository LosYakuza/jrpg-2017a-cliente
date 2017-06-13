package comandos;

import java.util.HashMap;

import mensajeria.PaqueteAtacar;

public class Atacar extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteAtacar paqueteAtacar = (PaqueteAtacar) paquete;
		HashMap<String, Object> datos = juego.getEstadoBatalla().getPersonaje().getTodo();
		datos.putAll(paqueteAtacar.getTodoPersonaje());
		juego.getEstadoBatalla().getPersonaje().actualizar(datos);
		
		datos = juego.getEstadoBatalla().getEnemigo().getTodo();
		datos.putAll(paqueteAtacar.getTodoEnemigo());
		juego.getEstadoBatalla().getEnemigo().actualizar(datos);

		juego.getEstadoBatalla().setMiTurno(true);

	}

}
