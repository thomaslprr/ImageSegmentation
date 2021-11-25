package main;

import java.io.File;
import java.io.FileNotFoundException;

import graphe.Graphe;
import parse.FileParser;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File(args[0]);
		Graphe g = FileParser.parse(file);
		
		System.out.println("AFFICHAGE TAB 1 ");
		for(int i=0;i<g.getNbLignes();i++) {
			for(int j=0;j<g.getNbColonnes();j++) {
				System.out.print(g.getaValeurs()[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("AFFICHAGE TAB 2 ");
		for(int i=0;i<g.getNbLignes();i++) {
			for(int j=0;j<g.getNbColonnes();j++) {
				System.out.print(g.getbValeurs()[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("AFFICHAGE TAB 3 ");
		for(int i=0;i<g.getHorizontalePenalites().length;i++) {
			for(int j=0;j<g.getHorizontalePenalites()[1].length;j++) {
				System.out.print(g.getHorizontalePenalites()[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("AFFICHAGE TAB 4 ");
		for(int i=0;i<g.getVerticalePenalites().length;i++) {
			for(int j=0;j<g.getVerticalePenalites()[1].length;j++) {
				System.out.print(g.getVerticalePenalites()[i][j]+" ");
			}
			System.out.println();
		}
		
	}

}
