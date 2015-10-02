public class Regle {
	//attributs
	private String premisse;
	private String resultat;
	
	Regle(String proposition){	
		//délimite la prémisse et le résultat sur la flèche
		int delim = proposition.indexOf("-");
		premisse = proposition.substring(0, delim);
		resultat = proposition.substring(delim+2, proposition.length());
	}
	
	public String getPrem(){
		return premisse;
	}
	
	public String getRes(){
		return resultat;
	}
	
}
