package graphe;

import java.util.ArrayList;

import parse.ImageInfo;

public class Graphe {
	
	private ArrayList<Sommet> sommets = new ArrayList<>();
	private Sommet premierSommet ;
	private int largeur;
	private int nbSommet=0;
	private int[][] grapheResiduel;
	

	public Graphe(ImageInfo imageInfo) {
		this.largeur = imageInfo.getNbColonnes();
		int hauteur = imageInfo.getNbLignes();
		int nbPixels = imageInfo.getNbColonnes()*imageInfo.getNbLignes();
		nbSommet = nbPixels+2;
		
		Sommet s = new Sommet(0,new ArrayList<Arc>(),true,false,0,0);
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
		sommets.add(0, s);
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
	
	public void executerInitialisationPreflot() {
		this.premierSommet.setHauteur(nbSommet);
		for(Arc arc : this.premierSommet.getArcs()) {
			arc.setFlot(arc.getCapacite());
			arc.getSommetDestination().setExcedent(arc.getCapacite());
		}
		setGrapheResiduel();
	}
	
	public void setGrapheResiduel(){
		int[][] grapheResiduel = new int[sommets.size()][sommets.size()];
		for(int i = 0 ; i<sommets.size();i++) {
			System.out.println("SOMMET : "+i);
			for(Arc arc : sommets.get(i).getArcs()) {
				System.out.println("ARC : de "+i+" vers "+arc.getSommetDestination().getId()+" val : "+arc.getCapacite());
					grapheResiduel[i][arc.getSommetDestination().getId()]= arc.getCapacite()-arc.getFlot();
					grapheResiduel[arc.getSommetDestination().getId()][i]= -arc.getFlot();
			}
		}
		this.grapheResiduel = grapheResiduel;
	}
	
	public void setGrapheResiduel(int[][] grapheResiduel){
		this.grapheResiduel = grapheResiduel;
	}
	
	
	public int[][] getGrapheResiduel() {
		return grapheResiduel;
	}


	public void afficherGrapheResiduel(){
		int[][] gr = this.grapheResiduel;
		for(int i =0 ; i< sommets.size();i++) {
			for(int j=0; j<sommets.size();j++) {
				System.out.print(gr[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	
	public boolean estElevable(Sommet s) {
		Integer min =null;
		
		if(!(s.getExcedent()>0)) {
			return false;
		}
		
		
		for(int i =0 ; i<this.grapheResiduel[1].length;i++) {
			if(this.grapheResiduel[s.getId()][i]>0) {
				if(s.getHauteur()<= sommets.get(i).getHauteur()) {
					if(min==null || sommets.get(i).getHauteur()<min) {
						min= sommets.get(i).getHauteur()+1;
					}
				}
			}
		}
		
		if(min!=null) {
			s.setHauteur(min+1);
			return true;
		}
		
		return false;
	}
	
	public boolean peutAvancer(Sommet s,Arc a) {
		
		if(!(s.getExcedent()>0)) {
			return false;
		}
		
		if(!(s.getHauteur()==a.getSommetDestination().getHauteur())) {
			return false;
		}
		
		if(!(this.grapheResiduel[s.getId()][a.getSommetDestination().getId()]>0)) {
			return false;
		}
		
		int val = Math.min(s.getExcedent(), a.getCapacite());
		a.setFlot(val);
		s.setExcedent(s.getExcedent()-val);
		a.getSommetDestination().setExcedent(a.getSommetDestination().getExcedent()+val);
		
		this.grapheResiduel[s.getId()][a.getSommetDestination().getId()]-= val ;
		this.grapheResiduel[a.getSommetDestination().getId()][s.getId()]+= val ;
		
		return true;

	}
	
	
	
	public static int[] getIndice(int largeur,int index){
		int[] res= new int[2];
		res[0] = index / largeur;
		res[1] = index % largeur;
		return res;
	}
	
}
