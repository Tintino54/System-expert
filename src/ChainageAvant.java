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
	 * On applique l'ensemble des rï¿½gles applicables, 
	 * c'est ï¿½ dire celles dont les premisses sont contenus dans la base de faits.
	 * On s'arrï¿½te quand l'objectif a ï¿½tï¿½ trouvï¿½ ou lorsque la base de faits est saturï¿½e.
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
					System.out.println("règle courante : "+regleCourante+"\n");
					for(int j = 0; j<regleCourante.getPrem().size(); j++){
						System.out.println("premisse courant: "+regleCourante.getPrem().get(j));
						//si l'ensemble des faits contient deja la proposition, alors on ne l'ajoute pas
						for(int k = 0; k<regleCourante.getRes().size(); k++){
							System.out.print("résultat courant : "+regleCourante.getRes().get(k)+"\n");
							if(BF.contains(regleCourante.getRes().get(k)) == false){
								System.out.println("ajout à la base de fait "+regleCourante.getRes().get(k)+"\n");
								BF.add(regleCourante.getRes().get(k));
								if(objectif.contains(regleCourante.getRes().get(k))){
									objectifsTrouves.add(regleCourante.getRes().get(k));
								}
							}
						}
					}	
					//On supprime la regle car nous n'auront plus besoin de l'utiliser
					System.out.println("la règle '"+regleCourante+"' ,a été supprimée car utilisée\n");
					iterator.remove();
				}
			}
		}	
		System.out.println("Base de faits : "+BF);
		System.out.println("objectifs trouves : "+objectifsTrouves);
		return objectifsTrouves;
	}

	public ArrayList<String> runProfondeur(){		
		 //On dï¿½clare un ArrayList contenant les fait initiaux 
		 //pour ï¿½viter  une "currentModificationException" lors de la lecture de la base de faits	
		ArrayList<String> objectifsTrouves=new ArrayList<String>();
		ArrayList<String> faitsInitiaux=new ArrayList<String>(BF);
		for(String o : objectif){
			//on appelle le parcours en profondeur sur chaque faits initiaux
			//si un seul permet de dï¿½duire l'objectif on sort de la boucle
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
	 * On applique la premiï¿½re rï¿½gle applicable contenant le fait en paramï¿½tre, puis
	 * on rappelle la fonction sur chaque consï¿½quence aprï¿½s l'avoir ajoutï¿½e ï¿½ la base de faits. 
	 * s'arrï¿½te quand la base de faits contient l'objectif que l'on cherche
	 */
	public boolean parcoursProfondeur(String objectif,String dernierFaitDeduit){
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
				System.out.println( "utilisation de la règle : "+regleCourante);
				regleCourante.setUtilisation();	
				/*regleCourante est une copie, on utilise set
				 *pour modifier la variable d'origine dans l'ArrayList*/
				regles.set(numRegle,regleCourante);
				//cas rï¿½cursif on rappelle la fonction sur chaque conclusion de la rï¿½gle appliquï¿½e
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
		System.out.println("le fait "+dernierFaitDeduit+" ne déclenche pas de règle");
		return false;
	}

}
