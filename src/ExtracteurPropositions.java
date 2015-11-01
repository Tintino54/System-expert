
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ExtracteurPropositions {
	private String __path;
	private ArrayList<Regle> regles=new ArrayList<Regle>();
	private ArrayList<Regle> reglesIncoherence=new ArrayList<Regle>();
	
	public ExtracteurPropositions(String path){
		__path=path;
	}
	
	public ArrayList<Regle> getRegles(){
		return regles;
	}
	
	public ArrayList<Regle> getReglesIncoherence(){
		return reglesIncoherence;
	}
	
	public void extraction(){
		String line;
		try {
			//lecture du fichier en entré
			InputStream ips=new FileInputStream(__path);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader buffer=new BufferedReader(ipsr);
			
			int numLigne=1;
			//On extrait les r�gles du fichier
			while((line=buffer.readLine())!=null){
				//on teste si la chaine est bien conforme � la syntaxe d'une r�gle
				if(line.matches("[A-Z,a-z, ]+( et [A-Z,a-z, ]+)*->[A-Z,a-z, ]+( et [A-Z,a-z, ]+)*")){
					Regle regle=new Regle(line);
					regles.add(regle);
				}
				//cas r�gle d�finissant une incoh�rence
				else if(line.matches("[A-Z,a-z, ]+( et [A-Z,a-z, ]+)*->INC")){
					Regle inc=new Regle(line);
					reglesIncoherence.add(inc);
					
				}
				else{
					throw new Exception("la proposition à la ligne "+Integer.toString(numLigne)+" est incorrecte");
				}
				numLigne++;
				//buffer.close();
			}
			buffer.close();

	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void affichageFaits(){
		for(Regle inc : reglesIncoherence){
			inc.display();
		}
		System.out.println();
	}
	
	public void affichageRegles(){
		for(Regle regle :regles){
			regle.display();
		}
	}
}