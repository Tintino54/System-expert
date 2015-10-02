public class Proposition {
	//attributs
	String prop;
	boolean value;
	
	Proposition(String p){
		prop = p;
		value = true;
	}
	Proposition(String p, boolean b){
		prop = p;
		value = b;
	}
	
	private String getProp(){
		return prop;
	}
	
	private boolean getV(){
		return value;
	}
}
