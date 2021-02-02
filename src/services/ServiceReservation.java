package services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import abstract_documents.Document;
import abstract_documents.IDocument;
import client.Abonne;
import documents.DVD;
import exceptions.AbonneException;
import exceptions.AgeException;
import exceptions.NumeroException;
import exceptions.ReservationException;
import minuteurs.DateMinuteur;

public class ServiceReservation extends Service implements Runnable {
	
	public ServiceReservation(Socket socket) {
		super(socket);
	}
	
	private void reserver(Abonne abonne, IDocument document) throws ReservationException {
		
		Document doc = (Document) document;
		synchronized(doc) {
			doc.reserve(abonne);
			Timer timer = new Timer();
			TimerTask task = new AnnulationTask(doc,abonne); 
			timer.schedule(task,DateMinuteur.addHours(new Date(), 2));
			
		}
		
	}
	
	public IDocument verifNumDoc(int numDoc) throws NumeroException{
		if(!getListeDocuments().containsKey(numDoc))
			throw new NumeroException(numDoc);
		return getListeDocuments().get(numDoc);
	}
	
	// Ici on vérifie si on peut se munir du document (Uniquement DVD)
		public void verifAdulte(int numDoc, int numAb) throws AgeException{
			DVD dvd = (DVD)getListeDocuments().get(numDoc);
			Abonne ab = Service.getListeAbonnes().get(numAb);
			synchronized(this) {
				if((dvd.isAdulte() == true) && (ab.isAdulte() == false))
					throw new AgeException(dvd);
				}
			return;
		}
	
	public Abonne verifNumAb(int numAb) throws AbonneException{
		if(!Service.getListeAbonnes().containsKey(numAb))
			throw new AbonneException(numAb);
		return Service.getListeAbonnes().get(numAb);
	}

	@Override
	public void run() {
		System.err.println("Connexion " + getNumConnexion() + " établie (Réservation)");
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(getSocket().getInputStream ( )));
			PrintWriter out = new PrintWriter (getSocket().getOutputStream ( ), true);
			String line;
			IDocument document = null;
			Abonne abonne = null;
			int idC = -1;
			boolean quit = false;			
			line = "---- RESERVATION ----\n\n**Entrez 'q' pour quitter**\nEntrez votre numéro d'abonné : "; 
			out.println(line.replace("\n","##"));
			while(!quit) {
				line = in.readLine();
				if(line.equals("q")) {
					quit = true;
					break;
				}
				try {
				idC = Integer.parseInt(line);
				abonne = verifNumAb(idC);
				break;
				} catch (AbonneException e) {
					line = "\n" + e + "\nVous vous êtes sûrement trompé de numéro.\nEntrez de nouveau votre numéro d'abonné : ";
					out.println(line.replace("\n","##"));
				} catch(NumberFormatException e) {
					line = "\nSymbole '" + line + "' inconnu\nEntrez de nouveau votre numéro d'abonné : ";
					out.println(line.replace("\n","##"));
				}
			}

			if(!quit) {
				line = "\nVous êtes authentifié " + abonne.getNom() + " !\nEntrez le numéro du Document :";
				out.println(line.replace("\n","##"));
			}
			
			while(!quit) {
				line = in.readLine();
				if(line.equals("q")) {
					break;
				}
				try {
					Integer idDoc = Integer.parseInt(line);
					document = verifNumDoc(idDoc);
					verifAdulte(idDoc, abonne.getNumero());
					reserver(abonne, document);
					
					line = "\nReservation du Document '" + document + "' effectué\n"
							+ "Entrez un autre numéro de Document (Si vous voulez réserver un autre Document):";
					out.println(line.replace("\n","##"));
				} /*catch(EmpruntException e) {
					line = "\n" + e + "\nEntrez un autre numéro de Document (Si vous voulez réserver un autre Document):\"";
					out.println(line.replace("\n","##"));
				} */catch(NumberFormatException e) {
					line = "\nSymbole '" + line + "' inconnu\nEntrez de nouveau le numéro du Document : ";
					out.println(line.replace("\n","##"));
				} catch (NumeroException e) {
					line = "\n" + e + "\nEntrez de nouveau le numéro du Document : ";
					out.println(line.replace("\n","##"));
				} catch (ReservationException e) {
					line = "\n" + e + "\nEntrez de nouveau le numéro du Document : ";
					out.println(line.replace("\n","##"));
				} catch (AgeException e) {
					line = "\n" + e + "\nEntrez de nouveau le numéro du Document : ";
					out.println(line.replace("\n","##"));
				}
			}
			line = "\n**Vous avez quitté l'application**\n\n---- A BIENTOT ----";
			out.println(line.replace("\n","##"));
			//fin du service
			closeSocket("Réservation",getNumConnexion());
		} catch (IOException e) { // Le client s'est deco
			closeSocket("Réservation",getNumConnexion());
		}
	}
	

}
