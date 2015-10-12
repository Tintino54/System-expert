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
		
		ExtracteurPropositions ep = new ExtracteurPropositions("/home/etudiant/Bureau/Graphes AI/System-expert/src/testSystem.txt");
		ep.extraction();
		regles = ep.getPropositions();
		BF = ep.getBaseFaits();
		



		//objectif : {nuage, Z, T, R}
		objectif.add("neige");
		objectif.add("pluie");

		ChainageAvant chainageAvant = new ChainageAvant(regles, BF, objectif);
		
		BF = chainageAvant.run();
		
	}


}
