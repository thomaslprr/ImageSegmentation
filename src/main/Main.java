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
<<<<<<< HEAD
		
		File file = new File(args[0]);
		ImageInfo imageInfo = FileParser.parse(file);
		System.out.println(imageInfo);
		Graphe g = new Graphe(imageInfo);
		System.out.println("FLOT MAXIMUM");
		System.out.println(g.executerPreflot());
		System.out.println("AFFICHAGE DES PLANS");
		g.afficherPlans();
		
		
=======
		if (args.length < 1) {
			System.out.println("Must give a file path as argument");
		} else {
			System.out.println(args[0]);
			File file = new File(args[0]);
			ImageInfo imageInfo = FileParser.parse(file);
			System.out.println(imageInfo);
			Graphe g = new Graphe(imageInfo);
			g.executerInitialisationPreflot();
			g.afficherGrapheResiduel();
		}
>>>>>>> branch 'master' of https://github.com/thomaslprr/ImageSegmentation.git
	}

}
