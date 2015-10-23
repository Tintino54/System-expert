import java.util.ArrayList;


public class Principale {
	public static void main(String args[]){
		String path="/home/etudiant/Dropbox/workspace/System-expert/src/testSystem.txt";
		ExtracteurPropositions extracteur=new ExtracteurPropositions(path);
		extracteur.extraction();
		
		ArrayList<String> objectifs=new ArrayList<String>();
		
		objectifs.add("neige");
		
		ChainageMixte chainage=new ChainageMixte(extracteur.getPropositions(),
												 extracteur.getBaseFaits(),
												 	objectifs);
		chainage.run();
		
		
		
		
	}

}
