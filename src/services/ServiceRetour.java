package services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import abstract_documents.IDocument;
import exceptions.NumeroException;
import exceptions.RetourException;

public class ServiceRetour extends Service implements Runnable{
	
	public ServiceRetour(Socket socket) {
		super(socket);
	}
	
	public IDocument verifNumDoc(int numDoc) throws NumeroException{
		if(!getListeDocuments().containsKey(numDoc))
			throw new NumeroException(numDoc);
		return getListeDocuments().get(numDoc);
	}
	
	public void retour(IDocument document) throws RetourException {
		document.retour();
	}

	
	public void run(){
		System.err.println("Connexion " + getNumConnexion() + " établie (Retour)");
		String line;
		int numDoc = -1;
		IDocument document = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
			
			line = "------ RETOUR ------\n\n**Entrez 'q' pour quitter**\nEntrez le numéro du Document : "; 
			out.println(line.replace("\n","##"));
			while(true) {
				line = in.readLine();
				if(line.equals("q")) {
					break;
				}
				try {
					numDoc = Integer.parseInt(line);
					document = verifNumDoc(numDoc);
					synchronized(document) {
						retour(document);
					}
					line = "\nRetour du Document '" + document + "' effectué\nEntrez un autre numéro de Document : ";
					out.println(line.replace("\n","##"));
				} catch(NumeroException e) { // Mauvais numero de retour
					line = "\n" + e + "\nEntrez de nouveau le numéro du Document : ";
					out.println(line.replace("\n","##"));
				} catch(NumberFormatException e) {
					line = "\nSymbole '" + line + "' inconnu\nEntrez de nouveau le numéro du Document : ";
					out.println(line.replace("\n","##"));
				}  catch(RetourException e) {
					line = "\n" + e + "\nEntrez un autre numéro de Document : ";
					out.println(line.replace("\n","##"));
				}
			}
			line = "\n**Vous avez quitté l'application**\n\n---- A BIENTOT ----";
			out.println(line.replace("\n","##"));
			closeSocket("Retour",getNumConnexion());
		} catch (IOException e) { // Le client s'est deco
			closeSocket("Retour",getNumConnexion());
		}
	}
}
