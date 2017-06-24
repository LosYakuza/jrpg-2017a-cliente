package comandos;

import cliente.Cliente;
import juego.Juego;
import mensajeria.Comando;

public abstract class ComandoCliente extends Comando {

	public static final String PACKAGEO = "comandos";
	
	protected Juego juego;
	protected Cliente cliente;

	public void setJuego(Juego juego) {
		this.juego = juego;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
