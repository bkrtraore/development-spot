package exceptions;

import abstract_documents.IDocument;

public class EmpruntException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDocument document;
	private int numAb = -1;
	
	public EmpruntException(IDocument document) {
		this.document = document;
	}
	
	public EmpruntException(IDocument document, int numAb) {
		this.document = document;
		this.numAb = numAb;
	}
	public String toString() {
		String s = "Le Document '" + this.document + "' ne peut être emprunté";
		
		if (numAb >= 0) {
			//s+= " il a été réservé jusqu'à : "+""; 
			try {
				throw new ReservationException(document);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return s;
	}
}
