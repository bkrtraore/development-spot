package services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import abstract_documents.Document;
import client.Abonne;
import documents.DVD;
import exceptions.AbonneException;
import exceptions.AgeException;
import exceptions.EmpruntException;
import exceptions.NumeroException;
import states.DocumentIndisponible;

public class ServiceEmprunt extends Service implements Runnable{
	public ServiceEmprunt(Socket socket) {
		super(socket);
	}
	
	public void emprunt(int numDoc, int numAb) throws EmpruntException, AbonneException{
		Abonne ab = Service.getListeAbonnes().get(numAb);
		synchronized(getListeDocuments().get(numDoc)) {
			getListeDocuments().get(numDoc).emprunt(ab);
		}
	}
	
	public boolean verifNumDoc(int numDoc) throws NumeroException, EmpruntException{
		if(!getListeDocuments().containsKey(numDoc))
			throw new NumeroException(numDoc);
		Document doc = (Document)getListeDocuments().get(numDoc);
		synchronized(doc) {
			if(doc.getState() instanceof DocumentIndisponible)
				throw new EmpruntException(doc);
		}
		return true;
	}
	
	// Ici on v�rifie si on peut se munir du document (Uniquement DVD)
	public boolean verifAdulte(int numDoc, int numAb) throws AgeException {
		DVD dvd = (DVD)getListeDocuments().get(numDoc);
		Abonne ab = Service.getListeAbonnes().get(numAb);
		synchronized(this) {
			if((dvd.isAdulte() == true) && (ab.isAdulte() == false))
				throw new AgeException(dvd);
			}
		return true;
	}
	
	// Ajout
	
	public boolean verifNumAb(int numAb) throws AbonneException{
		if(!Service.getListeAbonnes().containsKey(numAb))
			throw new AbonneException(numAb);
		return true;
	}
	
	public void run(){
		System.err.println("Connexion " + getNumConnexion() +" �tablie (Emprunt)");
		String line;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
			int numDoc = 0;
			int numAb = 0;
			boolean quit = false;
			
			line = "------ EMPRUNT ------\n\n**Entrez 'q' pour quitter**\nEntrez votre num�ro d'abonn� : ";
			out.println(line.replace("\n","##")); //envoi de cette chaine
			
			while(!quit) {
				line = in.readLine();//r�ception num�ro du document ou q
				if(line.equals("q")) {
					quit = true;
					line = "\n**Vous avez quitt� l'application** \n\n---- A BIENTOT ----";
					out.println(line.replace("\n", "##"));
					break;
				}
				//numDoc = Integer.parseInt(line);
				try {
					numAb = Integer.parseInt(line); //r�ception num�ro abonn�
					if (verifNumAb(numAb)) {
						break;
					}
				} catch(NumberFormatException e) {
					line = "\nSymbole '" + line + "' inconnu\nEntrez de nouveau le num�ro d'abonn� : ";
					out.println(line.replace("\n","##"));
				} catch (AbonneException e) {
					line = "\n" + e + "\nVous vous �tes s�rement tromp� de num�ro.\nEntrez de nouveau votre num�ro d'abonn� : ";
					out.println(line.replace("\n","##"));
				}
			}
			
			if(!quit) {
				line =  "\nVous �tes authentifi� "+ Service.getListeAbonnes().get(numAb).getNom() + " !\nEntrez le num�ro du document � emprunter : ";
				out.println(line.replace("\n","##"));//envoi de cette chaine
			}
			
			while(!quit) {
				line = in.readLine();//r�ception num�ro du document ou q
				if(line.equals("q")) {
					quit = true;
					line = "\n**Vous avez quitt� l'application** \n\n---- A BIENTOT ----";
					out.println(line.replace("\n", "##"));
					break;
				}
				try {
					numDoc = Integer.parseInt(line);
					if(verifNumDoc(numDoc) && verifAdulte(numDoc, numAb)) {
						emprunt(numDoc,numAb);
						line = "\nEmprunt du Document '" + getListeDocuments().get(numDoc) + "' effectu�\n\nEntrez le num�ro du prochain document � emprunter ou entrez \"q\" pour quitter : ";
						out.println(line.replace("\n","##"));
					}
				} catch (AbonneException e) {
					line = e+ "\nVous vous �tes s�rement tromp� de num�ro.\nEntrez de nouveau votre num�ro d'abonn� : ";
					out.println(line.replace("\n","##"));
				} catch (EmpruntException e) {
					line = e + "\nEntrez de nouveau le num�ro du Document : ";
					out.println(line.replace("\n","##"));
				} catch (NumberFormatException e) {
					line = "\nSymbole '" + line + "' inconnu\nEntrez de nouveau le num�ro du Document : ";
					out.println(line.replace("\n","##"));
				} catch (NumeroException e) {
					line = "\n" + e + "\nEntrez de nouveau le num�ro du Document : ";
					out.println(line.replace("\n","##"));
				} catch (AgeException e) {
					line = "\n" + e + "\nEntrez de nouveau le num�ro du Document : ";
					out.println(line.replace("\n","##"));
				}
			}
			
		
			closeSocket("Emprunt",getNumConnexion());
		} catch (IOException e) { // Le client s'est deco
			closeSocket("Emprunt",getNumConnexion());
		}
	}
}
