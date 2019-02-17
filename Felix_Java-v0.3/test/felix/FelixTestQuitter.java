package felix;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import felix.communication.Connexion;
import felix.controleur.ControleurFelix;
import felix.vue.VueChat;

// Nécessite de lancer Camix avant de lancer le test
public class FelixTestQuitter {

	/**
	 * Vue Chat nécessaire au test
	 */
	private static VueChat vueChat;

	/**
	 * Mock d'un contrôleur nécessaire aux tests.
	 */
	private ControleurFelix controleurMock;

	/**
	 * La fenêtre de la vue chat
	 */
	private static JFrameOperator fenetreChat;

	/**
	 * Les champs texte pour le message et le chat.
	 */
	private static JTextFieldOperator message;
	private static JTextPaneOperator chatMessage;

	/**
	 * Fixe les propriétés de Felix pour les tests. Crée et affiche la vue
	 * nécessaire aux tests.
	 *
	 * @throws Exception toute exception.
	 *
	 * @see org.netbeans.jemmy.JemmyProperties
	 *
	 */
	@Before
	public void setUp() {
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

		// Création d'un mock de contrôleur.
		this.controleurMock = EasyMock.createMock(ControleurFelix.class);
		Assert.assertNotNull(this.controleurMock);

		// Création de la vue nécessaire aux tests.
		// La vue s'appuie sur le mock de contrôleur.
		this.vueChat = new VueChat(this.controleurMock);
		Assert.assertNotNull(this.vueChat);

		// Affichage de la vue et récupération de cette vue.
		this.vueChat.affiche();
		this.recuperationVue();
	}

	/**
	 * Fermeture de la vue chat finale.
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception {
		// Pour avoir le temps d'observer les manipulations sur la vue (objectif
		// pédagogique),
		// ici : 2 secondes.
		final Long timeout = Long.valueOf(2000);
		Thread.sleep(timeout);

		if (vueChat != null) {
			vueChat.ferme();
		}
	}

	/**
	 * Récupération de la vue chat.
	 *
	 * <p>
	 * Cette méthode concerne la récupération de la fenêtre, avec titre adéquat, et
	 * des widgets attendus pour cette vue.
	 * </p>
	 */
	private void recuperationVue() {
		// Récupération de la fenêtre de la vue de connexion (par nom)
		fenetreChat = new JFrameOperator(new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CHAT_TITRE")));
		Assert.assertNotNull("La fenêtre de la vue chat n'est pas accessible.", fenetreChat);

		// Récupération du champ de message (par nom)
		message = new JTextFieldOperator(fenetreChat,
				new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CHAT_MESSAGE")));
		Assert.assertNotNull("Le champ de saisie de message n'est pas accessible.", message);

		// Récupération du chat (par nom)
		chatMessage = new JTextPaneOperator(fenetreChat,
				new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CHAT_CHAT")));
		Assert.assertNotNull("Le champ de chat n'est pas accessible.", chatMessage);
	}

	/**
	 * Test du message de sortie du chat après saisie de /q (Connexion Possible)
	 * 
	 * @throws IOException
	 *
	 */
	@Test
	public void testConnexionPossible() throws InterruptedException, IOException {
		/*
		 * Données de test. (Les valeurs permettant la connexion à Camix sont les
		 * valeurs par défauts du chat)
		 */
		long timeout = Long.parseLong(Felix.CONFIGURATION.getString("CONNEXION_TIMEOUT"));

		Connexion connexion = new Connexion("127.0.0.1", 12345);

		EasyMock.expect(controleurMock.donneConnexion()).andReturn(connexion).times(1);
		EasyMock.replay(controleurMock);

		Thread.sleep(timeout);

		this.message.enterText("/q");

		Thread.sleep(timeout);

		// test de la valeur du message après avoir quitté le chat
		try {
			this.chatMessage.waitText("* Sortie du chat.");
		} catch (TimeoutExpiredException e) {
			Assert.fail("Informations attendus incorrectes");
		}
		Thread.sleep(timeout);

	}
}
