package exceptions;

import abstract_documents.*;

public class ReservationException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDocument document;
	
	public ReservationException(IDocument document) {
		this.document = document;
	}
	public String toString() {
		String s = "Le Document '" + this.document + "' ne peut être réserver.";
		return s;
	}
}
