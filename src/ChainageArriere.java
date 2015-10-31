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
			System.out.println(objectif+" est dans la base de fait");
			return true;
		}
		
		//parcours de l'ensemble des règles
		for(Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
			Regle regleCourante = iterator.next();
			//si notre objectif est consequence d'une règle d'infèrence
			if(regleCourante.getRes().contains(objectif)){
				System.out.println(" utlisation de la règle "+regleCourante);
				boolean verifie=true;
				for(String premisse : regleCourante.getPrem()){
					System.out.println(" nouvel objectif : "+premisse);
					if(!chainageArriere(premisse)){
						verifie=false;
					}
				}
				if(verifie == true){
					System.out.println("l'objectif: "+objectif+ "est prouvé");
					return true;
				}
			}
		}
		System.out.println(objectif+" n'est pas dans la base de fait");
		return false;
	}

}
