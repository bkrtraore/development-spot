package services;


import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import abstract_documents.IDocument;
import client.Abonne;
import serveur.IService;

public abstract class Service implements IService {
	private static HashMap<Integer,IDocument> listeDocuments = new HashMap<>();
	private static HashMap<Integer,Abonne> listeAbonnes = new HashMap<>();
	private static int nbConnexion = 0;
	private static Object locker = new Object();
	private int numConnexion;
	private Socket socket;
	
	public Service(Socket socket) {
		this.socket = socket;
		synchronized(locker) {
			this.numConnexion = ++nbConnexion;
		}
		
	}
	
	public int getNumConnexion() {
		return numConnexion;
	}
	
	public Socket getSocket() {
		return socket;
	}
		
	public static HashMap<Integer,IDocument> getListeDocuments(){
		return listeDocuments;
	}	
	
	public static HashMap<Integer,Abonne> getListeAbonnes(){
		return listeAbonnes;
	}
	
	protected void closeSocket(String line, int num){
		System.err.println("Connexion " + num + " terminée (" + line + ")");
		try {
			socket.close();
		} catch (IOException e) {} 
	}
}
