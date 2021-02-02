package exceptions;

import abstract_documents.IDocument;

public class AgeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDocument document;
	
	public AgeException(IDocument document) {
		this.document = document;
	}
	public String toString() {
		return "Vous n'êtes pas autorisé à vous munir du document '" + this.document + "' en raison de votre âge.";
	}

}
