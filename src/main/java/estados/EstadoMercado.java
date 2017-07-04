package estados;

import java.awt.Graphics;

import juego.Juego;
import mensajeria.PaqueteMercado;
import mundo.Mundo;
import recursos.Recursos;

public class EstadoMercado extends Estado {
	//private Mundo mundo;

	public EstadoMercado(Juego juego, PaqueteMercado paqueteMercado) {
		super(juego);
		//mundo = new Mundo(juego, "recursos/mundoBatalla.txt", "recursos/mundoBatallaCapaDos.txt");
		//mundo = new Mundo(juego, "recursos/Aubenor.txt", "");
		
	}

	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void graficar(Graphics g) {
		g.drawImage(Recursos.background, 0, 0, juego.getAncho(), juego.getAlto(), null);
		//mundo.graficar(g);		
	}

}
