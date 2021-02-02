package client;

import services.Service;

import java.time.*;

public class Abonne {
	private int numero;
	private String nom;
	private LocalDate dateNaissance;
	private boolean adulte;

	public Abonne(int numero, String nom, LocalDate dateNaissance) {
		this.numero = numero;
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adulte = this.calculMajorité();
		Service.getListeAbonnes().put(numero,this);
	}

	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public boolean isAdulte() {
		return adulte;
	}

	// Retourne si l'abonné est considéré adulte en comparant la date courante à la
	// date de naissance
	private boolean calculMajorité() {

		LocalDate now = LocalDate.now();
		LocalDate birth = this.dateNaissance;

		Period period = Period.between(birth, now);

		int diff = period.getYears();

		if (diff >= 16) {
			return this.adulte = true;
		}
		return this.adulte = false;
	}

}
