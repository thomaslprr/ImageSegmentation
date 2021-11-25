package graphe;

import java.util.ArrayList;

public class Graphe {
	
	private ArrayList<Sommet> sommets ;
	private Sommet premierSommet ;
	private int nbLignes;
	private int nbColonnes;
	private int[][] aValeurs;
	private int[][] bValeurs;
	private int[][] verticalePenalites;
	private int[][] horizontalePenalites;
	
	
	
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
	public int getNbLignes() {
		return nbLignes;
	}
	public void setNbLignes(int nbLignes) {
		this.nbLignes = nbLignes;
	}
	public int getNbColonnes() {
		return nbColonnes;
	}
	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}
	public int[][] getaValeurs() {
		return aValeurs;
	}
	public void setaValeurs(int[][] aValeurs) {
		this.aValeurs = aValeurs;
	}
	public int[][] getbValeurs() {
		return bValeurs;
	}
	public void setbValeurs(int[][] bValeurs) {
		this.bValeurs = bValeurs;
	}
	public int[][] getVerticalePenalites() {
		return verticalePenalites;
	}
	public void setVerticalePenalites(int[][] verticalePenalites) {
		this.verticalePenalites = verticalePenalites;
	}
	public int[][] getHorizontalePenalites() {
		return horizontalePenalites;
	}
	public void setHorizontalePenalites(int[][] horizontalePenalites) {
		this.horizontalePenalites = horizontalePenalites;
	} 
	
	
	
	
	
	

}
