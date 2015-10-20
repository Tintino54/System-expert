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
		
		ExtracteurPropositions ep = new ExtracteurPropositions("/home/etudiant/Dropbox/workspace/System-expert/src/testSystem.txt");
		ep.extraction();
		regles = ep.getPropositions();
		BF = ep.getBaseFaits();
		


		//objectif : {nuage, Z, T, R}
		objectif.add("tagada");
		objectif.add("soleil");
		/*for(Regle regle : regles){
			System.out.println(regle.getPrem());
		}*/
		ChainageAvant chainageAvant = new ChainageAvant(regles, BF, objectif);
		/*ChainageArriere chainageArriere= new ChainageArriere(regles, BF, objectif);
		BF=chainageArriere.run();
		System.out.println(BF);*/
		
		/*if(chainageArriere.test("fraise tagada")==true){
			System.out.println("verifie");
		}*/
		
		
		//BF = chainageAvant.run();
		System.out.println(BF);
		ArrayList<String> objectifsRunProfondeur = chainageAvant.runProfondeur();
		System.out.println("objectifs trouvés : "+objectifsRunProfondeur);
	}


}
