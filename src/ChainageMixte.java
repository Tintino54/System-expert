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
		//condition d'arrêt
		if(BF.contains(objectif)){
			System.out.println("l'objectif courant : "+objectif+" a été trouvé");
			return true;
		}else{
			int numRegle=0;
			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();){
				Regle regleCourante = iterator.next();
				//si la règle à a gauche le fait courant et que les autres éléments sont dans la base de fait
				ArrayList<String> premisses=regleCourante.getPrem();
				//System.out.println(regleCourante.getPrem().contains(faitCourant) && BF.containsAll(regleCourante.getPrem()));
				if(premisses.contains(dernierFaitDeduit)&&BF.containsAll(premisses)&&!regleCourante.dejaUtilise()){
					System.out.println("appel de la règle: "+numRegle);
					regleCourante.setUtilisation();	
					//regleCourante est une copie de la règle,donc on utilise la classe set
					regles.set(numRegle,regleCourante);
					for(String resultatCourant : regleCourante.getRes()){
						//dans le cas où la on trouve une condition d'arrêt
						BF.add(resultatCourant);
						System.out.println("rappel sur : "+resultatCourant);
				
						if(chainage_mixte(objectif,resultatCourant))
							return true;
					}
				}
				numRegle++;
			}
			if(chainage_arriere(dernierFaitDeduit)){
				return true;
			}
			
			//System.out.println("parcours");
			return false;
		}
	}

	
	public boolean chainage_arriere(String objectif){
		System.out.println("objectif "+objectif);
		//cas d'arr�t objectif trouv�
		if(BF.contains(objectif)){
			//System.out.println("l'objectif: "+objectif+" est dans la base de fait");
			return true;
		}
		
		//parcours de l'ensemble des r�gles
		for(Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
			//System.out.println("boucle");
			Regle regleCourante = iterator.next();
			//si notre objectif est cons�quence d'une r�gle d'inf�rence
			if(regleCourante.getRes().contains(objectif)){
				//System.out.println("resultat");
				//System.out.println(regleCourante.getPrem());
				boolean verifie=true;
				for(String premisse : regleCourante.getPrem()){
					if(!chainage_arriere(premisse)){
						verifie=false;
					}
				}
				if(verifie==true){
					return true;
				}
			}
		}
		System.out.println("l'objectif: "+objectif+" n'est pas dans la base de fait");
		return false;
	}

}
