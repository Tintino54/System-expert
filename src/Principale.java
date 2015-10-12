import java.util.ArrayList;
import java.util.Iterator;

public class Principale {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//règles dans lesquelles nous allons chercher l'objectif
		ArrayList<Regle> regles = new ArrayList<Regle>();
		//Ensemble des faits
		ArrayList<String> BF = new ArrayList<String>();
		//ce que nous recherchons
		ArrayList<String> objectif = new ArrayList<String>();


		BF.add("eruption volcanique");
		BF.add("cyclone");
		//BF.add("orage");

		//R1 = A->B
		//R2 = ...
		regles.add(new Regle("beau temps->soleil"));
		regles.add(new Regle("anticyclone->beau temps et cumulus"));
		regles.add(new Regle("pluie et air froid->verglas et neige"));
		regles.add(new Regle("eruption volcanique->tremblement de terre"));
		regles.add(new Regle("nuage et air chaud->brouillard"));
		regles.add(new Regle("air chaud->stratus"));
		regles.add(new Regle("stratus et cyclone->orage"));
		regles.add(new Regle("air froid->cumulonimbus"));
		regles.add(new Regle("cumulonimbus->pluie"));
		regles.add(new Regle("stratus->pluie"));
		regles.add(new Regle("eruption volcanique->nuage"));
		regles.add(new Regle("cyclone->air froid"));
		regles.add(new Regle("soleil->air chaud"));
		regles.add(new Regle("cumulonimbus->nuage"));
		regles.add(new Regle("stratus->nuage"));
		regles.add(new Regle("cumulus->nuage"));
		regles.add(new Regle("orage->pluie et cumulonimbus"));



		//objectif : {nuage, Z, T, R}
		objectif.add("neige");

		ChainageAvant chainageAvant = new ChainageAvant(regles, BF, objectif);
		
		BF = chainageAvant.run();
		
	}


}
