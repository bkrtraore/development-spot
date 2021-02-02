package serveur;

import java.io.IOException;

public interface Factory {
	
	Runnable creerServeur(int port) throws IOException;
	

}
