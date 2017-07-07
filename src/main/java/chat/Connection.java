package chat;

//import chatServidor.Message;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread{
	
	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
	private String user;
	private MessageHandler mh;
	private int stop = 0;

	public Connection(String host, int port, String user, MessageHandler mh) throws Exception {
		this.sock = new Socket(host, port);
		this.user = user;
		this.mh = mh;
		this.in = new DataInputStream(new BufferedInputStream(this.sock.getInputStream()));
		this.out = new DataOutputStream(new BufferedOutputStream(this.sock.getOutputStream()));
	}

	public void stopRequest(){
		stop=1;
	}
	
	@Override
	public void run() {
		boolean c = true;
		while (c && stop==0) {
			try {
				Message msg = new Message(in.readUTF());
				process(msg);
			}catch(EOFException e){
				error("Mensaje no se pudo procesar");
			}catch (IOException e) {
				c=false;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		kissoff();
	}

	private void kissoff() {
		try {
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(Message msg) throws IOException {
		/**
		 * Mensajes de protocolo
		 */
		if (msg.getType() == Message.SERVER_ASK) {
			if (msg.getText().equals("login")) {
				login();
				return;
			}
		}
		
		/**
		 * Envia para ser mostrado 
		 */
		this.mh.messageReceived(msg);

	}
	
	private void error(String msj) { 
		Message m = new Message(); 
		m.setDestination("user"); 
		m.setText(msj); 
		m.setType(Message.STATUS_INFO); 
		this.mh.messageReceived(m); 
	} 

	private void login() throws IOException {
		Message m = new Message();
		m.setDestination("user");
		m.setText(this.user);
		m.setType(Message.CLIENT_DATA);
		this.out.writeUTF(m.toString());
		this.out.flush();
	}
	
	public void sendChat(String usr,String text) throws IOException{
		Message m = new Message();
		m.setType(Message.USR_MSJ);
		m.setSource(usr);
		if(text.contains("@")) {
			String dest = text.substring(1, text.indexOf(" "));
			String txt = text.substring(text.indexOf(" ") + 1);
			m.setDestination(dest);
			m.setText(txt);
		}
		else {
			m.setSource(usr);
			m.setDestination("all");
			m.setText(text);
		}
		this.out.writeUTF(m.toString());
		this.out.flush();
	}

}
