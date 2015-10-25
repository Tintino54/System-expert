import java.util.ArrayList;


public class Principale {
	public static void main(String args[]){
		String path="/home/etudiant/Dropbox/workspace/System-expert/src/testSystem.txt";
		ExtracteurPropositions extracteur=new ExtracteurPropositions(path);
		extracteur.extraction();
		
		ArrayList<String> objectifs=new ArrayList<String>();
		
	//	objectifs.add("tagada");
		objectifs.add("nuage");
		
		ChainageMixte chainage=new ChainageMixte(extracteur.getPropositions(),
												 extracteur.getBaseFaits(),
												 	objectifs);
		System.out.println(chainage.run());
		
		
	}

}
