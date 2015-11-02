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
		//dès qu'on le trouve on arrête de le chercher
		//et donc on arrête de parcourir les faits, et on regarde pour les objectifs suivants
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
		//condition d'arr�t objectif trouv�
		if(BF.contains(objectif)){
			System.out.println( "l'objectif "+objectif+" est dans la base de faits");
			return true;
		}
		int numRegle=0;
		for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();){
			Regle regleCourante = iterator.next();
			ArrayList<String> premisses=regleCourante.getPrem();
			if(premisses.contains(dernierFaitDeduit)&&BF.containsAll(premisses)&&!regleCourante.dejaUtilise()){
				System.out.println("utilisation de la r�gle : "+regleCourante);
				regleCourante.setUtilisation();	
				/*regleCourante est une copie, on utilise set
				 *pour modifier la variable d'origine dans l'ArrayList*/
				regles.set(numRegle,regleCourante);
				//cas r�cursif on rappelle la fonction sur chaque conclusion de la r�gle appliqu�e
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
	 * Chainage arri�re
	 * @param objectif
	 * @return boolean
	 * On applique la premi�re r�gle  qui a pour cons�quence l'objectif en param�tre, ensuite
	 * si chaque rappel de la fonction sur les premisses de cette r�gle aboutit � un �l�ment
	 * de la base de fait, l'objectif est prouv� sinon on applique la r�gle suivante. Et ainsi
	 * de suite jusqu'au parours de toutes les r�gles applicables.
	 */
	public boolean chainageArriere(String objectif){
		//cas d'arr�t objectif trouv�
		if(BF.contains(objectif)){
			System.out.println(objectif+" est dans BF");
			return true;
		}
		
		//parcours de l'ensemble des r�gles
		for(Regle regleCourante: regles) {
			//Regle regleCourante = iterator.next();
				//System.out.println("r�gle "+regleCourante+"n'est pas appliqu�e");
			//si notre objectif est consequence d'une r�gle d'inf�rence
			if(regleCourante.getRes().contains(objectif)&&!regleCourante.dejaUtilise()){
			//	System.out.println("utilisation de la r�gle : "+regleCourante);
				boolean verifie=true;
				for(String premisse : regleCourante.getPrem()){
					//System.out.println("chainage arriere utilise r�gle : "+regleCourante);
					if(!chainageArriere(premisse)){
						verifie=false;
					}
				}
				if(verifie == true){
					System.out.println("   le chainage arri�re deduit : "+objectif);
					BF.add(objectif);
					//return true;
				}
			}
		}
		return false;
	}

}
