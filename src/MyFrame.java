import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;


public class MyFrame extends JFrame implements ActionListener{
	
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
	
	JRadioButton[] boutonFaits = new JRadioButton[17];
	JRadioButton[] boutonObjectifs = new JRadioButton[17];
	
	//règles dans lesquelles nous allons chercher l'objectif
			private ArrayList<Regle> regles = new ArrayList<Regle>();
			//Ensemble des faits
			private ArrayList<String> BF = new ArrayList<String>();
			//ce que nous recherchons
			private ArrayList<String> objectif = new ArrayList<String>();

	public MyFrame(String nom){
		super(nom);
		
		boutonFaits = initialisation(boutonFaits);	
		boutonObjectifs = initialisation(boutonObjectifs);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints contraintes= new GridBagConstraints();
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
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 1;
		contraintes.gridy = 0;
		panel.add(fichierRegles,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 0;
		contraintes.gridy = 1;
		panel.add( affichageBR,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 0;
		contraintes.gridy = 2;
		panel.add(calculerChainageAvantLargeur,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 1;
		contraintes.gridy = 2;
		panel.add(calculerChainageAvantProfondeur,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 2;
		contraintes.gridy = 2;
		panel.add(calculerChainageArriere,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 3;
		contraintes.gridy = 2;
		panel.add(calculerChainageMixte,contraintes);
		
		JPanel buttonsFPanel = new JPanel(new GridLayout(0,2)); 
		for(int i = 0; i<boutonFaits.length; i++){
			buttonsFPanel.add(boutonFaits[i]);
		}
		JPanel buttonsOPanel = new JPanel(new GridLayout(0,2)); 
		for(int i = 0; i<boutonObjectifs.length; i++){
			buttonsOPanel.add(boutonObjectifs[i]);
		}
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 0;
		contraintes.gridy = 3;
		panel.add(labelFait,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 1;
		contraintes.gridy = 3;
		panel.add(buttonsFPanel, contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 2;
		contraintes.gridy = 3;
		panel.add(labelObjectif,contraintes);
		
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 3;
		contraintes.gridy = 3;
		panel.add(buttonsOPanel, contraintes);

		Container con = getContentPane();
		con.add(panel);
		browse.addActionListener(this);
		calculerChainageAvantProfondeur.addActionListener(this);
		calculerChainageAvantLargeur.addActionListener(this);
		calculerChainageArriere.addActionListener(this);
		calculerChainageMixte.addActionListener(this);
		
		pack();
	}
	
	public void getFait(){
		BF.clear();
		for(int i = 0; i<boutonFaits.length; i++){
			if(boutonFaits[i].isSelected()){
				BF.add(boutonFaits[i].getText());
			}
		}
	}
	
	public void getObjectif(){
		objectif.clear();
		for(int i = 0; i<boutonObjectifs.length; i++){
			if(boutonObjectifs[i].isSelected()){
				objectif.add(boutonObjectifs[i].getText());
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Regle> r = (ArrayList<Regle>) regles.clone();
		ArrayList<String> b = (ArrayList<String>) BF.clone();
		ArrayList<String> o = (ArrayList<String>) objectif.clone();
		Object source = e.getSource();
		if(source == browse){
			int result = c.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = c.getSelectedFile();
				fichierRegles.setText("Fichier selectionné: " + selectedFile.getName());
				
				ExtracteurPropositions ep = new ExtracteurPropositions(selectedFile.getAbsolutePath());
				ep.extraction();
				regles = ep.getPropositions();
				String baseDeRegles = "<html><b>Règles</b> : <br>";
				for(Regle regle : regles){
					baseDeRegles+=regle+"<br>";
				}
				baseDeRegles += "</html>";
				affichageBR.setText(baseDeRegles);
				pack();
			}
		}
		
		else if(source == calculerChainageAvantLargeur){
			getFait();
			getObjectif();
			(new ChainageAvant(r, b, o)).run();
		}

		else if(source == calculerChainageAvantProfondeur){
			getFait();
			getObjectif();
			(new ChainageAvant(r, b, o)).runProfondeur();
		}
		
		
		else if(source == calculerChainageArriere){
			getFait();
			getObjectif();
			(new ChainageArriere(r, b, o)).run();
		}
		
		else if(source == calculerChainageMixte){
			getFait();
			getObjectif();
			(new ChainageMixte(r, b, o)).run();
		}
		
		else if(source == calculerChainageArriere){
			getFait();
			getObjectif();
			(new ChainageArriere(r, b, o)).run();
		}
	}
	
	
	
	
	public static JRadioButton[] initialisation(JRadioButton[] jr){
		jr[0] = new JRadioButton("pluie");
		jr[1] = new JRadioButton("soleil");
		jr[2] = new JRadioButton("beau temps");
		jr[3] = new JRadioButton("eruption volcanique");
		jr[4] = new JRadioButton("brouillard");
		jr[5] = new JRadioButton("nuage");
		jr[6] = new JRadioButton("cyclone");
		jr[7] = new JRadioButton("anticyclone");
		jr[8] = new JRadioButton("air chaud");
		jr[9] = new JRadioButton("air froid");
		jr[10] = new JRadioButton("stratus");
		jr[11] = new JRadioButton("cumulus");
		jr[12] = new JRadioButton("cumulonimbus");
		jr[13] = new JRadioButton("neige");
		jr[14] = new JRadioButton("verglas");
		jr[15] = new JRadioButton("orage");
		jr[16] = new JRadioButton("tremblement de terre");
		return jr;
	}
}
