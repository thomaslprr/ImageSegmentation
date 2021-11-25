package graphe;

import java.util.ArrayList;

public class Sommet {
	
	private int id;
	private ArrayList<Arc> arcs = new ArrayList<>();
	private boolean estSource;
	private boolean estPuit ;
	private int aProba ;
	private int bProba ;
	private int hauteur;
	private int excedent;
	

	public Sommet(int id, ArrayList<Arc> arcs, boolean estSource, boolean estPuit, int aProba, int bProba) {
		this.id = id;
		this.arcs = arcs;
		this.estSource = estSource;
		this.estPuit = estPuit;
		this.aProba = aProba;
		this.bProba = bProba;
		this.hauteur= 0;
		this.excedent=0;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ArrayList<Arc> getArcs() {
		return arcs;
	}


	public void setArcs(ArrayList<Arc> arcs) {
		this.arcs = arcs;
	}


	public boolean isEstSource() {
		return estSource;
	}


	public void setEstSource(boolean estSource) {
		this.estSource = estSource;
	}


	public boolean isEstPuit() {
		return estPuit;
	}


	public void setEstPuit(boolean estPuit) {
		this.estPuit = estPuit;
	}


	public int getaProba() {
		return aProba;
	}


	public void setaProba(int aProba) {
		this.aProba = aProba;
	}


	public int getbProba() {
		return bProba;
	}


	public void setbProba(int bProba) {
		this.bProba = bProba;
	}
	
	public void ajouterArc(Arc a) {
		this.arcs.add(a);
	}


	public int getHauteur() {
		return hauteur;
	}


	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}


	public int getExcedent() {
		return excedent;
	}


	public void setExcedent(int excedent) {
		this.excedent = excedent;
	}	

}
