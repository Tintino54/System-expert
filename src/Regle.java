public class Regle {
	//attributs
	Proposition premisse;
	Proposition resultat;
	
	Regle(Proposition p, Proposition r){
		premisse = p;
		resultat = r;
	}
	
	private Proposition getPrem(){
		return premisse;
	}
	
	private Proposition getRes(){
		return resultat;
	}
	
}
