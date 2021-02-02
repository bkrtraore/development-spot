package factory;

import java.io.IOException;
import serveur.Factory;
import serveur.ServeurEmprunt;
import serveur.ServeurReservation;
import serveur.ServeurRetour;


public class ServeurFactory implements Factory{

	@Override
	public Runnable creerServeur(int port) throws IOException {
		Runnable serveur = null;
		if(port == 3000)
			serveur = new ServeurReservation(port);
		else if(port == 4000)
			serveur = new ServeurEmprunt(port);
		else if(port == 5000)
			serveur = new ServeurRetour(port);
		else
			throw new RuntimeException("Les ports de service disponibles sont ceux-ci uniquement : 3000 ; 4000 ; 5000");
		return serveur;
	}

	
	
	

}
