package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import graphe.Graphe;
import graphe.Sommet;
import parse.FileParser;
import parse.ImageInfo;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
	
		if (args.length < 1) {
			System.out.println("Must give a file path as argument");
		} else {
			File file = new File(args[0]);
			ImageInfo imageInfo = FileParser.parse(file);
			System.out.println(imageInfo);
			Graphe g = new Graphe(imageInfo);
			System.out.println("FLOT MAXIMUM");
			System.out.println(g.executerPreflot());
			System.out.println("AFFICHAGE DES PLANS");
			g.afficherPlans();
		}
	}

}
