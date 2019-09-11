package packageTP2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class FramePrincipal extends JFrame {
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmNouveauClient;
	private JMenuItem mntmFermetureDeSession;
	private JPanel panelClient;
	private JPanel panelCommande;
	private JPanel panelInventaire;
	private JPanel panelBoutons;
	private JLabel lblNoCarteMembre;
	private JLabel lblNomDuClient;
	private JLabel lblNombreDePoints;
	private JTextField champNoCarteMembre;
	private JTextField champNomClient;
	private JTextField champNombreDePoints;
	private JLabel lblCommande;
	private JLabel lblArticle;
	private JLabel lblPrixUnitaire;
	private JTextField champPrixUnitaire;
	private JButton btnTerminer;
	private JButton btnAchat;
	private JLabel lblQteStock;
	private JTextField champQteStock;
	private JTable tableFacture;
	private JLabel lblMontantDonn;
	private JLabel lblMontantRemis;
	private JTextField champMontantDonne;
	private JTextField champMontantRemis;
	private JButton btnNouvelleCommande;


	private double prixApresTaxes;
	private DecimalFormat df;

	private JScrollPane scrollPaneTblFacture;
	private DefaultTableModel modeleTableFacture;

	private NouveauClientDialogue nouveauClientDialogue;

	private Ecouteur ec;
	private JComboBox<String> comboArticle;

	private Client clientActuel;
	private Commande commandeActuelle;
	private JButton btnAnnulerTransaction;
	private JButton btnAnnulerDernierArticle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FramePrincipal frame = new FramePrincipal();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public FramePrincipal() throws InvalidFormatException, IOException{

		setTitle("SuperCheapAuto");
		setBounds(100, 100, 571, 569);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		mntmNouveauClient = new JMenuItem("Nouveau Client");
		mnOptions.add(mntmNouveauClient);

		mntmFermetureDeSession = new JMenuItem("Fermeture de Session");
		mnOptions.add(mntmFermetureDeSession);
		getContentPane().setLayout(null);

		panelClient = new JPanel();
		panelClient.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelClient.setBackground(new Color(102, 204, 51));
		panelClient.setBounds(10, 11, 535, 109);
		getContentPane().add(panelClient);
		panelClient.setLayout(null);

		lblNoCarteMembre = new JLabel("Nom\u00E9ro de la carte Boni:");
		lblNoCarteMembre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNoCarteMembre.setBounds(10, 11, 179, 25);
		panelClient.add(lblNoCarteMembre);

		lblNomDuClient = new JLabel("Nom du client :");
		lblNomDuClient.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNomDuClient.setBounds(10, 38, 189, 32);
		panelClient.add(lblNomDuClient);

		lblNombreDePoints = new JLabel("Nombre de points bonis \u00E0 ce jour :");
		lblNombreDePoints.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNombreDePoints.setBounds(10, 66, 254, 32);
		panelClient.add(lblNombreDePoints);

		champNoCarteMembre = new JTextField();
		champNoCarteMembre.setBounds(439, 15, 86, 20);
		panelClient.add(champNoCarteMembre);
		champNoCarteMembre.setColumns(10);

		champNomClient = new JTextField();
		champNomClient.setEditable(false);
		champNomClient.setBounds(370, 46, 155, 20);
		panelClient.add(champNomClient);
		champNomClient.setColumns(10);

		champNombreDePoints = new JTextField();
		champNombreDePoints.setEditable(false);
		champNombreDePoints.setBounds(415, 74, 110, 20);
		panelClient.add(champNombreDePoints);
		champNombreDePoints.setColumns(10);

		panelCommande = new JPanel();
		panelCommande.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelCommande.setBackground(new Color(255, 153, 102));
		panelCommande.setBounds(10, 135, 339, 149);
		getContentPane().add(panelCommande);
		panelCommande.setLayout(null);

		lblCommande = new JLabel("COMMANDE");
		lblCommande.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCommande.setBounds(20, 11, 103, 28);
		panelCommande.add(lblCommande);

		lblArticle = new JLabel("Article");
		lblArticle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblArticle.setBounds(20, 50, 65, 28);
		panelCommande.add(lblArticle);

		lblPrixUnitaire = new JLabel("Prix unitaire:");
		lblPrixUnitaire.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrixUnitaire.setBounds(20, 82, 84, 28);
		panelCommande.add(lblPrixUnitaire);

		champPrixUnitaire = new JTextField();
		champPrixUnitaire.setHorizontalAlignment(SwingConstants.CENTER);
		champPrixUnitaire.setEditable(false);
		champPrixUnitaire.setBounds(20, 106, 84, 32);
		panelCommande.add(champPrixUnitaire);
		champPrixUnitaire.setColumns(10);

		lblQteStock = new JLabel("Qt\u00E9 en stock :");
		lblQteStock.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQteStock.setBounds(231, 82, 84, 23);
		panelCommande.add(lblQteStock);

		champQteStock = new JTextField();
		champQteStock.setHorizontalAlignment(SwingConstants.RIGHT);
		champQteStock.setEditable(false);
		champQteStock.setBounds(241, 106, 63, 26);
		panelCommande.add(champQteStock);
		champQteStock.setColumns(10);

		comboArticle = new JComboBox();
		comboArticle.setBounds(75, 51, 249, 26);
		panelCommande.add(comboArticle);

		panelInventaire = new JPanel();
		panelInventaire.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelInventaire.setBackground(new Color(204, 51, 0));
		panelInventaire.setBounds(10, 295, 535, 203);
		getContentPane().add(panelInventaire);
		panelInventaire.setLayout(null);

		lblMontantDonn = new JLabel("Montant donn\u00E9:");
		lblMontantDonn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMontantDonn.setBounds(20, 136, 98, 25);
		panelInventaire.add(lblMontantDonn);

		lblMontantRemis = new JLabel("Montant remis:");
		lblMontantRemis.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMontantRemis.setBounds(20, 167, 98, 25);
		panelInventaire.add(lblMontantRemis);

		champMontantDonne = new JTextField();
		champMontantDonne.setBounds(288, 139, 86, 20);
		panelInventaire.add(champMontantDonne);
		champMontantDonne.setEditable(false);
		champMontantDonne.setColumns(10);

		champMontantRemis = new JTextField();
		champMontantRemis.setColumns(10);
		champMontantRemis.setBounds(288, 170, 86, 20);
		champMontantRemis.setEditable(false);
		panelInventaire.add(champMontantRemis);

		btnNouvelleCommande = new JButton("Nouvelle Commande");
		btnNouvelleCommande.setBounds(384, 136, 141, 56);
		panelInventaire.add(btnNouvelleCommande);

		scrollPaneTblFacture = new JScrollPane();
		scrollPaneTblFacture.setBounds(10, 11, 515, 112);
		panelInventaire.add(scrollPaneTblFacture);

		modeleTableFacture = new DefaultTableModel();

		tableFacture = new JTable(modeleTableFacture);
		tableFacture.setBounds(10, 11, 515, 114);
		scrollPaneTblFacture.setViewportView(tableFacture);

		modeleTableFacture.addColumn("Produit");
		modeleTableFacture.addColumn("Quantité");
		modeleTableFacture.addColumn("Prix");


		panelBoutons = new JPanel();
		panelBoutons.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelBoutons.setBackground(new Color(255, 153, 102));
		panelBoutons.setBounds(370, 135, 175, 149);
		getContentPane().add(panelBoutons);
		panelBoutons.setLayout(new GridLayout(4, 1, 0, 2));

		btnAchat = new JButton("Achat");
		panelBoutons.add(btnAchat);

		btnTerminer = new JButton("Terminer");
		panelBoutons.add(btnTerminer);

		btnAnnulerDernierArticle = new JButton("Annuler dernier article");
		btnAnnulerDernierArticle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panelBoutons.add(btnAnnulerDernierArticle);

		btnAnnulerTransaction = new JButton("Annuler transaction");
		btnAnnulerTransaction.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panelBoutons.add(btnAnnulerTransaction);


		ec = new Ecouteur();
		champNoCarteMembre.addActionListener(ec);
		btnAchat.addActionListener(ec);
		btnTerminer.addActionListener(ec);
		btnNouvelleCommande.addActionListener(ec);
		mntmNouveauClient.addActionListener(ec);
		mntmFermetureDeSession.addActionListener(ec);
		champMontantDonne.addActionListener(ec);
		btnAnnulerDernierArticle.addActionListener(ec);
		btnAnnulerTransaction.addActionListener(ec);

		comboArticle.addItemListener(ec);

		df = new DecimalFormat("###,##0.00$");

		remplirHashTables();
		remplirComboArticle();
		//Fonction qui remet tous les champs textes et les boutons a l'etat initiale d'une transaction
		reinitialiserFacture();
	}

//----------------	ECOUTEUR --------------------------------------------------------

	private class Ecouteur implements ActionListener, ItemListener
	{

		@Override
		public void actionPerformed(ActionEvent ae) {

			//---- Champ NoCarteMembre--------------------------
			if(ae.getSource() == champNoCarteMembre)
			{

				String NoCarteMembre = champNoCarteMembre.getText();
				clientActuel = EnsembleClients.getClient(NoCarteMembre);

				if(clientActuel != null)
				{
					champNomClient.setText(clientActuel.getNom());
					champNombreDePoints.setText("" + clientActuel.getNbPointsAcc());

					commandeActuelle = new Commande(clientActuel.getNoCarte());

					champNomClient.setEditable(false);
					btnAchat.setEnabled(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Veuillez entrer un numéro de client valide", "Attention", JOptionPane.INFORMATION_MESSAGE);
					champNoCarteMembre.setText("");
					champNomClient.setText("");
					champNombreDePoints.setText("");
				}

				btnAnnulerTransaction.setEnabled(true);
				champNoCarteMembre.setEditable(false);

			}
			//---- Bouton Achat --------------------------
			else if(ae.getSource() == btnAchat)
			{
				Produit produitSel = Inventaire.getListe().get((String)comboArticle.getSelectedItem());

				if(produitSel.getQteStock() > 0)
				{
					ajouterInfoTableFacture(produitSel);
					btnTerminer.setEnabled(true);
					btnAnnulerDernierArticle.setEnabled(true);
					btnAnnulerTransaction.setEnabled(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Quantité insuffusante restante du produit", "Attention", JOptionPane.INFORMATION_MESSAGE);
				}

			}
			//---- Bouton Terminer--------------------------
			else if(ae.getSource() == btnTerminer)
			{
				ajouterItemsCommande();

				double prixAvantTaxes = commandeActuelle.calculerSousTotal();
				double TPS = commandeActuelle.calculerTPS();
				double TVQ = commandeActuelle.calculerTVQ();
				prixApresTaxes = commandeActuelle.calculerGrandTotal();
				int qteArticles = commandeActuelle.nbArticlesTotalFacture();

				modeleTableFacture.addRow(new Object[] {"Sous total : ", null, df.format(prixAvantTaxes)});
				modeleTableFacture.addRow(new Object[] {"TPS : ", null, df.format(TPS)});
				modeleTableFacture.addRow(new Object[] {"TVQ: ", null, df.format(TVQ)});
				modeleTableFacture.addRow(new Object[] {"Total après taxes : ", qteArticles, df.format(prixApresTaxes)});
				btnAchat.setEnabled(false);
				btnTerminer.setEnabled(false);
				champMontantDonne.setEditable(true);
				btnAnnulerDernierArticle.setEnabled(false);

			}
			//---- Bouton AnnulerDernierArticle--------------------------
			else if(ae.getSource() == btnAnnulerDernierArticle)
			{
				reajusterInventaireUnItem();

				String nomProduitSel = (String)comboArticle.getSelectedItem();
				Produit produitSel = Inventaire.getListe().get(nomProduitSel);

				champQteStock.setText(Integer.toString(produitSel.getQteStock()));
			}
			//---- Bouton AnnulerTransaction--------------------------
			else if(ae.getSource() == btnAnnulerTransaction)
			{
				reinitialierInventaire();
				reinitialiserFacture();
			}
			//---- Champ MontantDonne--------------------------
			else if(ae.getSource() == champMontantDonne)
			{
				double montantDonne;

				try
				{
					montantDonne = Double.parseDouble(champMontantDonne.getText());

					if(clientActuel.assezArgent(commandeActuelle, montantDonne))
					{
						clientActuel.paieCommande(commandeActuelle, montantDonne);

						double montantRemis = montantDonne - prixApresTaxes;
						champMontantRemis.setText("" + df.format(montantRemis));
						champMontantDonne.setText("" + df.format(montantDonne));

						champNombreDePoints.setText("" + clientActuel.getNbPointsAcc());
						champMontantDonne.setEditable(false);
						btnNouvelleCommande.setEnabled(true);
						btnAnnulerTransaction.setEnabled(false);

						JOptionPane.showMessageDialog(FramePrincipal.this, "Transaction réussie", "Attention", JOptionPane.INFORMATION_MESSAGE);

					}
					else
					{
						JOptionPane.showMessageDialog(null, "Montant insuffisant", "Erreur", JOptionPane.ERROR_MESSAGE);
						champMontantDonne.setText("");
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(FramePrincipal.this, "Montant invalide", "Attention", JOptionPane.INFORMATION_MESSAGE);
					champMontantDonne.setText("");
				}
			}
			//---- Bouton NouvelleCommande--------------------------
			else if(ae.getSource() == btnNouvelleCommande)
			{
				reinitialiserFacture();
			}
			//---- Menu Item NouveauClient--------------------------
			else if(ae.getSource() == mntmNouveauClient)
			{
				nouveauClientDialogue = new NouveauClientDialogue(FramePrincipal.this);
				nouveauClientDialogue.setLocationRelativeTo(FramePrincipal.this);
				nouveauClientDialogue.setVisible(true);

			}
			//---- Menu Item FermetureDeSession--------------------------
			else if(ae.getSource() == mntmFermetureDeSession)
			{
				//Fonction pour ecrire dans le fichier xlsx malheureusement non fontionnelle
				//ecritureFichierFermetureSession();

				System.exit(0);
			}


		}

		@Override
		public void itemStateChanged(ItemEvent arg0) 
		{
			//---- ComboBox Article--------------------------

			String nomProduitSel = (String)comboArticle.getSelectedItem();
			Produit produitSel = Inventaire.getListe().get(nomProduitSel);

			double prix = produitSel.getPrix();

			champPrixUnitaire.setText(df.format(prix));
			champQteStock.setText(Integer.toString(produitSel.getQteStock()));
		}

	}
//-------------------------------------------------------------------------------------------

	private void remplirComboArticle()
	{
		Set<String> articles = Inventaire.getListe().keySet();

		for(String a : articles)
		{
			comboArticle.addItem(a);
		}
	}

//----------------
	private void ajouterInfoTableFacture(Produit produitSel)
	{
		String nom = produitSel.getNom();
		int quantite;
		double prix = produitSel.getPrix();

		int ligneProduitSiExistant = produitDejaSurFacture(nom);

		if(ligneProduitSiExistant >= 0)
		{
			//Modifier la quantite du produit
			int quantiteINT = (int) modeleTableFacture.getValueAt(ligneProduitSiExistant, 1);
			quantite = ++quantiteINT;
			modeleTableFacture.setValueAt(quantite, ligneProduitSiExistant, 1);

			//Modifier le prix total
			double quantiteDOUBLE = (double) quantite;

			modeleTableFacture.setValueAt(df.format((quantiteDOUBLE * prix)), ligneProduitSiExistant, 2);			
		}

		else
		{
			quantite = 1;
			modeleTableFacture.addRow(new Object[] {nom, 1, df.format(prix)});
		}


		produitSel.modifierQteStock(1);
		champQteStock.setText(Integer.toString(produitSel.getQteStock()));
	}

//----------------
	private int produitDejaSurFacture(String nomProduit)
	{
		for(int i = 0; i < modeleTableFacture.getRowCount(); i++)
		{
			if(modeleTableFacture.getValueAt(i, 0) == nomProduit)
			{
				return i;
			}
		}

		return -1;
	}

//----------------
	private void ajouterItemsCommande()
	{
		for(int i = 0; i < modeleTableFacture.getRowCount(); i++)
		{
			String nom = modeleTableFacture.getValueAt(i, 0).toString();
			int qte = (int) modeleTableFacture.getValueAt(i, 1);
			int noCommande = commandeActuelle.getNumero();

			commandeActuelle.ajouterItem(new Item(nom, qte, noCommande));
		}
	}

//----------------
	private void effacerTableau()
	{
		for(int i = modeleTableFacture.getRowCount() - 1; i >= 0; i--) 
		{
			modeleTableFacture.removeRow(i);
		}
	}

//----------------
	public void reinitialierInventaire()
	{

		if(modeleTableFacture.getRowCount() == 0)
		{
			champNoCarteMembre.setText("");
		}
		else
		{

			int derniereRangee = modeleTableFacture.getRowCount() - 1;
			int rangeeDepart = 0; 

			if(modeleTableFacture.getValueAt(derniereRangee, 0).equals("Total après taxes : "))
			{
				rangeeDepart = derniereRangee - 4;
			}
			else
			{
				rangeeDepart = derniereRangee;
			}
			for(int i = rangeeDepart; i >= 0; i--) 
			{
				String nomProduit = (String) modeleTableFacture.getValueAt(i, 0);
				int qteRemettre = (int) modeleTableFacture.getValueAt(i, 1);

				for(String cle: Inventaire.getListe().keySet())
				{
					if(cle.equals(nomProduit))
					{
						Produit p = Inventaire.getProduit(nomProduit);
						p.setQteStock(qteRemettre);
					}
				}
			}
		}
	}

//----------------
	private void reajusterInventaireUnItem()
	{
		int rangeeDernierItemFacture = modeleTableFacture.getRowCount() - 1;

		String nomProduit = (String) modeleTableFacture.getValueAt(rangeeDernierItemFacture, 0);
		int qteINT = (int) modeleTableFacture.getValueAt(rangeeDernierItemFacture, 1);
		double qte = (double) qteINT;
		double nouvelleQte = qte - 1;


		for(String cle: Inventaire.getListe().keySet())
		{
			if(cle.equals(nomProduit))
			{
				Produit p = Inventaire.getProduit(nomProduit);
				//on ajoute une fois l'item a l'inventaire qu'on retire de la facture
				p.setQteStock(1);

				double prix = p.getPrix();

				//Reajuste la quantite affiche dans la JTable
				modeleTableFacture.setValueAt(--qteINT, rangeeDernierItemFacture, 1);
				//Recalcule le prix de la ligne ou l'item a ete retire
				modeleTableFacture.setValueAt(df.format(prix*nouvelleQte), rangeeDernierItemFacture, 2);
			}
		}

		//si la quantite du produit est a zero dans la ligne ou l'on retire un item
		if(nouvelleQte == 0)
		{
			//on enleve la ligne de la JTable
			modeleTableFacture.removeRow(rangeeDernierItemFacture);

			//s'il ne reste plus de ligne dans la JTable facture
			if(modeleTableFacture.getRowCount() == 0)
			{
				btnAnnulerDernierArticle.setEnabled(false);
				btnTerminer.setEnabled(false);
			}
		}
	}

//----------------

	//Fonction qui iniatialise les champs et boutons pour une nouvelle transaction
	public void reinitialiserFacture()
	{
		prixApresTaxes = 0;

		comboArticle.setSelectedIndex(0);

		champMontantDonne.setText("");
		champMontantRemis.setText("");
		champNoCarteMembre.setText("");
		champNombreDePoints.setText("");
		champNomClient.setText("");

		String nomProduitSel = (String)comboArticle.getSelectedItem();
		Produit produitSel = Inventaire.getListe().get(nomProduitSel);

		double prix = produitSel.getPrix();

		champPrixUnitaire.setText(df.format(prix));
		champQteStock.setText(Integer.toString(produitSel.getQteStock()));

		btnAchat.setEnabled(false);
		btnTerminer.setEnabled(false);
		btnNouvelleCommande.setEnabled(false);
		champNoCarteMembre.setEditable(true);
		btnAnnulerDernierArticle.setEnabled(false);
		btnAnnulerTransaction.setEnabled(false);
		champMontantDonne.setEditable(false);

		effacerTableau();
	}

//----------------
	private void remplirHashTables() throws InvalidFormatException, IOException
	{

		try {

			InputStream inputStream = new FileInputStream ("Clients.xlsx");

			XSSFWorkbook classeur = ( XSSFWorkbook ) WorkbookFactory.create(inputStream);
			XSSFSheet feuille = classeur.getSheetAt(0);

			int nbRangees = feuille.getLastRowNum();

			for(int i = 1; i <= nbRangees; i++)
			{
				XSSFRow rangee = feuille.getRow(i);

				XSSFCell celluleNoCarteMembre  = rangee.getCell(0);
				double NoCarteMembreDOUBLE = celluleNoCarteMembre.getNumericCellValue();
				int NoCarteMembreINT = (int)NoCarteMembreDOUBLE;
				String NoCarteMembre = Integer.toString(NoCarteMembreINT);

				XSSFCell celluleNomClient  = rangee.getCell(1);
				String nomClient = celluleNomClient.toString();

				XSSFCell celluleNbPoints = rangee.getCell(2);
				double nbPointsDOUBLE = celluleNbPoints.getNumericCellValue();
				int nbPoints = (int)nbPointsDOUBLE;

				inputStream.close();

				EnsembleClients.ajouterClient(new Client(NoCarteMembre, nomClient, nbPoints));

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		try {

			InputStream inputStream = new FileInputStream ("Produits.xlsx");

			XSSFWorkbook classeur = ( XSSFWorkbook ) WorkbookFactory.create(inputStream);
			XSSFSheet feuille = classeur.getSheetAt(0);

			int nbRangees = feuille.getLastRowNum();

			for(int i = 1; i <= nbRangees; i++)
			{
				XSSFRow rangee = feuille.getRow(i);

				XSSFCell celluleCodeProduit  = rangee.getCell(0);
				double codeProduitDOUBLE = celluleCodeProduit.getNumericCellValue();
				int codeProduit = (int)codeProduitDOUBLE;

				XSSFCell celluleNomProduit  = rangee.getCell(1);
				String nomProduit = celluleNomProduit.toString();

				XSSFCell celluleQuantite  = rangee.getCell(2);
				double quantiteDOUBLE = celluleQuantite.getNumericCellValue();
				int quantite = (int)quantiteDOUBLE;


				XSSFCell celluleCout  = rangee.getCell(3);
				double cout = celluleCout.getNumericCellValue();



				XSSFCell celluleNbPoints = rangee.getCell(4);
				double nbPointsDOUBLE = celluleNbPoints.getNumericCellValue();
				int nbPoints = (int)nbPointsDOUBLE;

				inputStream.close();

				Inventaire.ajouterProduit(new Produit(codeProduit, nomProduit, quantite, cout, nbPoints));
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

//----------------
	void ecritureFichierFermetureSession() throws InvalidFormatException, IOException
	{
		try
		{
			int nbClients = EnsembleClients.getListe().size();
			Set<String> cles = EnsembleClients.getListe().keySet();
			Vector<String> numClients = new Vector<String>();

			for(String cle : cles)
			{
				numClients.addElement(cle);
			}

			InputStream inputStream = new FileInputStream ("Clients.xlsx");
			XSSFWorkbook classeur = ( XSSFWorkbook ) WorkbookFactory.create(inputStream);
			XSSFSheet feuille = classeur.getSheetAt(0);

			for(int i = 0; i < nbClients - 1; i++)
			{
				XSSFRow rangee = feuille.getRow(i);

				Client c = EnsembleClients.getClient(numClients.elementAt(i));

				XSSFCell celluleNumClient  = rangee.getCell(0);
				String numClient = c.getNoCarte();
				celluleNumClient.setCellValue(numClient);

				XSSFCell celluleNomClient = rangee.getCell(1);
				String nomClient = c.getNom();
				celluleNomClient.setCellValue(nomClient);

				XSSFCell celluleNbPoints = rangee.getCell(2);
				String nbPoints = Integer.toString(c.getNbPointsAcc());
				celluleNbPoints.setCellValue(nbPoints);

			}

			OutputStream outputStream = new FileOutputStream ( "Clients.xlsx");
			classeur.write(outputStream);  
			outputStream.close();
			inputStream.close();


		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		try
		{
			int nbProduits = Inventaire.getListe().size();
			Set<String> cles = Inventaire.getListe().keySet();
			Vector<String> nomProduits = new Vector<String>();

			for(String cle : cles)
			{
				nomProduits.addElement(cle);
			}

			InputStream inputStream = new FileInputStream ("Produits.xlsx");
			XSSFWorkbook classeur = ( XSSFWorkbook ) WorkbookFactory.create(inputStream);
			XSSFSheet feuille = classeur.getSheetAt(0);

			for(int i = 0; i < nbProduits - 1; i++)
			{
				XSSFRow rangee = feuille.getRow(i);

				Produit p = Inventaire.getProduit(nomProduits.elementAt(i));

				XSSFCell celluleCode = rangee.getCell(0);
				int code = p.getCode();
				celluleCode.setCellValue(code);

				XSSFCell celluleNomProduit  = rangee.getCell(1);
				String nomProduit = p.getNom();
				celluleNomProduit.setCellValue(nomProduit);

				XSSFCell celluleQte = rangee.getCell(2);
				int qte = p.getQteStock();
				celluleQte.setCellValue(qte);

				XSSFCell celluleCout = rangee.getCell(3);
				double cout = p.getPrix();
				celluleCout.setCellValue(cout);

				XSSFCell celluleNbPoints = rangee.getCell(4);
				int nbPoints = p.getPoints();
				celluleNbPoints.setCellValue(nbPoints);

				OutputStream outputStream = new FileOutputStream ("Produits.xlsx");
				classeur.write(outputStream);  
				outputStream.close();
				inputStream.close();

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}


	}



//---------- Getters --------------

	public JTextField getChampNumClient()
	{
		return champNoCarteMembre;
	}

	public JTextField getChampNomClient()
	{
		return champNomClient;
	}

	public JTextField getChampPointsClient()
	{
		return champNombreDePoints;
	}

	public JButton getBouttonAnnulerTrans()
	{
		return btnAnnulerTransaction;
	}

	public JButton getBouttonAchat()
	{
		return btnAchat;
	}

}