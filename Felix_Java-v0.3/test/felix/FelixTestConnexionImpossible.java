package felix;

import felix.controleur.ControleurFelix;
import felix.vue.Fenetre;
import felix.vue.VueConnexion;
import org.easymock.EasyMock;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.*;
import org.netbeans.jemmy.util.NameComponentChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Classe de tests unitaires JUnit 4 de la classe VueConnexion.
 *
 * @version 1.0
 * @author Axel COUDRAY
 *
 * @see felix.vue.VueConnexion
 * @see org.easymock.EasyMock
 * @see org.netbeans.jemmy.JemmyProperties
 *
 */
public class FelixTestConnexionImpossible {

    /**
     * Vue Connexion nécessaire au test
     */
    private static VueConnexion vueConnexion;

    /**
     * Mock d'un contrôleur nécessaire aux tests.
     */
    private ControleurFelix controleurMock;


    /**
     * La fenêtre de la vue.
     */
    private static JFrameOperator fenetre;

    /**
     * Les champs texte pour l'adresse IP, le port, message.
     */
    private static JTextFieldOperator texteIP;
    private static JTextFieldOperator textePort;
    private static JTextFieldOperator texteMessage;

    /**
     * Le bouton connecter de la vue.
     */
    private static JButtonOperator boutonConnecter;

    /**
     * Fixe les propriétés de Felix pour les tests.
     * Crée et affiche la vue nécessaire aux tests.
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
        this.vueConnexion = new VueConnexion(this.controleurMock);
        Assert.assertNotNull(this.vueConnexion);

        // Affichage de la vue et récupération de cette vue.
        this.vueConnexion.affiche();
        this.recuperationVue();
    }

    /**
     * Fermeture de la vue connexion finale.
     *
     * @throws Exception toute exception.
     *
     */
    @After
    public void tearDown() throws Exception
    {
        // Pour avoir le temps d'observer les manipulations sur la vue (objectif pédagogique),
        // ici : 2 secondes.
        final Long timeout = Long.valueOf(2000);
        Thread.sleep(timeout);

        if (vueConnexion != null) {
            vueConnexion.ferme();
        }
    }

    /**
     * Récupération de la vue caisse.
     *
     * <p>Cette méthode concerne la récupération de la fenêtre, avec titre adéquat,
     * et des widgets attendus pour cette vue.</p>
     */
    private void recuperationVue()
    {
        // Récupération de la fenêtre de la vue de connexion (par nom)
        fenetre = new JFrameOperator(new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE")));
        Assert.assertNotNull("La fenêtre de la vue connexion n'est pas accessible.", fenetre);

        // Récupération du champ de saisie de l'adresse IP (par nom)
        texteIP = new JTextFieldOperator(fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_IP")));
        Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", texteIP);

        // Récupération du champ de saisie du port (par nom)
        textePort = new JTextFieldOperator(fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_PORT")));
        Assert.assertNotNull("Le champ de saisie du port n'est pas accessible.", textePort);

        // Récupération du message (par nom)
        texteMessage = new JTextFieldOperator(fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE")));
        Assert.assertNotNull("Le champ du message n'est pas accessible.", texteIP);

        // Récupération du bouton d'ajout d'un produit à la vente (par nom)
        boutonConnecter = new JButtonOperator(fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION")));
        Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", boutonConnecter);
    }

    /**
     * Test l'initialisation des différents champs de la vue.
     *
     */
    @Test
    public void testInitialiseVue()
    {
        /*
         * Données de test
         */
        final String fenetreAttendu = Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE");
        final String texteIPAttendu = Felix.CONFIGURATION.getString("ADRESSE_CHAT");
        final String textePortAttendu = Felix.CONFIGURATION.getString("PORT_CHAT");
        final String texteMessageAttendu = Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");
        final String boutonConnecterAttendu = "Connexion";

        /*
         * Execution du test
         */
        try {
            // Récupération des libellés de la vue
            final String fenetreActuelle = this.fenetre.getTitle();
            final String texteIPActuel = this.texteIP.getText();
            final String textePortActuel = this.textePort.getText();
            final String texteMessageActuel = this.texteMessage.getText();
            final String boutonConnecterActuel = this.boutonConnecter.getText();

            // Assertions
            Assert.assertEquals("Titre de la fenetre invalide", fenetreAttendu, fenetreActuelle);
            Assert.assertEquals("Texte IP par défaut invalide.", texteIPAttendu, texteIPActuel);
            Assert.assertEquals("Texte port par défaut invalide.", textePortAttendu, textePortActuel);
            Assert.assertEquals("Texte message par défaut invalide.", texteMessageAttendu, texteMessageActuel);
            Assert.assertEquals("Texte bouton connexion par défaut invalide.", boutonConnecterAttendu, boutonConnecterActuel);
        }catch (Exception e) {
            Assert.fail("Manipulation de la vue connexion invalide." + e.getMessage());
        }
    }

    @Test
    public void testConnexionImpossible() throws InterruptedException {
        // tests syntaxique @ip
        testConnexionImpossibleParametre("0123456789", "12345");
        testConnexionImpossibleParametre("265.300.256.700", "12345");
        testConnexionImpossibleParametre("aaa.bbb.256.700", "12345");
        // tests syntaxique @port
        testConnexionImpossibleParametre("127.0.0.1", "abc34");
        testConnexionImpossibleParametre("127.0.0.1", "123456");
        // tests sémantiques
        testConnexionImpossibleParametre("216.58.213.174", "12345");
        testConnexionImpossibleParametre("127.0.0.1", "80");
        testConnexionImpossibleParametre("192.168.254.254", "12345");
    }


    /**
     * Test du MESSAGE_CONNEXION avant et après appui sur le bouton connecter (Connexion Impossible) en fonction des parametres d'IP et de port
     *
     */
    public void testConnexionImpossibleParametre(String ip, String port) throws InterruptedException {
        /*
         * Données de test.
         */
        long timeout = Long.parseLong(Felix.CONFIGURATION.getString("CONNEXION_TIMEOUT"));
        textePort.clearText();
        texteIP.clearText();

        Thread.sleep(timeout);

        // insertion de nouvelles valeurs
        texteIP.typeText(ip);
        textePort.typeText(port);

        final String messageAttenduAvantErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                ip, port);
        final String messageAttenduApresErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"),
                ip, port);

        boutonConnecter.clickMouse();

        try {
            this.texteMessage.waitText(messageAttenduAvantErreur);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);

        // test de la valeur du message après erreur de connexion
        try {
            this.texteMessage.waitText(messageAttenduApresErreur);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);
    }
}
