package com.formation.Toudoid;

import java.util.ArrayList;

public class Group {
	private String nom;
	private ArrayList<Task> objets;

	public Group(String nom) {
		super();
		this.nom = nom;
		this.objets = new ArrayList<Task>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Task> getObjets() {
		return objets;
	}

	public void setObjets(ArrayList<Task> objets) {
		this.objets = objets;
	}
	
	public void addTask(Task t) {
		objets.add(t);
	}
	
	public String toString(){
		String s = "";
		s += "Groupe : " + nom + "\n";
		for (int i=0 ; i<objets.size() ; i++){
			s += "tache : " + i + " : " + objets.get(i).getNom() + ", " + (objets.get(i).isChecked()) + "\n";
			
		}
		return s;
	}

}
