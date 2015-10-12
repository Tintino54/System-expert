
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ExtracteurPropositions {
	private String __path;
	private ArrayList<Regle> props=new ArrayList<Regle>();
	private ArrayList<String> baseFaits=new ArrayList<String>();
	
	public ExtracteurPropositions(String path){
		__path=path;
	}
	
	public ArrayList<Regle> getPropositions(){
		return props;
	}
	
	public ArrayList<String> getBaseFaits(){
		return baseFaits;
	}
	
	public void extraction(){
		String line;
		try {
			//lecture du fichier en entré
			InputStream ips=new FileInputStream(__path);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader buffer=new BufferedReader(ipsr);
			System.out.println("ok");
			//On extrait les faits
			if((line=buffer.readLine())!=null&&line.matches("\\{[A-Z,a-z, ]+(,[A-Z,a-z, ]+)*\\}")){
				String[] faits=line.split("[,{}]");
				for(String fait : faits){
					if(!fait.trim().isEmpty()){
						baseFaits.add(fait);
					}
				}
			}
			
			else{
				throw new Exception("le chaine contenant les faits est mal formée");
			}
			int numLigne=1;
			//On extrait les propositions
			while((line=buffer.readLine())!=null){
				//on teste si la chaine est bien conforme à la syntaxe d'une proposition
				if(line.matches("[A-Z,a-z, ]+( et [A-Z,a-z, ]+)*->[A-Z,a-z, ]+( et [A-Z,a-z, ]+)*")){
					Regle prop=new Regle(line);
					props.add(prop);
				}
				else{
					throw new Exception("la proposition à la ligne "+Integer.toString(numLigne)+" est incorrecte");
				}
				numLigne++;
		//	buffer.close();
			}

	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		ExtracteurPropositions ext=new ExtracteurPropositions("/home/etudiant/Dropbox/workspace/System-expert/src/testSystem.txt");
		ext.extraction();
		ext.affichageFaits();
		ext.affichageRegles();
	}
	
	public void affichageFaits(){
		for(String fait : baseFaits){
			System.out.print(fait+" ");
		}
		System.out.println();
	}
	
	public void affichageRegles(){
		for(Regle regle :props){
			regle.display();
		}
	}
}