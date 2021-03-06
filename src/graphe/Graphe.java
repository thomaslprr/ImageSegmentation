package graphe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import parse.ImageInfo;

public class Graphe {
	
	private ArrayList<Sommet> sommets = new ArrayList<>();
	private Sommet premierSommet ;
	private Sommet dernierSommet ;
	private int largeur;

	/**
	 * Constructeur d'un graphe
	 * @param imageInfo 
	 */
	public Graphe(ImageInfo imageInfo) {
		this.largeur = imageInfo.getNbColonnes();
		constructionReseau(imageInfo);
		
	}
	
	private void constructionReseau(ImageInfo imageInfo) {
		int nbPixels = imageInfo.getNbColonnes()*imageInfo.getNbLignes();

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
	
	//IMPLEMENTATION DE L'ALGORITHME DES PREFLOTS
	
	/**
	 * Initialise le graphe pour executer les preflots
	 */
	public void executerInitialisationPreflot() {
		//on change la hauteur du sommet source
		this.premierSommet.setHauteur(sommets.size());
		for(Arc arc : this.premierSommet.getArcs()) {
			//on envoie sur les arcs voisins de la source tout ce que l'on peut
			arc.setFlot(arc.getCapacite());
			//on change l'exedent des sommets voisins de la source
			arc.getSommetDestination().setExcedent(arc.getCapacite());
			//on ajoute un arc r??siduel
			arc.getSommetDestination().ajouterArc(new Arc(-arc.getCapacite(),0,this.premierSommet));
		}
	}

	/**
	 * M??thode qui permet d'??lever le sommet d'un graphe
	 * @param s Sommet
	 */
	public void elever(Sommet s) {
		Integer min =Integer.MAX_VALUE;
		
		//on verifie qu'il ne s'agit pas du sommet source ou puit
		if(s.isEstPuit()||s.isEstSource()) {
			return;
		}
		
		//on verifie que le sommet a de l'exedent
		if(!(s.getExcedent()>0)) {
			return;
		}		
		
		for(Arc a : s.getArcs()) {
			if((a.getFlot() != a.getCapacite()) && (a.getSommetDestination().getHauteur()<min)) {
				min = a.getSommetDestination().getHauteur();
				s.setHauteur(min+1);
			}
		}
		
	}
	
	/**
	 * Methode qui fait avancer tout l'excedent possible contenu dans le sommet pass?? en param??tre
	 * @param s Sommet
	 * @return TRUE s'il a ??t?? possible de faire avancer de l'excedent, FALSE sinon.
	 */
	public boolean peutAvancer(Sommet s) {
		
		//on verifie qu'il ne s'agit pas du sommet source ou puit
		if(s.isEstPuit()|| s.isEstSource()) {
			return false;
		}
		
		//on verifie que le sommet a de l'exedent
		if(!(s.getExcedent()>0)) {
			return false;
		}		

		for(Arc a : s.getArcs()) {		
			if((s.getHauteur()>a.getSommetDestination().getHauteur())&&(a.getFlot() != a.getCapacite())) {
				//on r??cup??re la quantit?? maximum qu'il est possible de faire circuler
				int val = Math.min(s.getExcedent(), a.getCapacite()-a.getFlot());
				//on retire l'excedent au sommet
				s.setExcedent(s.getExcedent()-val);
				//on ajoute l'excedent au flot de l'arc
				a.setFlot(a.getFlot()+val);
				//on ajoute l'excedent en plus de celui contenu au sommet de destination
				a.getSommetDestination().setExcedent(a.getSommetDestination().getExcedent()+val);
				//on met ?? jour l'arc r??siduel
				modifierArcInverse(a, val);		
				
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * M??thode pour ex??cuter l'algorithme des pr??flots 
	 * @return Le flot maximum
	 */
	public int calculFlotMax() {
		
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
	
	/**
	 * M??thode qui met ?? jour l'arc r??siduel
	 * @param a Arc 
	 * @param flot Flot
	 */
	private void modifierArcInverse(Arc a, int flot) {
		//on regarde si on a d??j?? un arc r??siduel si oui on le met ?? jour
	    for (Arc ar : a.getSommetDestination().getArcs()) {
	      if (ar.getSommetDestination().equals(a.getSommetSource())) {
	        ar.setFlot(ar.getFlot()-flot);
	        return;
	      }
	    }
	    //si on a pas d'arc r??siduel alors on en cr???? un
	    a.getSommetDestination().ajouterArc(new Arc(-flot,0,a.getSommetSource())); 
	}
	
	/**
	 * Methode qui renvoie un sommet qui a de l'excedent
	 * @return Sommet avec de l'excedent
	 */
	public Sommet getSommetAvecExcedent() {
	    for (int i = 1; i < this.sommets.size()-1; i++) {
	      if (this.sommets.get(i).getExcedent() > 0) {
	        return this.sommets.get(i);
	      }
	    }
	    return null;
	}
	
	//GESTION DE LA SOLUTION
	
	/**
	 * M??thode qui retourne les sommets de la coupe minimum
	 * @return liste des sommets de la coupe minimum
	 */
	public HashSet<Sommet> calculCoupeMin(){
		HashSet<Sommet> listePlan1 = new HashSet<Sommet>();
		this.recAfficherSommetAccessible(listePlan1,this.getPremierSommet());
		//on supprime le sommet source
		listePlan1.remove(this.getPremierSommet());
		//on supprime le sommet puit
		listePlan1.remove(this.getDernierSommet());
		return listePlan1;
	}
	
	private void recAfficherSommetAccessible(HashSet<Sommet> listeSommets,Sommet s) {
		for(Arc a : s.getArcs()) {
			if(a.getFlot()!=a.getCapacite()) {
				//si le sommet n'est pas d??j?? pr??sent dans la liste
				if(!listeSommets.contains(a.getSommetDestination())) {
					listeSommets.add(a.getSommetDestination());
					recAfficherSommetAccessible(listeSommets,a.getSommetDestination());			
				}
			}
		}
	}
	
	/**
	 * M??thode qui affiche la segmentation de l'image et qui permet
	 *  ainsi de diff??rencier les deux plans
	 *  
	 *  La m??thode calcule les sommets pr??sents dans le premier plan
	 */
	public void afficherPlans() {
		HashSet<Sommet> premierPlan = calculCoupeMin();
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
	
	/**
	 * M??thode qui affiche la segmentation de l'image et qui permet
	 *  ainsi de diff??rencier les deux plans
	 *  
	 *  La m??thode n'a pas a calculer les sommets pr??sents dans le premier plan
	 * @param premierPlan Sommets pr??sents dans le premier plan
	 */
	public void afficherPlans(HashSet<Sommet> premierPlan) {
			
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
	
	/**
	 * Methode qui renvoie la liste des sommets pr??sents dans le premier plan
	 * et la liste des sommets pr??sents dans le deuxi??me plan
	 */
	public ArrayList<HashSet<Sommet>> resoudreBinMin(){

		ArrayList<HashSet<Sommet>> ensemblePixels =  new ArrayList<>();
		//ensemble1
		HashSet<Sommet> premierPlan = calculCoupeMin();
		ensemblePixels.add(premierPlan);
		//ensemble 2
		HashSet<Sommet> deuxiemePlan = new HashSet<Sommet>(this.getSommets());
		
		//on supprime le sommet source
		deuxiemePlan.remove(this.getPremierSommet());
		//on supprime le sommet puit
		deuxiemePlan.remove(this.getDernierSommet());
		deuxiemePlan.removeAll(premierPlan);
		ensemblePixels.add(deuxiemePlan);
		return ensemblePixels;
	}
	
	// Definition des getters et des setters 
	
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
	
	public Sommet getDernierSommet() {
		return dernierSommet;
	}

	public void setDernierSommet(Sommet dernierSommet) {
		this.dernierSommet = dernierSommet;
	}
	
	/**
	 * Transforme un indice tableau 1D en deux indices pour tableaux ?? 2D
	 * @param largeur
	 * @param index
	 * @return tableau avec l'indice de la ligne et l'indice de la colonne
	 */
	public static int[] getIndice(int largeur,int index){
		int[] res= new int[2];
		res[0] = index / largeur;
		res[1] = index % largeur;
		return res;
	}
	
}
