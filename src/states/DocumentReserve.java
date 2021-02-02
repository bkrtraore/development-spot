package states;


import abstract_documents.IDocument;
import client.Abonne;
import documents.DocumentState;
import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;

public class DocumentReserve implements DocumentState{
	private Abonne abonne;
	
	public DocumentReserve(Abonne abonne) {
		this.abonne = abonne;
	}
	
	@Override
	public DocumentState retour(IDocument doc) throws RetourException {
		throw new RetourException(doc);
	}

	@Override
	public DocumentState emprunt(IDocument doc, Abonne ab) throws EmpruntException {
		if(ab.getNumero() != abonne.getNumero())
			throw new EmpruntException(doc);
		return new DocumentIndisponible(ab);
	}

	@Override
	public DocumentState reserve(IDocument doc, Abonne ab) throws ReservationException {
		throw new ReservationException(doc);
	}

	@Override
	public Abonne getAbonne() {
		return this.abonne;
	}
}
