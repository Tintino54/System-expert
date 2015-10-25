import java.util.ArrayList;


public class Principale {
	public static void main(String args[]){
		String path="C:\\Users\\Alexandre\\Dropbox\\workspace\\System-expert\\src\\testSystem.txt";
		ExtracteurPropositions extracteur=new ExtracteurPropositions(path);
		extracteur.extraction();
		
		ArrayList<String> objectifs=new ArrayList<String>();
		
		//objectifs.add("nuage");
		objectifs.add("verglas");
		
		ChainageAvant chainage=new ChainageAvant(extracteur.getPropositions(),
												 extracteur.getBaseFaits(),
												 	objectifs);
		System.out.println(chainage.runProfondeur());
		
		
	}

}
