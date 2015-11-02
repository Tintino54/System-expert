import java.util.ArrayList;
import java.util.Iterator;


public class ChainageMixte  extends Chainage{
	public ChainageMixte(ArrayList<Regle> r, ArrayList<String> b,ArrayList<String> o) {
		super(r, b, o);
	}
	
	public ArrayList<String> run(){	
		ArrayList<String> foundObjectif = new ArrayList<String>();
		ArrayList<String> faitsInitiaux=new ArrayList<String>(BF);
		//on parcoure chaque objectif avec chaque fait,
		//dÃ¨s qu'on le trouve on arrÃªte de le chercher
		//et donc on arrÃªte de parcourir les faits, et on regarde pour les objectifs suivants
		for(String o : objectif){
			for(String faitInitial:faitsInitiaux){
				if (chainage_mixte(o,faitInitial)){
					foundObjectif.add(o);
					break;
				}
			}
		}
		return foundObjectif;
	}
	
	public boolean chainage_mixte(String objectif,String dernierFaitDeduit){
		//condition d'arrï¿½t objectif trouvï¿½
		if(BF.contains(objectif)){
			System.out.println( "l'objectif "+objectif+" est dans la base de faits");
			return true;
		}
		int numRegle=0;
		for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();){
			Regle regleCourante = iterator.next();
			ArrayList<String> premisses=regleCourante.getPrem();
			if(premisses.contains(dernierFaitDeduit)&&BF.containsAll(premisses)&&!regleCourante.dejaUtilise()){
				System.out.println("utilisation de la règle : "+regleCourante);
				regleCourante.setUtilisation();	
				/*regleCourante est une copie, on utilise set
				 *pour modifier la variable d'origine dans l'ArrayList*/
				regles.set(numRegle,regleCourante);
				//cas rï¿½cursif on rappelle la fonction sur chaque conclusion de la rï¿½gle appliquï¿½e
				for(String resultatCourant : regleCourante.getRes()){
					BF.add(resultatCourant);
					System.out.println("rappel sur le fait: "+resultatCourant);
					if(chainage_mixte(objectif,resultatCourant))
						return true;
				}
			}
			else{
				
			}
			numRegle++;
		}
		System.out.println("chaine arriere sur : "+dernierFaitDeduit);
		BF.remove(dernierFaitDeduit);
		chainageArriere(dernierFaitDeduit);
		return false;
	}

	/**
	 * Chainage arrière
	 * @param objectif
	 * @return boolean
	 * On applique la première règle  qui a pour conséquence l'objectif en paramètre, ensuite
	 * si chaque rappel de la fonction sur les premisses de cette règle aboutit à un élément
	 * de la base de fait, l'objectif est prouvé sinon on applique la règle suivante. Et ainsi
	 * de suite jusqu'au parours de toutes les règles applicables.
	 */
	public boolean chainageArriere(String objectif){
		//cas d'arrêt objectif trouvé
		if(BF.contains(objectif)){
			System.out.println(objectif+" est dans BF");
			return true;
		}
		
		//parcours de l'ensemble des règles
		for(Regle regleCourante: regles) {
			//Regle regleCourante = iterator.next();
				//System.out.println("règle "+regleCourante+"n'est pas appliquée");
			//si notre objectif est consequence d'une règle d'infèrence
			if(regleCourante.getRes().contains(objectif)&&!regleCourante.dejaUtilise()){
			//	System.out.println("utilisation de la règle : "+regleCourante);
				boolean verifie=true;
				for(String premisse : regleCourante.getPrem()){
					//System.out.println("chainage arriere utilise règle : "+regleCourante);
					if(!chainageArriere(premisse)){
						verifie=false;
					}
				}
				if(verifie == true){
					System.out.println("   le chainage arrière deduit : "+objectif);
					BF.add(objectif);
					//return true;
				}
			}
		}
		return false;
	}

}
