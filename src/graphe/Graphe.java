package graphe;

import java.util.ArrayList;

public class Graphe {
	
	private ArrayList<Sommet> sommets ;
	private Sommet premierSommet ;
	

	
	
	
	public ArrayList<Sommet> getSommets() {
		return sommets;
	}
	public void setSommets(ArrayList<Sommet> sommets) {
		this.sommets = sommets;
	}
	public Sommet getPremierSommet() {
		return premierSommet;
	}
	public void setPremierSommet(Sommet premierSommet) {
		this.premierSommet = premierSommet;
	}
	
	
}
