import java.util.ArrayList;

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

		// On fait un chaînage avant, donc on cherche à aggrandir notre BF jusqu'à ce qu'il contienne
		//la ou les solutions recherchées

		//tant qu'on a pas trouvé l'objectif dans notre ensemble de faits
		//ou lorsque l'on fait un tour de boucle sans effectuer aucune opération

		//arret permet d'arrêter la boucle dès qu'un probleme survient
		boolean arret = false;	
		ArrayList<String> newBF = new ArrayList<String>();
		while(BF.containsAll(objectif) == false && arret == false){
			newBF = (ArrayList<String>) BF.clone();
			//pour chaque règle, on va regarder si on peut l'effectuer
			//si on peut on va alors ajouter le résultat à l'ensemble des faits
			for(int i = 0; i < regles.size(); i++){
				//on va regarder chaque proposition de la prémisse et on va regarder si on l'a dans notre ensemble de faits
				Regle regleCourante = regles.get(i);
				//si l'ensemble des faits contient bien l'ensemble des propositions de la premisse
				if(newBF.containsAll(regleCourante.getPrem())){
					for(int j = 0; j<regleCourante.getPrem().size(); j++){
						//si l'ensemble des faits contient déjà la proposition, alors on ne l'ajoute pas
						//System.out.println(regleCourante.getRes());
						for(int k = 0; k<regleCourante.getRes().size(); k++){
							if(newBF.contains(regleCourante.getRes().get(k)) == false){
								newBF.add(regleCourante.getRes().get(k));
							}
						}
					}	
					//On supprime la règle car nous n'auront plus besoin de l'utiliser
					//regles.remove(i);
				}
			}
			System.out.println(BF);
			System.out.println(newBF);
			if(BF.equals(newBF)){
				arret = true;
			}else{
				BF = newBF;
			}
			System.out.println(BF);
		}	
	}


}
