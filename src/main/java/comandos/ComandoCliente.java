package comandos;

import juego.Juego;
import mensajeria.Comando;

public abstract class ComandoCliente extends Comando {

	public static final String PACKAGEO = "comandos";
	
	protected Juego juego;

	public void setJuego(Juego juego) {
		this.juego = juego;
	}
	
}
