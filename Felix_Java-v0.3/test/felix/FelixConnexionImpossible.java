package felix;

import felix.controleur.ControleurFelix;
import felix.vue.VueConnexion;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

/**
 * Classe de tests unitaires JUnit 4 de la classe VueConnexion.
 *
 * @version 2.0
 * @author Axel COUDRAY
 *
 */
public class FelixConnexionImpossible {
    /**
     * Vue connexion à tester
     */
    private static VueConnexion vueConnexion;

    /**
     * La fenetre de la vue
     */
    private static JFrameOperator fenetre;

    /**
     * Le bouton de la vue
     */
    private static JButtonOperator boutonConnexion;

    /**
     * Le champ de texte de l'@ip
     */
    private static JTextFieldOperator texteIP;

    /**
     * Le champ de texte du port
     */
    private static JTextFieldOperator textePort;

    /**
     * Le champ d'information de connexion de la vue
     */
    private static JTextFieldOperator messageInfo;

    /**
     * Mock d'un controlleur pour les tests de la vue
     */
    private static ControleurFelix controleurMock;

    /**
     * Fixe les propriétés dela vue et de Jemmy pour les tests
     *
     */
    @Before
    public void setUp() {
        // Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
        // ici : 3s pour l'affichage d'une frame.
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
     * Fermeture de la vue de test
     *
     */
    @After
    public void tearDown() throws InterruptedException {
        // Pour avoir le temps d'observer les manipulations sur la vue (objectif pédagogique),
        // ici : 2 secondes.
        final Long timeout = Long.valueOf(2000);
        Thread.sleep(timeout);

        if (this.vueConnexion != null) {
            this.vueConnexion.ferme();
        }
    }

    /**
     * Recupération de la vue et de ses éléments dans la fenetre
     *
     */
    public void recuperationVue() {
        // Récupération de la fenêtre de la vue de connexion (par nom)
        this.fenetre = new JFrameOperator(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE"));
        Assert.assertNotNull("La fenêtre de la vue connexion n'est pas accessible.", this.fenetre);

        // Récupération du champ de saisie de l'adresse IP (par nom)
        this.texteIP = new JTextFieldOperator(this.fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_IP")));
        Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", this.texteIP);

        // Récupération du champ de saisie du port (par nom)
        this.textePort = new JTextFieldOperator(this.fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_PORT")));
        Assert.assertNotNull("Le champ de saisie du port n'est pas accessible.", this.textePort);

        // Récupération du message (par nom)
        this.messageInfo = new JTextFieldOperator(this.fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE")));
        Assert.assertNotNull("Le champ du message n'est pas accessible.", this.messageInfo);

        // Récupération du bouton d'ajout d'un produit à la vente (par nom)
        this.boutonConnexion = new JButtonOperator(this.fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION")));
        Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", this.boutonConnexion);
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
            final String texteMessageActuel = this.messageInfo.getText();
            final String boutonConnecterActuel = this.boutonConnexion.getText();

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

    /**
     * Lance des tests de connexion avec des ports et @ips différentes
     *
     */
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
     * Methode de test de connexion impossible prenant en parametre les valeurs à renseigner
     * dans les champs de port et d'@ip de la vue
     *
     */
    public void testConnexionImpossibleParametre(String ip, String port) throws InterruptedException {

        //Initialisation et préparation des données
        long timeout = Long.valueOf(2000);
        final String messageAttenduAvantErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                ip, port);
        final String messageAttenduApresErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"),
                ip, port);

        // Démarrage du test
        try {
            // Nettoyage de la vue
            this.textePort.clearText();
            this.texteIP.clearText();

            // Insertion des données
            this.texteIP.setText(ip);
            this.textePort.setText(port);

            // Clique sur le bouton
            this.boutonConnexion.clickMouse();

            // Vérification du changement de libellé
            this.messageInfo.waitText(messageAttenduAvantErreur);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);

        try {
            this.messageInfo.waitText(messageAttenduApresErreur);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);
    }

}
