import java.util.ArrayList;

public class Principale {
	public static void main(String[] args) {
		//règles dans lesquelles nous allons chercher l'objectif
		ArrayList<Regle> regles = new ArrayList<Regle>();
		//Ensemble des faits
		ArrayList<String> BF = new ArrayList<String>();
		//ce que nous recherchons
		ArrayList<String> objectif = new ArrayList<String>();
		
		//BF = {A,B,C}
		BF.add("A");
		BF.add("B");
		BF.add("C");
			
		//R1 = A->B
		//R2 = ...
		regles.add(new Regle("A->B"));
		regles.add(new Regle("A^B->D"));
		regles.add(new Regle("C^F->A"));
		regles.add(new Regle("D->F"));
		regles.add(new Regle("D^A->Z"));
		
		
		//objectif : {F}
		objectif.add("F");
		//affiche les règles
		for(int i = 0; i < regles.size(); i++){
			System.out.println(regles.get(i).getPrem()+" "+regles.get(i).getRes());
		}
		
		
		
	}
}
