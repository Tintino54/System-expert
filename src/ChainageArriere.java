import java.util.ArrayList;
import java.util.Iterator;
public class ChainageArriere extends Chainage{
	
	public ChainageArriere(ArrayList<Regle> r, ArrayList<String> b,
			ArrayList<String> o) {
		super(r, b, o);
	}
	
	public ArrayList<String> run(){
		trace="";
		for(String elt :  objectif){
			if(chainageArriere(elt))
				BF.add(elt);
		}
		
		
		return BF;
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
			System.out.println(objectif+" est dans la base de fait");
			return true;
		}
		
		//parcours de l'ensemble des r�gles
		for(Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
			Regle regleCourante = iterator.next();
			//si notre objectif est consequence d'une r�gle d'inf�rence
			if(regleCourante.getRes().contains(objectif)){
				System.out.println(" utlisation de la r�gle "+regleCourante);
				boolean verifie=true;
				for(String premisse : regleCourante.getPrem()){
					System.out.println(" nouvel objectif : "+premisse);
					if(!chainageArriere(premisse)){
						verifie=false;
					}
				}
				if(verifie == true){
					System.out.println("l'objectif: "+objectif+ "est prouv�");
					return true;
				}
			}
		}
		System.out.println(objectif+" n'est pas dans la base de fait");
		return false;
	}

}
