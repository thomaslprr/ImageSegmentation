package graphe;

import java.util.ArrayList;

import parse.ImageInfo;

public class Graphe {
	
	private ArrayList<Sommet> sommets = new ArrayList<>();
	private Sommet premierSommet ;
	private int largeur;
	

	public Graphe(ImageInfo imageInfo) {
		this.largeur = imageInfo.getNbColonnes();
		int hauteur = imageInfo.getNbLignes();
		int nbPixels = imageInfo.getNbColonnes()*imageInfo.getNbLignes();
		
		Sommet s = new Sommet(0,new ArrayList<Arc>(),true,false,0,0);
		sommets.add(s);
		this.setPremierSommet(s);
		Sommet p = new Sommet(nbPixels+1,new ArrayList<Arc>(),false,true,0,0);
		//Pour tous les pixels
		for(int i=0;i<nbPixels;i++) {
			Sommet pixel = new Sommet(i+1,new ArrayList<Arc>(),false,false,0,0);
			sommets.add(pixel);
			
		}
		
		//Pour tous les pixels
		for(int i=0;i<nbPixels;i++) {
			int[] doubleIndice = getIndice(largeur,i);
			s.ajouterArc(new Arc(0,imageInfo.getaValeurs()[doubleIndice[0]][doubleIndice[1]],sommets.get(i)));
			sommets.get(i).ajouterArc(new Arc(0,imageInfo.getbValeurs()[doubleIndice[0]][doubleIndice[1]],p));
			
			//on ajoute le voisin droite s'il existe
			if((i%largeur)+1<largeur) {
				sommets.get(i).ajouterArc(new Arc(0,imageInfo.getHorizontalePenalites()[doubleIndice[0]][doubleIndice[1]],sommets.get(i+1)));
			}
			
			//on ajoute le voisin gauche s'il existe
			if((i%largeur)-1>=0) {
				sommets.get(i).ajouterArc(new Arc(0,imageInfo.getHorizontalePenalites()[doubleIndice[0]][doubleIndice[1]-1],sommets.get(i-1)));				
			}
			
			//on ajoute le voisin bas s'il existe
			if(i<nbPixels-largeur) {
				sommets.get(i).ajouterArc(new Arc(0,imageInfo.getVerticalePenalites()[doubleIndice[0]][doubleIndice[1]],sommets.get(i+largeur)));
			}
			
			//on ajoute le voisin haut s'il existe
			if(i>=largeur) {
				sommets.get(i).ajouterArc(new Arc(0,imageInfo.getVerticalePenalites()[doubleIndice[0]-1][doubleIndice[1]],sommets.get(i-largeur)));
			}
					
		}
		sommets.add(p);
	}
	
	
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
	
	
	public static int[] getIndice(int largeur,int index){
		int[] res= new int[2];
		res[0] = index / largeur;
		res[1] = index % largeur;
		return res;
	}
	
}
