package felix.vue;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import felix.Felix;
import felix.controleur.ControleurFelix;
import felix.controleur.VueFelix;

/**
 * Classe de la vue de connexion de Felix.
 * 
 * Cette vue permet de saisir le port et l'adresse du serveur auquel
 * l'utilisateur veut se connecter.
 * 
 * @version 1.0
 * @author Robin Carrez
 *
 */
public class VueConnexion extends VueFelix implements ActionListener, Runnable {

	/**
	 * La fenêtre de la vue.
	 */
	private Fenetre fenetre;

	/**
	 * Le conteneur de la vue.
	 */
	private Container contenu;

	/**
	 * Les panels
	 */
	private JPanel panelIP, panelPort, panelButton, panelMessage;

	/**
	 * Le champs de saisi de l'IP.
	 */
	private JTextField ipSaisie;

	/**
	 * Le champs de saisie du port.
	 */
	private JTextField portSaisie;

	/**
	 * Le champs pour les messages (non éditable).
	 */
	private JTextField message;
	private JLabel messageInfo;

	/**
	 * Le label de l'IP.
	 */
	private JLabel labelIp;

	/**
	 * Le label du port.
	 */
	private JLabel labelPort;

	/**
	 * Le bouton de connexion.
	 */
	private JButton buttonConnexion;

	/**
	 * Constructeur de la vue de connexion.
	 * 
	 * @param controleur le controleur du chat.
	 */
	public VueConnexion(ControleurFelix controleur) {

		super(controleur);

		final Integer largeur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_LARGEUR"));
		final Integer hauteur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_HAUTEUR"));

		this.fenetre = new Fenetre(largeur, hauteur, Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE"));
		this.fenetre.setName(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE")); // set name for Jemmy
		this.fenetre.setResizable(false);

		this.construireFenetre();
	}

	/**
	 * Construire les panneaux et les widgets de contrôle de la vue.
	 */
	private void construireFenetre() {
		this.construirePanneaux();
		this.construireControles();
	}

	/**
	 * Construction et ajout des composants de la fenêtre.
	 */
	private void construireControles() {

		/*
		 * Saisie IP
		 */
		this.panelIP.setLayout(new FlowLayout());
		this.labelIp = new JLabel("IP : ");
		this.labelIp.setPreferredSize(new Dimension(30, 20));
		this.ipSaisie = new JTextField(Felix.CONFIGURATION.getString("ADRESSE_CHAT"), 30);
		this.ipSaisie.setName(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_IP"));	// set name for Jemmy
		this.panelIP.add(labelIp);
		this.panelIP.add(ipSaisie);

		/*
		 * Saisie PORT
		 */
		this.panelPort.setLayout(new FlowLayout());
		this.labelPort = new JLabel("PORT : ");
		this.labelPort.setPreferredSize(new Dimension(50, 20));
		this.portSaisie = new JTextField(Felix.CONFIGURATION.getString("PORT_CHAT"), 5);
		this.portSaisie.setName(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_PORT"));	// set name for Jemmy
		this.panelPort.add(labelPort);
		this.panelPort.add(portSaisie);

		/*
		 * Message informatif
		 */
		/*this.message = new JTextField(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT"));
		this.message.setName(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE"));	// set name for Jemmy
		this.message.setEditable(false);
		this.message.setEnabled(false);
		this.message.setPreferredSize(new Dimension(350, 20));
		this.panelMessage.add(message);*/
		this.messageInfo = new JLabel(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT"));
		this.messageInfo.setName(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE"));	// set name for Jemmy
		//this.messageInfo.setEditable(false);
		this.messageInfo.setEnabled(false);
		this.messageInfo.setPreferredSize(new Dimension(350, 20));
		this.panelMessage.add(messageInfo);


		/**
		 * Bouton de connexion
		 */
		this.buttonConnexion = new JButton("Connexion");
		this.buttonConnexion.setName(Felix.CONFIGURATION.getString("BOUTON_CONNEXION"));	// set name for Jemmy
		this.buttonConnexion.setPreferredSize(new Dimension(200, 20));
		this.buttonConnexion.addActionListener(this);
		this.panelButton.add(buttonConnexion);

	}

	/**
	 * Construction et ajout des différents panneaux qui composent la fenêtre.
	 */
	private void construirePanneaux() {
		this.contenu = this.fenetre.getContentPane();
		this.contenu.setLayout(new FlowLayout());

		this.panelIP = new JPanel();
		this.contenu.add(this.panelIP);

		this.panelPort = new JPanel();
		this.contenu.add(this.panelPort);

		this.panelMessage = new JPanel();
		this.contenu.add(this.panelMessage);

		this.panelButton = new JPanel();
		this.contenu.add(this.panelButton);
	}

	@Override
	/**
	 * Actions réalisées au lancement du Thread.
	 */
	public void run() {
		try {
			String ip = this.ipSaisie.getText();
			int port = Integer.parseInt(this.portSaisie.getText());
			Thread.sleep(500);
			this.donneControleur().connecteCamix(ip, port);

		} catch (IOException | InterruptedException e) {
			afficheErreurCo();
		}
	}

	@Override
	/**
	 * Gestion des évènements de la fenêtre (ici uniquement bouton de connexion.
	 * 
	 * @param arg0 l'event
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.buttonConnexion) {
			try {
				String ip = this.ipSaisie.getText();
				int port = Integer.parseInt(this.portSaisie.getText());
				/*this.message.setText(
						String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"), ip, port));*/
				this.messageInfo.setText(
						String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"), ip, port));
				this.buttonConnexion.setEnabled(false);
				new Thread(this).start();
			} catch (NumberFormatException ex) {
				System.err.println("Port doit être un nombre");
			}
		}
	}

	@Override
	/**
	 * Affichage de la fenêtre.
	 */
	public void affiche() {
		this.fenetre.setVisible(true);
	}

	@Override
	/**
	 * Fermeture de la fenêtre.
	 */
	public void ferme() {
		this.fenetre.dispose();
	}

	/**
	 * Affichage du message d'erreur si la connexion échoue.
	 */
	public void afficheErreurCo() {
		String ip = this.ipSaisie.getText();
		int port = Integer.parseInt(this.portSaisie.getText());
		/*this.message.setText(String
				.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"), ip, port));*/
		this.messageInfo.setText(String
				.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"), ip, port));
		this.buttonConnexion.setEnabled(true);
	}

}
