package com.formation.Toudoid;

public class Task {

	private Group groupe;
	private String nom;
	private boolean isChecked;

	public Task(Group groupe, String nom, boolean check) {
		super();
		this.groupe = groupe;
		this.nom = nom;
		this.isChecked = check;
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
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean c) {
		isChecked = c;
	}

}
