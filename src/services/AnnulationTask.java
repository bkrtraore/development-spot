package services;
import java.util.TimerTask;

import abstract_documents.IDocument;
import client.Abonne;
import exceptions.EmpruntException;
import exceptions.RetourException;
public class AnnulationTask extends TimerTask{
	IDocument doc;
	Abonne a;
	public AnnulationTask(IDocument d, Abonne ab) {
		this.doc = d;
		this.a= ab;
		
	}
	@Override
	public void run() {

			try {
				doc.emprunt(a);
			}
			catch (EmpruntException e) {
				return;
				//Si l'exception est lev� alors le document a d�ja �t� empreint�
			}//sinon la r�servation doit �tre annul�e
			try {
				doc.retour();
			} catch (RetourException e) {
				System.err.println("Annulation de r�servation impossible");
			}
				
			
		
		
		
	}

}
