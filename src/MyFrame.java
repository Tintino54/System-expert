import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.*;


public  class MyFrame extends JFrame implements ActionListener{
	
	final JButton browse = new JButton("parcourir");
	final JButton calculerChainageAvantLargeur = new JButton("Chainage avant largeur");
	final JButton calculerChainageAvantProfondeur = new JButton("Chainage avant profondeur");
	final JButton calculerChainageArriere = new JButton("Chainage arriere");
	final JButton calculerChainageMixte = new JButton("Chainage mixte");
	final JFileChooser c = new JFileChooser();
	final JLabel fichierRegles = new JLabel();
	final JLabel affichageBF = new JLabel();
	final JLabel affichageBR = new JLabel();
	final JLabel labelFait = new JLabel("Choisir faits : ");
	final JLabel labelObjectif = new JLabel("Choisir objectifs : ");
	final JTextField selectionFait = new JTextField(30);
	final JTextField selectionObjectif = new JTextField(30);
	final JButton ajouterFait = new JButton("ajouter fait");
	final JButton ajouterObjectif = new JButton("ajouter objectif");
	final JButton removeFO = new JButton("reset");
	final JLabel listeFaits = new JLabel("faits         : ");
	final JLabel listeObjectifs = new JLabel("objectifs : ");
	final JTextArea resultatExecution = new JTextArea(20,30);
	final JCheckBox verbose = new JCheckBox("afficher déroulement");
	
	
	//variables servant pour la redirection du flux de sortie
	private PrintStream textAreaOutput=new PrintStream(new MyOutputStream(resultatExecution));
	private PrintStream nullOutput=new NullPrintStream();

		
	JPanel panel;
	GridBagConstraints contraintes;
	
	//rÃ¨gles dans lesquelles nous allons chercher l'objectif
			private ArrayList<Regle> regles = new ArrayList<Regle>();
			//Ensemble des faits
			private ArrayList<String> BF = new ArrayList<String>();
			//ce que nous recherchons
			private ArrayList<String> objectif = new ArrayList<String>();
			
			
	/**
	 * MyFrame
	 * @param nom
	 * place les différents composants dans le panel principal
	 */

	public MyFrame(String nom){
		super(nom);
		
		
		panel = new JPanel(new GridBagLayout());
		contraintes= new GridBagConstraints();
		panel.setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200,200,1200,600);
		
		affichageBF.setSize(250, 150);
		affichageBR.setSize(250, 150);

		fichierRegles.setText("");
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 0;
		contraintes.gridy = 0;
		panel.add( browse,contraintes);
		
		contraintes.gridx = 1;
		contraintes.gridy = 0;
		panel.add(fichierRegles,contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 1;
		panel.add( affichageBR,contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 2;
		panel.add(calculerChainageAvantLargeur,contraintes);
		
		contraintes.gridx = 1;
		contraintes.gridy = 2;
		panel.add(calculerChainageAvantProfondeur,contraintes);
		
		contraintes.gridx = 2;
		contraintes.gridy = 2;
		panel.add(calculerChainageArriere,contraintes);
		
		contraintes.gridx = 3;
		contraintes.gridy = 2;
		panel.add(calculerChainageMixte,contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 6;	
		panel.add(selectionFait, contraintes);
		
		contraintes.gridx = 1;
		contraintes.gridy = 6;	
		panel.add(ajouterFait, contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 7;	
		panel.add(selectionObjectif, contraintes);
		
		contraintes.gridx = 1;
		contraintes.gridy = 7;	
		panel.add(ajouterObjectif, contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 8;
		panel.add(removeFO, contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 9;
		panel.add(listeFaits, contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 10;
		panel.add(listeObjectifs, contraintes);
		
		contraintes.gridx = 0;
		contraintes.gridy = 11;
		//panel.add(resultatExecution, contraintes);
		panel.add(new JScrollPane(resultatExecution), contraintes);
		
		contraintes.gridx = 1;
		contraintes.gridy = 12;
		panel.add(verbose, contraintes);
		
		System.out.println("redirection de flux");
		
		Container con = getContentPane();
		con.add(panel);
		browse.addActionListener(this);
		removeFO.addActionListener(this);
		ajouterFait.addActionListener(this);
		ajouterObjectif.addActionListener(this);
		calculerChainageAvantProfondeur.addActionListener(this);
		calculerChainageAvantLargeur.addActionListener(this);
		calculerChainageArriere.addActionListener(this);
		calculerChainageMixte.addActionListener(this);
		
		
		
		pack();
	}
	

	/**
	 * actionPerformed
	 * @param ActionEvent
	 * redéfinie les actions à effectuer lors des événements déclenchés par l'utilisateur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == browse){
			afficherRegles();
		}
		
		else if(source == calculerChainageAvantLargeur || source == calculerChainageAvantProfondeur|| source == calculerChainageArriere || source == calculerChainageMixte){
			resultatExecution.setText("");
			lancerChainage(source);
		}
		
		else if(source == ajouterFait ){
			String faits=listeFaits.getText();
			BF.add(selectionFait.getText());
			faits=faits+" "+selectionFait.getText();
			listeFaits.setText(faits);
			selectionFait.setText("");
		}
		
		else if(source == ajouterObjectif){
			String objectifs=listeObjectifs.getText();
			objectif.add(selectionObjectif.getText());
			objectifs=objectifs+" "+selectionObjectif.getText();
			listeObjectifs.setText(objectifs);
			selectionObjectif.setText("");
		}
		
		else if(source == removeFO){
			BF.clear();
			objectif.clear();
			listeFaits.setText("faits         : ");
			listeObjectifs.setText("objectifs : ");
		}
	}
	
	public void lancerChainage(Object source){
		//redirection du flux de sortie
		if(verbose.isSelected()){
			System.setOut(textAreaOutput);		
		}
		else{
			System.setOut(nullOutput);
		}
		ArrayList<Regle> r = (ArrayList<Regle>) regles.clone();
		ArrayList<String> b = (ArrayList<String>) BF.clone();
		ArrayList<String> o = (ArrayList<String>) objectif.clone();
		if(source == calculerChainageAvantLargeur){
			ChainageAvant chainageAvant=new ChainageAvant(r, b, o);
			chainageAvant.run();
			
		}
		
		else if (source == calculerChainageAvantProfondeur){
			ChainageAvant chainageAvant=new ChainageAvant(r, b, o);
			chainageAvant.runProfondeur();
		}
		
		else if(source == calculerChainageArriere ){
			ChainageArriere chainageArriere=new ChainageArriere(r, b, o);
			chainageArriere.run();
		}
		
		else {
			ChainageMixte chainageMixte=new ChainageMixte(r, b, o);
			chainageMixte.run();	
		}
		System.setOut(textAreaOutput);
		System.out.println(BF);
		
	}
	
	public void afficherRegles(){
		int result = c.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = c.getSelectedFile();
			fichierRegles.setText("Fichier selectionnÃ©: " + selectedFile.getName());
			
			ExtracteurPropositions ep = new ExtracteurPropositions(selectedFile.getAbsolutePath());
			ep.extraction();
			regles = ep.getRegles();
			String baseDeRegles = "<html><b>RÃ¨gles</b> : <br>";
			for(Regle regle : regles){
				baseDeRegles+=regle+"<br>";
			}
			baseDeRegles += "</html>";
			affichageBR.setText(baseDeRegles);
			pack();
		}		
	}
	
	public void afficherResultatExecution(String trace){
		if(!resultatExecution.getText().isEmpty()){
			resultatExecution.setText("");
		}
		resultatExecution.setText(trace);
	}

}
