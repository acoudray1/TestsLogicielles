package felix.vue;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import felix.Felix;

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
public class VueConnexion {

	/**
	 * La fenêtre de la vue.
	 */
	private Fenetre fenetre;

	/**
	 * Le conteneur de la vue.
	 */
	private Container contenu;

	/**
	 * Le champs de saisi de l'IP.
	 */
	private JTextField ipSaisie;

	/**
	 * Le champs de saisie du port.
	 */
	private JTextField portSaisie;

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
	 */
	public VueConnexion() {

		final Integer largeur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_LARGEUR"));
		final Integer hauteur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_HAUTEUR"));

		this.fenetre = new Fenetre(largeur, hauteur, Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE"));

		this.construireFenetre(largeur, hauteur);
	}

	/**
	 * Construire les panneaux et les widgets de contrôle de la vue.
	 *
	 * @param largeur la largeur de la fenêtre.
	 * @param hauteur la hauteur de la fenêtre.
	 */
	private void construireFenetre(Integer largeur, Integer hauteur) {
		this.construirePanneaux();
		this.construireControles(largeur, hauteur);
	}

	private void construireControles(Integer largeur, Integer hauteur) {
		this.contenu = this.fenetre.getContentPane();
		this.contenu.setLayout(new GridLayout(2, 2));
	}

	private void construirePanneaux() {
		// TODO Auto-generated method stub

	}

}
