package appli_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliClientEmprunt {
	private final static int PORT = 4000;
	private final static String HOST = "localhost"; 

	public static void main(String[] args) {
		Socket socket = null; 
		try {
			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
			System.out.println("Connect� au serveur " + socket.getInetAddress() + ":"+ socket.getPort() + "\n");
			String lineServer = "";
			String lineClient = "";
			System.out.println(lineServer = sin.readLine().replace("##","\n"));
			while(!lineClient.equals("q") && !lineServer.contains("A BIENTOT")){
				sout.println(lineClient = clavier.readLine());
				System.out.println(lineServer = sin.readLine().replace("##","\n"));
			};
			socket.close();
		}
		catch (IOException e) { // Le serveur a crash�
			System.err.println("Serveur Interrompu");
		}
		try { if (socket != null) socket.close(); } 
		catch (IOException e) {}	
	}
}
