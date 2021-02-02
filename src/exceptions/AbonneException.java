package exceptions;


public class AbonneException extends Exception {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int numAb;
		
		public AbonneException(int numAb) {
			this.numAb = numAb;
		}
		public String toString() {
			return "L'abonné n° " + this.numAb + " n'existe pas";
		}
}
