package states;

import java.util.Date;
import java.util.Timer;

import abstract_documents.IDocument;
import client.Abonne;
import documents.DocumentState;
import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;
import minuteurs.DateMinuteur;
import services.TimerTaskReservation;

public class DocumentDisponible implements DocumentState{

	@Override
	public DocumentState retour(IDocument doc) throws RetourException{
		throw new RetourException(doc);
	}

	@Override
	public DocumentState emprunt(IDocument doc, Abonne ab) throws EmpruntException {
		return new DocumentIndisponible(ab);
	}

	@Override
	public DocumentState reserve(IDocument doc, Abonne ab) throws ReservationException{
		Timer chrono = new Timer();
		chrono.schedule(new TimerTaskReservation(doc,ab), DateMinuteur.addHours(new Date(), 2)); //2h de reservation
		return new DocumentReserve(ab);
	}

	@Override
	public Abonne getAbonne() {
		return null;
	}
	
}
