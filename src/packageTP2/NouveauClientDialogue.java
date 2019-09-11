package packageTP2;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JTextField;

public class NouveauClientDialogue extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField champNomClient;
	private JTextField champNumCarteGenere;
	private JButton btnAjouterClient;
	private JButton btnAnnuler;
	
	private FramePrincipal fp;
	private Ecouteur ec;
	private Client nouveauClient;

	public NouveauClientDialogue(FramePrincipal fp) {
		
		this.fp = fp;
		
		setTitle("Ajouter nouveau client");
		setBounds(100, 100, 307, 181);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 1, 260);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);


		btnAjouterClient = new JButton("OK");
		btnAjouterClient.setBounds(42, 91, 86, 32);
		getContentPane().add(btnAjouterClient);


		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(145, 91, 86, 32);
		getContentPane().add(btnAnnuler);


		JLabel lblNomDuClient = new JLabel("Nom du client: ");
		lblNomDuClient.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomDuClient.setBounds(11, 17, 117, 32);
		getContentPane().add(lblNomDuClient);

		JLabel lblNumroDeCarte = new JLabel("Num\u00E9ro de carte g\u00E9n\u00E9r\u00E9:");
		lblNumroDeCarte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumroDeCarte.setBounds(11, 48, 166, 32);
		getContentPane().add(lblNumroDeCarte);

		champNomClient = new JTextField();
		champNomClient.setBounds(171, 25, 110, 20);
		getContentPane().add(champNomClient);
		champNomClient.setColumns(10);

		champNumCarteGenere = new JTextField();
		champNumCarteGenere.setEditable(false);
		champNumCarteGenere.setBounds(195, 56, 86, 20);
		getContentPane().add(champNumCarteGenere);
		champNumCarteGenere.setColumns(10);


		ec = new Ecouteur();

		btnAjouterClient.addActionListener(ec);
		btnAnnuler.addActionListener(ec);
		champNomClient.addActionListener(ec);
		
		//On genere un nombre aleatoire a l'ouverture du dialogue pour l'afficher dans le bon champTexte
		genererNumCarteAleatoire();

	}

	
//----------------	ECOUTEUR --------------------------------------------------------

	private class Ecouteur implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			if(ae.getSource() == btnAjouterClient || ae.getSource() == champNomClient)
			{
				if(!champNomClient.equals(""))
				{				
				//Fonction nouveau client pour l'info nessecaire des champs et cree un objet Client	
				nouveauClient = creerClient();
				EnsembleClients.ajouterClient(nouveauClient);
				
				dispose();
				
				JOptionPane.showMessageDialog(null, "Nouveau client enregistré", "Attention", JOptionPane.INFORMATION_MESSAGE);

				//Fonction qui remplie les champs du FramePrincipal avec les infos du nouveau client et iniatialise les boutons du frame correctement 
				initialiserFactureNouveauClient();
				}
				//Si l'usager n'a pas entrer de nom dans le champNom
				else
				{
					JOptionPane.showMessageDialog(NouveauClientDialogue.this, "Veuillez écrire un nom de client", "Attention", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if(ae.getSource() == btnAnnuler)
			{
				dispose();
			}
		}
	}


	private void genererNumCarteAleatoire()
	{
		int numCarteMin = 100000;
		int numCarteMax = 999999;

		int numCarteINT = numCarteMin + (int)(Math.random() * ((numCarteMax - numCarteMin) + 1));
		String numCarte = Integer.toString(numCarteINT);

		verifNumCarteUnique(numCarte);		 
	}

	private void verifNumCarteUnique(String numCarte)
	{
		Set<String> numCarteExistant = EnsembleClients.getListe().keySet();

		for(String n : numCarteExistant)
		{
			if(n.equals(numCarte))
			{
				//si le numero existe deja, on reappel la fonction genererNumCarteAleatoire() jusqu'a ce qu'on aille un numero unique
				genererNumCarteAleatoire();
			}
			else
			{
				champNumCarteGenere.setText(numCarte);
			}
		}
	}
	
	private Client creerClient()
	{
		String nom = champNomClient.getText();
		String numClient = champNumCarteGenere.getText();
		
		Client nouveauClient = new Client(numClient, nom, 0);
		
		return nouveauClient;
	}
	
	private void initialiserFactureNouveauClient()
	{
		//verification s'il y avait une transaction en cours dans le framePrincipal
		if(!fp.getChampNumClient().getText().equals(""))
		{
			fp.reinitialierInventaire();
		}
		
		fp.reinitialiserFacture();
		
		fp.getChampNomClient().setText(nouveauClient.getNom());
		fp.getChampNumClient().setText(nouveauClient.getNoCarte());
		//tous les nouveaux clients ont 0 points boni accumules
		fp.getChampPointsClient().setText("0");
		
		fp.getChampNumClient().setEditable(false);
		fp.getBouttonAnnulerTrans().setEnabled(true);
		fp.getBouttonAchat().setEnabled(true);

	}
	
	
}

