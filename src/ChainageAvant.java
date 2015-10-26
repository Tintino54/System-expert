import java.util.ArrayList;
import java.util.Iterator;

public class ChainageAvant extends Chainage{

	public ChainageAvant(ArrayList<Regle> r, ArrayList<String> b,
			ArrayList<String> o) {
		super(r, b, o);
	}

	/**
	 * Chainage avant en largeur
	 * @return ArrayList<String>
	 * On applique l'ensemble des règles applicables, 
	 * c'est à dire celles dont les premisses sont contenus dans la base de faits.
	 * On s'arrête quand l'objectif a été trouvé ou lorsque la base de faits est saturée.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> run(){
		boolean arret = false;
		ArrayList<String> newBF = new ArrayList<String>();
		while(BF.containsAll(objectif) == false && arret == false){
			newBF = (ArrayList<String>) BF.clone();
			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
				Regle regleCourante = iterator.next();
				//si l'ensemble des faits contient bien l'ensemble des propositions de la premisse
				if(newBF.containsAll(regleCourante.getPrem())){
					for(int j = 0; j<regleCourante.getPrem().size(); j++){
						//si l'ensemble des faits contient déjà  la proposition, alors on ne l'ajoute pas
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
		
		
		
		 //On déclare un ArrayList contenant les fait initiaux 
		 //pour éviter  une "currentModificationException" lors de la lecture de la base de faits	
		ArrayList<String> faitsInitiaux=new ArrayList<String>(BF);
		for(String o : objectif){
			//on appelle le parcours en profondeur sur chaque faits initiaux
			//si un seul permet de déduire l'objectif on sort de la boucle
			for(String faitInitial:faitsInitiaux){
				if (parcoursProfondeur(o,faitInitial)){
					break;
				}
			}
		}
		return BF;
	} 
	/**
	 * Chainage avant en profondeur
	 * @param objectif
	 * @param dernierFaitDeduit
	 * @return boolean
	 * On applique la première règle applicable contenant le fait en paramètre, puis
	 * on rappelle la fonction sur chaque conséquence après l'avoir ajoutée à la base de faits. 
	 * s'arrête quand la base de faits contient l'objectif que l'on cherche
	 */
	public boolean parcoursProfondeur(String objectif,String dernierFaitDeduit){
		//condition d'arrêt objectif trouvé
		if(BF.contains(objectif)){
			System.out.println("l'objectif : "+objectif+" a été trouvé");
			return true;
		}else{
			int numRegle=0;
			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();){
				Regle regleCourante = iterator.next();
				ArrayList<String> premisses=regleCourante.getPrem();
				if(premisses.contains(dernierFaitDeduit)&&BF.containsAll(premisses)&&!regleCourante.dejaUtilise()){
					System.out.println("utilisation de la règle: "+numRegle);
					regleCourante.setUtilisation();	
					/*regleCourante est une copie, on utilise set
					 *pour modifier la variable d'origine dans l'ArrayList*/
					regles.set(numRegle,regleCourante);
					//cas récursif on rappelle la fonction sur chaque conclusion de la règle appliquée
					for(String resultatCourant : regleCourante.getRes()){
						BF.add(resultatCourant);
						System.out.println("rappel sur : "+resultatCourant);
				
						if(parcoursProfondeur(objectif,resultatCourant))
							return true;
					}
				}
				numRegle++;
			}

			System.out.println(dernierFaitDeduit+" ne déclenche aucune règle");
			return false;
		}
	}

}
