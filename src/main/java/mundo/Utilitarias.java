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
		BufferedReader br;
		String linea;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (Exception e) {
			try {
				br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(path)));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Fallo al intentar cargar el mapa " + path);
				return null;
			}
		}

		try{
			while ((linea = br.readLine()) != null) {
				builder.append(linea + System.lineSeparator());
			}
	
			br.close();
			return builder.toString();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fallo al intentar cargar el mapa " + path);
			return null;
		}
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
