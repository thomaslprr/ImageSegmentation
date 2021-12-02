package parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileParser {
		
	public static ImageInfo parse(File fichier) throws FileNotFoundException {
		
		ImageInfo g = new ImageInfo();
		
		Scanner sc = new Scanner(fichier);
		
		int cpt=1;
		int tab=0;
		int tabValue = 0;

		int[][] tableau1 = new int[0][0];
		int[][] tableau2 = new int[0][0];
		int[][] tableau3 = new int[0][0];
		int[][] tableau4 = new int[0][0];

		
	    while (sc.hasNextLine()) {
	    	String line = sc.nextLine() ;
	    	if(cpt==1) {
	    		String[] dimensions = line.split(" ");
	    		g.setNbLignes(Integer.parseInt(dimensions[0]));
	    		g.setNbColonnes(Integer.parseInt(dimensions[1]));
	    		tableau1 = new int[g.getNbLignes()][g.getNbColonnes()] ;
	    		tableau2 = new int[g.getNbLignes()][g.getNbColonnes()] ;
	    		tableau3 = new int[g.getNbLignes()][g.getNbColonnes()-1] ;
	    		tableau4 = new int[g.getNbLignes()-1][g.getNbColonnes()] ;
	    	}
	    	
	    	
	    	if(line.trim().equals("")) {
	    		tab++;
	    		tabValue =0;
	    	}else {
	    		String[] valeurs;
	    		switch(tab) {
	    		case 1:
	    			valeurs = line.split(" ");
		    		for(int i=0 ;i<valeurs.length;i++) {
		    			tableau1[tabValue][i] = Integer.parseInt(valeurs[i]);
		    		}
		    		tabValue++;
		    		g.setaValeurs(tableau1);
	    			break;
	    		case 2:
	    			valeurs = line.split(" ");
		    		for(int i=0 ;i<valeurs.length;i++) {
		    			tableau2[tabValue][i] = Integer.parseInt(valeurs[i]);
		    		}
		    		tabValue++;
		    		g.setbValeurs(tableau2);
	    			break;
	    		case 3: 
	    			valeurs = line.split(" ");
		    		for(int i=0 ;i<valeurs.length;i++) {
		    			tableau3[tabValue][i] = Integer.parseInt(valeurs[i]);
		    		}
		    		tabValue++;
		    		g.setHorizontalePenalites(tableau3);
	    			break;
	    		case 4:
	    			valeurs = line.split(" ");
		    		for(int i=0 ;i<valeurs.length;i++) {
		    			tableau4[tabValue][i] = Integer.parseInt(valeurs[i]);
		    		}
		    		tabValue++;
		    		g.setVerticalePenalites(tableau4);
	    			break;
	    		}
	    			
	    	}
		    		
		      cpt++;
  			

	    }
		
		sc.close();
	    
	    return g;
		
	}

}
