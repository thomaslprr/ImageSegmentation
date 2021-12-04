package graphe;

import java.util.ArrayList;

import parse.ImageInfo;

public class Graphe {
	
	private ArrayList<Sommet> sommets = new ArrayList<>();
	private Sommet premierSommet ;
	private Sommet dernierSommet ;
	private int largeur;
	private int nbSommet=0;
	

	public Graphe(ImageInfo imageInfo) {
		this.largeur = imageInfo.getNbColonnes();
		int nbPixels = imageInfo.getNbColonnes()*imageInfo.getNbLignes();
		nbSommet = nbPixels+2;
		
		Sommet s = new Sommet(0,new ArrayList<Arc>(),true,false,0,0);
		this.setPremierSommet(s);
		Sommet p = new Sommet(nbPixels+1,new ArrayList<Arc>(),false,true,0,0);
		this.setDernierSommet(p);
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
			arc.getSommetDestination().ajouterArc(new Arc(-arc.getCapacite(),0,this.premierSommet));
		}
	}


	public void elever(Sommet s) {
		Integer min =Integer.MAX_VALUE;
		
		if(s.isEstPuit()||s.isEstSource()) {
			return;
		}
		
		if(!(s.getExcedent()>0)) {
			return;
		}
		
		
		for(Arc a : s.getArcs()) {
			if((a.getFlot() != a.getCapacite())&& (a.getSommetDestination().getHauteur()<min)) {
				min = a.getSommetDestination().getHauteur();
				s.setHauteur(min+1);
			}
		}
		
	}
	
	public boolean peutAvancer(Sommet s) {
		
		if(s.isEstPuit()|| s.isEstSource()) {
			return false;
		}
		
		if(!(s.getExcedent()>0)) {
			return false;
		}
		

		for(Arc a : s.getArcs()) {
			
			
			if((s.getHauteur()>a.getSommetDestination().getHauteur())&&(a.getFlot() != a.getCapacite())) {
						
					int val = Math.min(s.getExcedent(), a.getCapacite()-a.getFlot());
					s.setExcedent(s.getExcedent()-val);
					a.setFlot(a.getFlot()+val);
					a.getSommetDestination().setExcedent(a.getSommetDestination().getExcedent()+val);
					modifierArcInverse(a, val);					
						
					return true;
			}
		}
		
		return false;
		
	}
	
	
	
	public int executerPreflot() {
		
		executerInitialisationPreflot();
		Sommet sommetActif = getSommetAvecExcedent();
		while (sommetActif != null) {
			if (!peutAvancer(sommetActif)) {
		        elever(sommetActif);
		    }
		    sommetActif = getSommetAvecExcedent();
		}

		return this.sommets.get(this.sommets.size()-1).getExcedent();		
		   
	}
	
	private void modifierArcInverse(Arc a, int flot) {
	    for (Arc ar : a.getSommetDestination().getArcs()) {
	      if (ar.getSommetDestination().equals(a.getSommetSource())) {
	        ar.setFlot(ar.getFlot()-flot);
	        return;
	      }
	    }

	    a.getSommetDestination().ajouterArc(new Arc(-flot,0,a.getSommetSource())); 
	}
	
	
	public Sommet getSommetAvecExcedent() {
	    for (int i = 1; i < this.sommets.size()-1; i++) {
	      if (this.sommets.get(i).getExcedent() > 0) {
	        return this.sommets.get(i);
	      }
	    }
	    return null;
	  }
	
	
	public ArrayList<Sommet> afficherSommetAccessible(){
		ArrayList<Sommet> listePlan1 = new ArrayList<>();
		this.recAfficherSommetAccessible(listePlan1,this.getPremierSommet());
		listePlan1.remove(this.getPremierSommet());
		listePlan1.remove(this.getDernierSommet());
		return listePlan1;
	}
	
	private void recAfficherSommetAccessible(ArrayList<Sommet> listeSommets,Sommet s) {
		for(Arc a : s.getArcs()) {
			if(a.getFlot()!=a.getCapacite()) {
				if(!listeSommets.contains(a.getSommetDestination())) {
					listeSommets.add(a.getSommetDestination());
					recAfficherSommetAccessible(listeSommets,a.getSommetDestination());			
				}
			}
		}
	}
	
	
	public static int[] getIndice(int largeur,int index){
		int[] res= new int[2];
		res[0] = index / largeur;
		res[1] = index % largeur;
		return res;
	}


	public Sommet getDernierSommet() {
		return dernierSommet;
	}


	public void setDernierSommet(Sommet dernierSommet) {
		this.dernierSommet = dernierSommet;
	}
	
	public void afficherPlans() {
		
		ArrayList<Sommet> premierPlan = afficherSommetAccessible();
		for(int i=1;i<this.sommets.size()-1;i++) {
			if(premierPlan.contains(this.sommets.get(i))) {
				System.out.print("O ");
			}else {
				System.out.print("- ");
			}
			if(i%largeur==0) {
				System.out.println();
			}
		}
		
	}
	
	public void afficherPlans(ArrayList<Sommet> premierPlan) {
			
			for(int i=1;i<this.sommets.size()-1;i++) {
				if(premierPlan.contains(this.sommets.get(i))) {
					System.out.print("O ");
				}else {
					System.out.print("- ");
				}
				if(i%largeur==0) {
					System.out.println();
				}
			}
			
	}
	
	public ArrayList<ArrayList<Sommet>> resoudreBinMin(){

		ArrayList<ArrayList<Sommet>> ensemblePixels =  new ArrayList<>();
		//ensemble1
		ArrayList<Sommet> premierPlan = afficherSommetAccessible();
		ensemblePixels.add(premierPlan);
		//ensemble 2
		ArrayList<Sommet> deuxiemePlan = new ArrayList<>(this.getSommets());
		
		deuxiemePlan.remove(this.getPremierSommet());
		deuxiemePlan.remove(this.getDernierSommet());
		deuxiemePlan.removeAll(premierPlan);
		ensemblePixels.add(deuxiemePlan);
		return ensemblePixels;
	}
	
	
}
