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
		
		/**
		 * On déclare un ArrayList contenant les fait initiaux 
		 * pour éviter  une "currentModificationException" lors de la lecture de la base de faits
		*/
		ArrayList<String> faitsInitiaux=new ArrayList<String>(BF);
	//	int nbFaitsInitiaux=BF.size(),numFait=0;
		
		//on parcoure chaque objectif avec chaque fait,
		//dès qu'on le trouve on arrête de le chercher
		//et donc on arrête de parcourir les faits, et on regarde pour les objectifs suivants
		for(String o : objectif){
			for(String faitInitial:faitsInitiaux){
				if (parcoursProfondeur(o,faitInitial)){
					foundObjectif.add(o);
					break;
				}
			}
		}
		return foundObjectif;
	} 

	public boolean parcoursProfondeur(String objectif,String dernierFaitDeduit){
		//condition d'arrêt
		if(BF.contains(objectif)){
			System.out.println("l'objectif courant : "+objectif+" a �t� trouv�");
			return true;
		}else{
			int numRegle=0;
			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();){
				Regle regleCourante = iterator.next();
				//si la règle à a gauche le fait courant et que les autres éléments sont dans la base de fait
				ArrayList<String> premisses=regleCourante.getPrem();
				//System.out.println(regleCourante.getPrem().contains(faitCourant) && BF.containsAll(regleCourante.getPrem()));
				if(premisses.contains(dernierFaitDeduit)&&BF.containsAll(premisses)&&!regleCourante.dejaUtilise()){
				//	BF.add(dernierFaitDeduit);
					System.out.println("utilisation de la r�gle: "+numRegle);
					regleCourante.setUtilisation();	
					//regleCourante est une copie de la règle,donc on utilise la classe set
					regles.set(numRegle,regleCourante);
					for(String resultatCourant : regleCourante.getRes()){
						//dans le cas où la on trouve une condition d'arrêt
						BF.add(resultatCourant);
						System.out.println("rappel sur : "+resultatCourant);
				
						if(parcoursProfondeur(objectif,resultatCourant))
							return true;
					}
				}
				numRegle++;
			}

			System.out.println(dernierFaitDeduit+" ne d�clenche aucune r�gle");
			return false;
		}
	}

}
