package main;

import java.io.File;
import java.io.FileNotFoundException;

import graphe.Graphe;
import parse.FileParser;
import parse.ImageInfo;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File(args[0]);
		ImageInfo imageInfo = FileParser.parse(file);
		System.out.println(imageInfo);
		Graphe g = new Graphe(imageInfo);
		g.afficherGrapheResiduel();
		
	}

}
