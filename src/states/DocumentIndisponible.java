package states;
import abstract_documents.IDocument;
import client.Abonne;
import documents.DocumentState;
import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;

public class DocumentIndisponible implements DocumentState {
	private Abonne abonne;
	
	public DocumentIndisponible(Abonne abonne) {
		this.abonne = abonne;
	}
	
	@Override
	public DocumentState retour(IDocument doc) throws RetourException{
		synchronized(doc) { doc.notifyAll(); }
		return new DocumentDisponible();
	}

	@Override
	public DocumentState emprunt(IDocument doc, Abonne ab) throws EmpruntException {
		throw new EmpruntException(doc);
	}
	
	@Override
	public DocumentState reserve(IDocument doc, Abonne ab) throws ReservationException {
		throw new ReservationException(doc);
	}
	
	public Abonne getAbonne() {
		return abonne;
	}
}
