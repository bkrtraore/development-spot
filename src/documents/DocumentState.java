package documents;
import abstract_documents.IDocument;
import client.Abonne;
import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;

public interface DocumentState {
	DocumentState retour(IDocument doc) throws RetourException;
	DocumentState emprunt(IDocument doc, Abonne ab) throws EmpruntException;
	DocumentState reserve(IDocument doc, Abonne ab) throws ReservationException;
	Abonne getAbonne();
}
