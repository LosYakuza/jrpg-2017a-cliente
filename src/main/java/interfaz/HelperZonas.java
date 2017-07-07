package interfaz;

import mensajeria.PaqueteMovimiento;

public class HelperZonas {

	public static boolean clickEnMercado(int x, int y) {
		return (x >= 7 && x <= 9) && (y >= 1 && y <= 3);
	}

	/**
	 * Si un pj está en zona mercado y el otro no, no deberían poder interactuar.
	 * @param ubicPj1 paquete movimiento pj
	 * @param ubicPj2 paquete movimiento pj
	 * @return false si se encuentran en zonas distintas
	 */
	public static boolean esZonaNeutra(PaqueteMovimiento ubicPj1,
			int[] ubicPj2) {
		return (pjEnZonaMercado(ubicPj1) && !pjEnZonaMercado(ubicPj2)) ||
				(pjEnZonaMercado(ubicPj2) && !pjEnZonaMercado(ubicPj1));
	}

	/**
	 * Tiene definidos los límites de la zona de mercado. Evalúa según x y.
	 * @param ubic paquete movimiento
	 * @return true si se encuentra en zona mercado
	 */
	public static boolean pjEnZonaMercado(PaqueteMovimiento ubic) {
		float x = ubic.getPosX(), y = ubic.getPosY();
		return (x >= 32 && x <= 223) && (y >= 129 && y <= 223.5);
	}

	public static boolean pjEnZonaMercado(int[] ubic) {
		// Límites --> X(7, 11) - Y(1, 10) tiles
		float x = ubic[0], y = ubic[1];
		return (x >= 7 && x <= 11) && (y >= 1 && y <= 10);
	}
}
