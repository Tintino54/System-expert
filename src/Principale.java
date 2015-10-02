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

		// On fait un chaînage avant, donc on cherche à aggrandir notre BF jusqu'à ce qu'il contienne
		//la ou les solutions recherchées

		//tant qu'on a pas trouvé l'objectif dans notre ensemble de faits
		//ou lorsque l'on fait un tour de boucle sans effectuer aucune opération

		//arret permet d'arrêter la boucle dès qu'un probleme survient
		boolean arret = false;		
		while(BF.containsAll(objectif) == false && arret == false){
			System.out.println(BF);
			//pour chaque règle, on va regarder si on peut l'effectuer
			//si on peut on va alors ajouter le résultat à l'ensemble des faits
			for(int i = 0; i < regles.size(); i++){
				//on va regarder chaque proposition de la prémisse et on va regarder si on l'a dans notre ensemble de faits
				Regle regleCourante = regles.get(i);
				//si l'ensemble des faits contient bien l'ensemble des propositions de la premisse
				if(BF.containsAll(regleCourante.getPrem())){
					for(int j = 0; j<regleCourante.getPrem().size(); j++){
						String propCourante = regleCourante.getPrem().get(j);
						//si l'ensemble des faits contient déjà la proposition, alors on ne l'ajoute pas
						System.out.println(regleCourante.getRes());
						if(BF.contains(regleCourante.getRes()) == false){
							BF.add(regleCourante.getRes());
						}
					}
					
					//On supprime la règle car nous n'auront plus besoin de l'utiliser
					//regles.remove(i);
				}
			}
			
		}	
		
		System.out.println(BF);

	}


}
