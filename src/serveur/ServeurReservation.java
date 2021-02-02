package serveur;

import java.io.IOException;
import java.net.ServerSocket;

import services.ServiceReservation;

public class ServeurReservation implements Runnable{
	private ServerSocket listen_socket;
	
	public ServeurReservation(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}

	public void run() {
		try {
			System.err.println("Lancement du serveur au port "+this.listen_socket.getLocalPort());
			while(true)
				new Thread(new ServiceReservation(listen_socket.accept())).start();
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Arrêt du serveur au port "+this.listen_socket.getLocalPort());
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}
}
