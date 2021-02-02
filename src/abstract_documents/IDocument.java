package abstract_documents;

import client.Abonne;
import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;

public interface IDocument {
	int numero();
	void reserve(Abonne ab) throws ReservationException;
	void emprunt(Abonne ab) throws EmpruntException;
	void retour() throws RetourException;
}
