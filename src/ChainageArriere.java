import java.util.ArrayList;
import java.util.Iterator;
public class ChainageArriere extends Chainage{

	public ChainageArriere(ArrayList<Regle> r, ArrayList<String> b,
			ArrayList<String> o) {
		super(r, b, o);
	}
	
	public ArrayList<String> run(){
		
		
		
		return BF;
	}
	
	public boolean test(String objectif){
		System.out.println("objectif "+objectif);
		//cas d'arr�t objectif trouv�
		if(BF.contains(objectif)){
			System.out.println("l'objectif: "+objectif+" est dans la base de fait");
			return true;
		}
		
		//parcours de l'ensemble des r�gles
		for(Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
			System.out.println("boucle");
			Regle regleCourante = iterator.next();
			//si notre objectif est cons�quence d'une r�gle d'inf�rence
			if(regleCourante.getRes().contains(objectif)){
				System.out.println("resultat");
				System.out.println(regleCourante.getPrem());
				boolean verifie=true;
				for(String premisse : regleCourante.getPrem()){
					if(!test(premisse)){
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
