package com.formation.Toudoid;

public class Task {

	private Group groupe;
	private String nom;

	public Task(Group groupe, String nom) {
		super();
		this.groupe = groupe;
		this.nom = nom;
	}

	public Group getGroupe() {
		return groupe;
	}

	public void setGroupe(Group groupe) {
		this.groupe = groupe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
