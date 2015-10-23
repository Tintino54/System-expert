import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;


public class MyFrame extends JFrame implements ActionListener{
	final JButton browse = new JButton("parcourir");
	final JFileChooser c = new JFileChooser();
	final JLabel fichierRegles = new JLabel();
	final JTextField affichageBF = new JTextField();
	final JTextField affichageBR = new JTextField();
	
	//règles dans lesquelles nous allons chercher l'objectif
			ArrayList<Regle> regles = new ArrayList<Regle>();
			//Ensemble des faits
			ArrayList<String> BF = new ArrayList<String>();
			//ce que nous recherchons
			ArrayList<String> objectif = new ArrayList<String>();

	public MyFrame(String nom){
		super(nom);
		JPanel panel = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200,200,600,450);
		
		affichageBF.setSize(250, 150);
		affichageBR.setSize(250, 150);

		fichierRegles.setText("");
		
		panel.add(BorderLayout.PAGE_START, browse);
		panel.add(BorderLayout.PAGE_START, fichierRegles);
		panel.add(BorderLayout.LINE_START, affichageBF);
		panel.add(BorderLayout.LINE_END, affichageBR);

		Container con = getContentPane();
		con.add(panel);
		browse.addActionListener(this);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == browse){
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
				String baseDeFaits = "";
				for(String fait : BF){
					baseDeFaits+=fait+"\n";
				}
				String baseDeRegles = "";
				for(Regle regle : regles){
					baseDeRegles+=regle+"\n";
				}
				affichageBF.setText(baseDeFaits);
				affichageBR.setText(baseDeRegles);
			}
		}
	}
}
