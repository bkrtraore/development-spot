package services;

import java.util.TimerTask;

import abstract_documents.Document;
import abstract_documents.IDocument;
import client.Abonne;
import states.DocumentDisponible;
import states.DocumentReserve;

public class TimerTaskReservation extends TimerTask {
	private IDocument doc;
	private Abonne ab;
	
	public TimerTaskReservation(IDocument doc, Abonne ab) {
		this.doc = doc;
		this.ab = ab;
	}
	
	
	@Override
	public void run() {
		Document document = (Document) doc;
		synchronized(doc) {
			if(document.getState() instanceof DocumentReserve) {
				if(document.getState().getAbonne().getNumero() == ab.getNumero())
					document.setState(new DocumentDisponible());
			}
		}
	}

}
