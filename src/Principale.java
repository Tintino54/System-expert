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
		
		ExtracteurPropositions ep = new ExtracteurPropositions("C:\\Users\\Alexandre\\Dropbox\\workspace\\System-expert\\src\\testChainage.txt");
		ep.extraction();
		regles = ep.getPropositions();
		BF = ep.getBaseFaits();
		



		//objectif : {nuage, Z, T, R}
		/*objectif.add("neige");
		objectif.add("pluie");
		System.out.println(BF);*/
		for(Regle regle : regles){
			System.out.println(regle.getPrem());
		}

		//ChainageAvant chainageAvant = new ChainageAvant(regles, BF, objectif);
		ChainageArriere chainageArriere= new ChainageArriere(regles, BF, objectif);
		
		chainageArriere.test("accepte");
		
		//BF = chainageAvant.run();
		
	}


}
