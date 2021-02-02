package exceptions;

import abstract_documents.IDocument;

public class RetourException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDocument document;
	
	public RetourException(IDocument document) {
		this.document = document;
	}
	public String toString() {
		return "Le Document '" + this.document + "' a déjà été retourné (Retour annulé)";
	}
}
