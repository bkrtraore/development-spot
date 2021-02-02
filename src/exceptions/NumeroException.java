package exceptions;

public class NumeroException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numero;
	
	public NumeroException(int numero) {
		this.numero = numero;
	}
	public String toString() {
		return "Le Document " + this.numero + " n'existe pas";
	}
}
