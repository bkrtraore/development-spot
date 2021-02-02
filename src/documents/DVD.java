package documents;

import abstract_documents.Document;
import client.Abonne;
import exceptions.EmpruntException;
import exceptions.ReservationException;

public class DVD extends Document{
	private boolean adulte;
	
	public DVD(String titre, int numero, DocumentState document, boolean adulte) {
		super(titre, numero, document);
		this.adulte = adulte;
	}
	
	@Override
	public void reserve(Abonne ab) throws ReservationException{
		super.reserve(ab);
	}
	
	@Override
	public void emprunt(Abonne ab) throws EmpruntException {
		super.emprunt(ab);
	}

	public boolean isAdulte() {
		return adulte;
	}
	
	
}
