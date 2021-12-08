package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import graphe.Graphe;
import graphe.Sommet;
import parse.FileParser;
import parse.ImageInfo;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
	
		if (args.length < 2) {
			System.out.println("Must give a file path as first argument and algorithm method in second argument");
		} else {
			if(!args[1].equals("-p") && !args[1].equals("-a")) {
				System.out.println("Must have -a or -p in second argument");
				return;
			}
			
			File file = new File(args[0]);
			ImageInfo imageInfo = FileParser.parse(file);
			
			if(args.length == 3 && args[2].equals("--info")) {
				System.out.println(imageInfo);
			}
			
			Graphe g = new Graphe(imageInfo);
			System.out.println("FLOT MAXIMUM");
			System.out.println(g.calculFlotMax());
			
			if(args[1].equals("-a")) {
				ArrayList<HashSet<Sommet>> binmin = g.resoudreBinMin();
				
				System.out.println("PIXEL PREMIER PLAN");
				for(Sommet s : binmin.get(0)) {
					System.out.print(s.getId()+" ");
				}
				System.out.println();
				System.out.println("PIXEL DEUXIEME PLAN");
				for(Sommet s : binmin.get(1)) {
					System.out.print(s.getId()+" ");
				}
				System.out.println();
				System.out.println("AFFICHAGE DES PLANS");
				g.afficherPlans(binmin.get(0));
			}else {
				System.out.println();
				System.out.println("AFFICHAGE DES PLANS");
				g.afficherPlans();
			}
					
		}
	}

}
