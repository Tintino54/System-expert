import java.util.ArrayList;
import java.util.Iterator;

public class ChainageAvant extends Chainage{

	public ChainageAvant(ArrayList<Regle> r, ArrayList<String> b,
			ArrayList<String> o) {
		super(r, b, o);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> run(){
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


			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
				//on va regarder chaque proposition de la prémisse et on va regarder si on l'a dans notre ensemble de faits
				Regle regleCourante = iterator.next();
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
					iterator.remove();
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
		return BF;
	}

	public ArrayList<String> runProfondeur(){
		
		ArrayList<String> foundObjectif = new ArrayList<String>();
		
		//on parcoure chaque objectif avec chaque fait,
		//dès qu'on le trouve on arrête de le chercher
		//et donc on arrête de parcourir les faits, et on regarde pour les objectifs suivants
		for(String o : objectif){
			for(String f: BF){
				System.out.println(parcoursProfondeur(f,o));
				if (parcoursProfondeur(f, o)){
					foundObjectif.add(o);
					break;
				}
			}
		}
		return foundObjectif;
	} 

	public boolean parcoursProfondeur(String faitCourant, String objectifCourant){
		//condition d'arrêt
		if(objectif.contains(faitCourant)){
			//System.out.println("l'objectif courant : "+faitCourant+" a été trouvé");
			return true;
		}else{
			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();){
				Regle regleCourante = iterator.next();
				//si la règle à a gauche le fait courant et que les autres éléments sont dans la base de fait
				//System.out.println(regleCourante.getPrem().contains(faitCourant) && BF.containsAll(regleCourante.getPrem()));
				if(regleCourante.getPrem().contains(faitCourant)){
					for(String resultatCourant : regleCourante.getRes()){
						//dans le cas où la on trouve une condition d'arrêt
						

						//mais oui c'est clair
						//System.out.println(resultatCourant+" "+objectifCourant);
						//return parcoursProfondeur(resultatCourant, objectifCourant);
						if(resultatCourant.equals(objectif)){
							return true;
						}else{
							return parcoursProfondeur(resultatCourant, objectifCourant);
						}
					}
					/*if(verifie==true){
						return true;
					}*/
				}
			}

			//System.out.println("parcours");
			return false;
		}
	}

}
