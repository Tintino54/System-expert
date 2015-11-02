import java.util.ArrayList;
import java.util.Iterator;

public class ChainageAvant extends Chainage{

	public ChainageAvant(ArrayList<Regle> r, ArrayList<String> b,
			ArrayList<String> o,ArrayList<Regle> inc) {
		super(r, b, o, inc);
	}

	/**
	 * Chainage avant en largeur
	 * @return ArrayList<String>
	 * On applique l'ensemble des r�gles applicables, 
	 * c'est � dire celles dont les premisses sont contenus dans la base de faits.
	 * On s'arr�te quand l'objectif a �t� trouv� ou lorsque la base de faits est satur�e.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> run(){
		trace = "";
		ArrayList<String> objectifsTrouves = new ArrayList<String>();
		boolean arret = false;
		//ArrayList<String> newBF = new ArrayList<String>();
		while(BF.containsAll(objectif) == false && arret == false){
			
			for (Iterator<Regle> iterator = regles.iterator(); iterator.hasNext();) {
				Regle regleCourante = iterator.next();
				//si l'ensemble des faits contient bien l'ensemble des propositions de la premisse
				if(BF.containsAll(regleCourante.getPrem())){
					System.out.println("r�gle courante : "+regleCourante+"\n");
					for(int j = 0; j<regleCourante.getPrem().size(); j++){
						System.out.println("premisse courant: "+regleCourante.getPrem().get(j));
						//si l'ensemble des faits contient deja la proposition, alors on ne l'ajoute pas
						for(int k = 0; k<regleCourante.getRes().size(); k++){
							System.out.print("r�sultat courant : "+regleCourante.getRes().get(k)+"\n");
							if(BF.contains(regleCourante.getRes().get(k)) == false){
								System.out.println("ajout � la base de fait "+regleCourante.getRes().get(k)+"\n");
								BF.add(regleCourante.getRes().get(k));
								if(objectif.contains(regleCourante.getRes().get(k))){
									objectifsTrouves.add(regleCourante.getRes().get(k));
								}
							}
						}
					}	
					//On supprime la regle car nous n'auront plus besoin de l'utiliser
					System.out.println("la r�gle '"+regleCourante+"' ,a �t� supprim�e car utilis�e\n");
					iterator.remove();
				}
			}
		}	
		System.out.println("Base de faits : "+BF);
		System.out.println("objectifs trouves : "+objectifsTrouves);
		return objectifsTrouves;
	}

	public ArrayList<String> runProfondeur(){		
		 //On d�clare un ArrayList contenant les fait initiaux 
		 //pour �viter  une "currentModificationException" lors de la lecture de la base de faits	
		ArrayList<String> objectifsTrouves=new ArrayList<String>();
		ArrayList<String> faitsInitiaux=new ArrayList<String>(BF);
		for(String o : objectif){
			//on appelle le parcours en profondeur sur chaque faits initiaux
			//si un seul permet de d�duire l'objectif on sort de la boucle
			for(String faitInitial:faitsInitiaux){
				if (parcoursProfondeur(o,faitInitial)){
					objectifsTrouves.add(o);
					break;
				}
			}
		}
		return objectifsTrouves;
	} 
	/**
	 * Chainage avant en profondeur
	 * @param objectif
	 * @param dernierFaitDeduit
	 * @return boolean
	 * On applique la premi�re r�gle applicable contenant le fait en param�tre, puis
	 * on rappelle la fonction sur chaque cons�quence apr�s l'avoir ajout�e � la base de faits. 
	 * s'arr�te quand la base de faits contient l'objectif que l'on cherche
	 */
	public boolean parcoursProfondeur(String objectif,String dernierFaitDeduit){
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
				System.out.println( "utilisation de la r�gle : "+regleCourante);
				regleCourante.setUtilisation();	
				/*regleCourante est une copie, on utilise set
				 *pour modifier la variable d'origine dans l'ArrayList*/
				regles.set(numRegle,regleCourante);
				//cas r�cursif on rappelle la fonction sur chaque conclusion de la r�gle appliqu�e
				for(String resultatCourant : regleCourante.getRes()){
					BF.add(resultatCourant);
					trace=trace + "rappel sur le fait: "+resultatCourant+"\n";
					if(parcoursProfondeur(objectif,resultatCourant)){
							return true;
					}
				}
			}
			numRegle++;
		}
		System.out.println("le fait "+dernierFaitDeduit+" ne d�clenche pas de r�gle");
		return false;
	}

}
