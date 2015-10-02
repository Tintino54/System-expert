import java.util.ArrayList;

public class Regle {
	//attributs
	private ArrayList<String> premisse;
	private String resultat;

	Regle(String proposition){	
		premisse = new ArrayList<String>();
		//délimite la prémisse et le résultat sur la flèche
		int delim = proposition.indexOf("-");
		resultat = proposition.substring(delim+2, proposition.length());
		String partieGauche = proposition.substring(0, delim);

		//gestion premisse
		delim = partieGauche.indexOf("^");
		while(partieGauche.length() != 1){
			delim = partieGauche.indexOf("^");
			premisse.add(partieGauche.substring(0, delim));
			partieGauche = partieGauche.substring(delim+1, partieGauche.length());
		}
		premisse.add(partieGauche);
	}


	public void display(){
		for(int i = 0; i < premisse.size(); i++)
			System.out.print(premisse.get(i)+" ");
		System.out.print("\n");
	}

	public ArrayList<String> getPrem(){
		return premisse;
	}

	public String getRes(){
		return resultat;
	}

}
