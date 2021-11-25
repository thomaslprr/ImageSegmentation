package parse;

public class ImageInfo {

	private int nbLignes;
	private int nbColonnes;
	private int[][] aValeurs;
	private int[][] bValeurs;
	private int[][] verticalePenalites;
	private int[][] horizontalePenalites;
	
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
	
	public String toString() {
		String s = "";
		s="AFFICHAGE PROBA A \n";
		for(int i=0;i<this.getNbLignes();i++) {
			for(int j=0;j<this.getNbColonnes();j++) {
				s+= this.getaValeurs()[i][j]+" ";
			}
			s+="\n";
		}
		s+= "\nAFFICHAGE PROBA B \n";
		for(int i=0;i<this.getNbLignes();i++) {
			for(int j=0;j<this.getNbColonnes();j++) {
				s+= this.getbValeurs()[i][j]+" ";
			}
			s+="\n";
		}
		s+= "\nAFFICHAGE PENALITES HORIZONTALES \n";
		for(int i=0;i<this.getHorizontalePenalites().length;i++) {
			for(int j=0;j<this.getHorizontalePenalites()[1].length;j++) {
				s+= this.getHorizontalePenalites()[i][j]+" ";
			}
			s+="\n";
		}
		s+= "\nAFFICHAGE PENALITES VERTICALES \n";
		for(int i=0;i<this.getVerticalePenalites().length;i++) {
			for(int j=0;j<this.getVerticalePenalites()[1].length;j++) {
				s+= this.getVerticalePenalites()[i][j]+" ";
			}
			s+="\n";
		}
		return s;
	}
	
}
