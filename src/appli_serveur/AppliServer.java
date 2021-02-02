package appli_serveur;
import java.io.IOException;
import java.time.LocalDate;

import client.Abonne;
import documents.DVD;
import serveur.M�diath�que;
import states.DocumentDisponible;

public class AppliServer {

	public static void main(String[] args) {
		M�diath�que biblio = new M�diath�que();
		new DVD("La petite Sir�ne", 0, new DocumentDisponible(), false);
		new DVD("Jigsaw", 1, new DocumentDisponible(), true);
		new DVD("Le roi lion", 2, new DocumentDisponible(), false);
		new Abonne(0, "Bakari", LocalDate.of(2000, 9, 21));
		new Abonne(1, "Ivan", LocalDate.of(2000, 1, 10));
		new Abonne(2, "Yannis", LocalDate.of(2007, 1, 17));
		try {
			new Thread(biblio.lancerServeur(3000)).start();
			new Thread(biblio.lancerServeur(4000)).start();
			new Thread(biblio.lancerServeur(5000)).start();
		} catch (IOException e) {
				System.err.println("Erreur lors de la cr�ation du serveur, veuillez red�marrez SVP...");
		}
	}
}
