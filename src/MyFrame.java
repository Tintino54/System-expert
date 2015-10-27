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
	
	JRadioButton[] boutonFaits = new JRadioButton[17];
	JRadioButton[] boutonObjectifs = new JRadioButton[17];
	
	//règles dans lesquelles nous allons chercher l'objectif
			ArrayList<Regle> regles = new ArrayList<Regle>();
			//Ensemble des faits
			ArrayList<String> BF = new ArrayList<String>();
			//ce que nous recherchons
			ArrayList<String> objectif = new ArrayList<String>();
			
			

	public MyFrame(String nom){
		super(nom);
		
		boutonFaits = initialisation(boutonFaits);	
		boutonObjectifs = initialisation(boutonObjectifs);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200,200,500,600);
		
		affichageBF.setSize(250, 150);
		affichageBR.setSize(250, 150);

		fichierRegles.setText("");
		
		panel.add( browse);
		panel.add(fichierRegles);
		panel.add( affichageBF);
		panel.add( affichageBR);
		panel.add(calculerChainageAvantLargeur);
		panel.add(calculerChainageAvantProfondeur);
		panel.add(calculerChainageArriere);
		panel.add(calculerChainageMixte);
		for(int i = 0; i<boutonFaits.length; i++){
			panel.add(boutonFaits[i]);
		}
		for(int i = 0; i<boutonObjectifs.length; i++){
			panel.add(boutonObjectifs[i]);
		}

		Container con = getContentPane();
		con.add(panel);
		browse.addActionListener(this);
		calculerChainageAvantProfondeur.addActionListener(this);
		calculerChainageAvantLargeur.addActionListener(this);
		calculerChainageArriere.addActionListener(this);
		calculerChainageMixte.addActionListener(this);
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
		Object source = e.getSource();
		if(source == browse){
			int result = c.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = c.getSelectedFile();
				fichierRegles.setText("Fichier selectionné: " + selectedFile.getName());
				
				objectif.add("tagada");
				objectif.add("soleil");
				objectif.add("neige");
				
				ExtracteurPropositions ep = new ExtracteurPropositions(selectedFile.getAbsolutePath());
				ep.extraction();
				regles = ep.getPropositions();
				BF = ep.getBaseFaits();
				String baseDeFaits = "<html><b>Faits</b> : <br>";
				for(String fait : BF){
					baseDeFaits+=fait+"<br>";
				}
				baseDeFaits += "<br><br><br></html>";
				String baseDeRegles = "<html><b>Règles</b> : <br>";
				for(Regle regle : regles){
					baseDeRegles+=regle+"<br>";
				}
				baseDeRegles += "</html>";
				affichageBF.setText(baseDeFaits);
				affichageBR.setText(baseDeRegles);
			}
		}
		
		else if(source == calculerChainageAvantLargeur){
			getFait();
			ChainageAvant cal = new ChainageAvant(regles, BF, objectif);
			cal.run();
		}
		
		else if(source == calculerChainageArriere){
			getFait();
			ChainageArriere car = new ChainageArriere(regles, BF, objectif);
			car.run();
		}
		
		else if(source == calculerChainageMixte){
			getFait();
			ChainageMixte cm = new ChainageMixte(regles, BF, objectif);
			cm.run();
			
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
