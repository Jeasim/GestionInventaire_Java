package packageTP2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

public class AjouterNouveauClientFrame extends JFrame {
	private JLabel labelNomClient;
	private JLabel labelNumeroGenere;
	private JTextField champNomNouveauClient;
	private JTextField champNumGenere;
	private JButton btnAjouterClient;
	private JButton btnAnnuler;
	
	private Ecouteur ec;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AjouterNouveauClientFrame frame = new AjouterNouveauClientFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AjouterNouveauClientFrame() {
		setTitle("Inscription d'un nouveau client");
		setBounds(100, 100, 407, 225);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		labelNomClient = new JLabel("Nom du client:");
		labelNomClient.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelNomClient.setBounds(10, 20, 124, 40);
		getContentPane().add(labelNomClient);
		
		labelNumeroGenere = new JLabel("Num\u00E9ro de Carte boni g\u00E9n\u00E9r\u00E9:");
		labelNumeroGenere.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelNumeroGenere.setBounds(10, 71, 219, 40);
		getContentPane().add(labelNumeroGenere);
		
		champNomNouveauClient = new JTextField();
		champNomNouveauClient.setBounds(214, 32, 133, 20);
		getContentPane().add(champNomNouveauClient);
		champNomNouveauClient.setColumns(10);
		
		champNumGenere = new JTextField();
		champNumGenere.setEditable(false);
		champNumGenere.setBounds(261, 83, 86, 20);
		getContentPane().add(champNumGenere);
		champNumGenere.setColumns(10);
		
		btnAjouterClient = new JButton("OK");
		btnAjouterClient.setBounds(52, 136, 100, 39);
		getContentPane().add(btnAjouterClient);
		
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(210, 136, 100, 39);
		getContentPane().add(btnAnnuler);
		
		ec = new Ecouteur();
		btnAjouterClient.addActionListener(ec);
		btnAnnuler.addActionListener(ec);
		
		genererNumCarteAleatoire();
		

	}
	
	private class Ecouteur implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			if(ae.getSource() == btnAjouterClient)
			{
				genererNumCarteAleatoire();
			}
			else if(ae.getSource() == btnAnnuler)
			{
				
			}
			
		}
		
	}
	
	void genererNumCarteAleatoire()
	{
		int numCarteMin = 100000;
		int numCarteMax = 999999;
		
		int numCarteINT = numCarteMin + (int)(Math.random() * ((numCarteMax - numCarteMin) + 1));
		String numCarte = Integer.toString(numCarteINT);
		
		verifNumCarteUnique(numCarte);		 
	}
	
	void verifNumCarteUnique(String numCarte)
	{
		 Set<String> numCarteExistant = EnsembleClients.getListe().keySet();
		 
		 for(String n : numCarteExistant)
		 {
			 if(n.equals(numCarte))
			 {
				 genererNumCarteAleatoire();
			 }
			 else
			 {
				 champNumGenere.setText(numCarte);
			 }
		 }
	}
	
	
	
	
	
	
}
