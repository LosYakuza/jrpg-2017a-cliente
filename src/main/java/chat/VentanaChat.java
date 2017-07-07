package chat;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import chatServidor.Message;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class VentanaChat extends JInternalFrame implements MessageHandler {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JTextArea textArea;
	private Connection conn;
	private String nombrePj;

	/**
	 * Create the frame.
	 */
	public VentanaChat(final String nombrePj) {	
		setResizable(false);
		setBounds(0, 375, 250, 240);
		setVisible(true);
		setClosable(false);
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);

		this.nombrePj = nombrePj;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 235, 180);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setText("Bienvenido a WOME...\r\n\r\n");

		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					enviarMensaje(nombrePj);
			}
		});
		textField.setBounds(0, 185, 233, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		ArchivoDePropiedades adp = new ArchivoDePropiedades("config.properties");
		adp.lectura();
		try {
			conn = new Connection(adp.getIP(), adp.getPuertoChat(), nombrePj, this);
			conn.start();
		} catch (Exception e) {
			textArea.setCaretPosition(textArea.getText().length());
			textArea.append("##Info: Error al conectar chat");
		}
	}
	
	public void enviarMensaje(final String nombrePj) {
		try {
			conn.sendChat(nombrePj, textField.getText());
		} catch (IOException e) {
			e.printStackTrace();
			recibido("##Info: Error al enviar mensaje");
		}
		if(textField.getText().toString().contains("@")) {
			String mensaje = textField.getText().toString().substring(1);
			String destino = textField.getText().toString().substring(1, textField.getText().toString().indexOf(" "));
			mensaje = mensaje.substring(mensaje.indexOf(" ") + 1);
			textArea.setCaretPosition(textArea.getText().length());
			textArea.append("<< Para " +  destino + ": " + mensaje + " >>" + "\n");
			textField.setText("");
		}
		else {	
			textArea.setCaretPosition(textArea.getText().length());
			textArea.append(nombrePj + ": " + textField.getText() + "\n");
			textField.setText("");
		}
	}
	
	public void recibido(String str){
		textArea.setCaretPosition(textArea.getText().length());
		textArea.append(str + "\n");
	}

	@Override
	public void messageReceived(Message m) {
		if(m.getDestination().equals("all")){
			if(m.getType() == Message.USR_MSJ && !m.getSource().equals(nombrePj)){
				recibido(m.getSource()+ ": "+m.getText());
			}
		}else{
			if(m.getType() == Message.USR_MSJ){
				recibido("<< De " + m.getSource()+ ": "+m.getText() + " >>");
				setVisible(true);
			}
			if(m.getType() == Message.STATUS_INFO){
				if(!m.getText().contains(nombrePj))
					recibido("## Info: "+m.getText());
			}
		}
		
	}

}
