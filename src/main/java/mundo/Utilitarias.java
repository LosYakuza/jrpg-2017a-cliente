package mundo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import frames.MenuJugar;


public class Utilitarias {

	public static String archivoAString(String path) {
		StringBuilder builder = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					ClassLoader.getSystemResourceAsStream(path)
					));
			String linea;

			while ((linea = br.readLine()) != null) {
				builder.append(linea + System.lineSeparator());
			}
			
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo al intentar cargar el mapa " + path );
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static int parseInt(String numero) {
		try {
			return Integer.parseInt(numero);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
