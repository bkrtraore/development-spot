package abstract_documents;

import client.Abonne;
import documents.DocumentState;
import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;
import services.Service;

public abstract class Document implements IDocument{
	private DocumentState state;
	private int numero;
	private String titre;
	
	public Document(String titre, int numero, DocumentState document) {
		this.state = document;
		this.titre = titre;
		this.numero = numero;
		Service.getListeDocuments().put(numero, this);
	}
	
	@Override
	public void retour() throws RetourException {
		this.state = state.retour(this);
	}
	
	@Override
	public int numero() {
		return this.numero;
	}
	
	public String getTitre() {
		return this.titre;
	}
	
	public DocumentState getState() {
		return this.state;
	}
	
	public void setState(DocumentState state) {
		this.state = state;
	}
	
	@Override
	public void emprunt(Abonne abonne) throws EmpruntException {
		this.state = state.emprunt(this,abonne);
	}
	
	@Override
	public void reserve(Abonne abonne) throws ReservationException {
			this.state = state.reserve(this,abonne);
	}
	
	public String getStateName() {
		return this.state.toString();
	}
	
	public String toString() {
		return this.getTitre();
	}
}
