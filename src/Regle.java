import java.util.ArrayList;

public class Regle {
	//attributs
	private ArrayList<String> premisse;
	private ArrayList<String> resultat;
	
	private boolean utilise;

	Regle(String proposition){	
		premisse = new ArrayList<String>();
		resultat = new ArrayList<String>();
		utilise = false;
		//délimite la prémisse et le résultat sur la flèche
		int delim = proposition.indexOf("-");
		//gestion premisse
		String partieGauche = proposition.substring(0, delim);
		String partieDroite = proposition.substring(delim+2, proposition.length());

		cutOnDelimitors(" et ",partieGauche, premisse);
		cutOnDelimitors(" et ",partieDroite, resultat);
	}

	public void display(){
		for(int i = 0; i < premisse.size(); i++)
			System.out.print(premisse.get(i)+" ");
		System.out.print("\n");
	}

	public ArrayList<String> getPrem(){
		return premisse;
	}

	public ArrayList<String> getRes(){
		return resultat;
	}
	
	public void cutOnDelimitors(String delimitor, String toCut, ArrayList<String> toAdd){
		String[] split = toCut.split(delimitor);
		for(int i = 0; i<split.length; i++){
			toAdd.add(split[i]);
		}
	}
	
	public boolean dejaUtilise(){
		return utilise;
	}
	
	public void setUtilisation(){
		utilise=true;
	}

}