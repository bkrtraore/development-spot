package serveur;

import java.io.IOException;

import factory.ServeurFactory;

public class Médiathèque {
	private Factory serveurFactory = new ServeurFactory();
	
	public Runnable lancerServeur(int port) throws IOException {
		return serveurFactory.creerServeur(port);
	}
	
}
