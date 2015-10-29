import java.util.ArrayList;
public abstract class Chainage {
	protected ArrayList<Regle> regles;
	protected ArrayList<String> BF;
	protected ArrayList<String> objectif;
	protected String trace;
	
	public Chainage(ArrayList<Regle> r,ArrayList<String> b,ArrayList<String> o){
		regles = r;
		BF = b;
		objectif = o;
	}
	
	public ArrayList<Regle> getRegles(){
		return regles;
	}
	
	public ArrayList<String> getBF(){
		return BF;
	}
	
	public ArrayList<String> getObjectif(){
		return objectif;
	}
	
	
}
