package SystemeExpert;
import java.util.ArrayList;


public abstract class Chainage {
	protected ArrayList<Regle> regles;
	protected ArrayList<String> BF;
	protected ArrayList<String> objectif;
	protected ArrayList<Regle> reglesIncoherence;
	protected String trace;
	
	public Chainage(ArrayList<Regle> r,ArrayList<String> b,ArrayList<String> o,ArrayList<Regle> inc){
		regles = r;
		BF = b;
		objectif = o;
		reglesIncoherence=inc;
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
	
	public ArrayList<Regle> getReglesIncoherence(){
		return reglesIncoherence;
	}
	
	public String getTrace(){
		return trace;
	}
	
	/**
	 * verifierCoherence
	 * @return
	 * verifie que la base de faits obtenue par saturation
	 * est coherente
	 */
	public boolean verifierCoherence(){
		for(Regle regle : reglesIncoherence){
			if(BF.containsAll(regle.getPrem())){
				return false;
			}
		}
		return true;
	}
	
	
}
