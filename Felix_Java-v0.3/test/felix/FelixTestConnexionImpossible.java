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
 * <p>Utilisation d'un mock de stock (construit avec EasyMock).</p>
 * <p>Utilisation d'un mock de contrôleur (construit avec EasyMock) pour le MVC.</p>
 * <p>Utilisation de Jemmy 2 pour la manipulation de l'IHM.</p>
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see felix.vue.VueConnexion
 * @see org.easymock.EasyMock
 * @see org.netbeans.jemmy.JemmyProperties
 *
 */
@RunWith(Parameterized.class)
public class FelixTestConnexionImpossible {

    /**
     * Vue Connexion nécessaire au test
     */
    private static VueConnexion vueConnexion;

    private static ClassReference application;

    private static String[] parameter;

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
     * Attributs auxquels on va donner des valeurs
     */
    private String ip, port;

    /**
     * Le bouton connecter de la vue.
     */
    private static JButtonOperator boutonConnecter;

    /**
     * Constructor
     * @param ip
     * @param port
     */
    public FelixTestConnexionImpossible(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Création du jeu de test.
     *
     * @return l'ensemble des données de test.
     */
    @Parameterized.Parameters(name = "dt[{index}] : {0}, {1}")
    public static Collection<Object[]> jt()
    {
        final Object[][] data = new Object[][] {
                // tests syntaxique @ip
                {"0123456789", "12345"},
                {"265.300.256.700", "12345"},
                {"aaa.bbb.256.700", "12345"},
                // tests syntaxique @port
                {"127.0.0.1", "abc34"},
                {"127.0.0.1", "123456"},
                // tests sémantiques
                {"216.58.213.174", "12345"},
                {"127.0.0.1", "80"},
                {"192.168.254.254", "12345"}
        };
        return Arrays.asList(data);
    }

    /**
     * Fixe les propriétés de Jemmy pour les tests.
     * Crée un mock de contrôleur et un mock de stock.
     * Crée et affiche la vue nécessaire aux tests.
     *
     * <p>Code exécuté avant les tests.</p>
     *
     * @throws Exception toute exception.
     *
     * @see org.netbeans.jemmy.JemmyProperties
     *
     */
    @BeforeClass
    public static void setUp() throws Exception
    {
        final Integer timeout = 3000;
        JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

        FelixTestConnexionImpossible.application = new ClassReference("felix.Felix");
        FelixTestConnexionImpossible.parameter = new String[1];
        FelixTestConnexionImpossible.parameter[0] = "";

        FelixTestConnexionImpossible.application.startApplication(FelixTestConnexionImpossible.parameter);
        recuperationVue();
    }

    /**
     * Fermeture de la vue connexion finale.
     *
     * <p>Code exécuté après les tests.</p>
     *
     * @throws Exception toute exception.
     *
     */
    @AfterClass
    public static void tearDownFinal() throws Exception
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
     * Fermeture de la vue connexion.
     *
     * <p>Code exécuté après les tests.</p>
     *
     * @throws Exception toute exception.
     *
     */
    /* //TODO: Gérer la fermeture de la fenêtre chat si test fail
    @After
    public static void tearDown() throws Exception
    {
        // Pour avoir le temps d'observer les manipulations sur la vue (objectif pédagogique),
        // ici : 2 secondes.
        final Long timeout = Long.valueOf(2000);
        Thread.sleep(timeout);

        if (vueConnexion != null) {
            vueConnexion.ferme();
        }
    }*/

    /**
     * Récupération de la vue caisse.
     *
     * <p>Cette méthode concerne la récupération de la fenêtre, avec titre adéquat,
     * et des widgets attendus pour cette vue.</p>
     */
    private static void recuperationVue()
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

        // Récupération du champ de saisie du message (par nom)
        texteMessage = new JTextFieldOperator(fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE")));
        Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", texteIP);

        // Récupération du bouton d'ajout d'un produit à la vente (par nom)
        boutonConnecter = new JButtonOperator(fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION")));
        Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", boutonConnecter);
    }

    /**
     * Test du MESSAGE_CONNEXION avant et après appui sur le botuon connecter (Connexion Impossible)
     *
     * <p></p>
     *
     * <p></p>
     */
    // TODO: Tester le message au début du test
    // TODO: Faire passer les tests (le message ne passe pas à "Connexion impossible")
    @Test
    public void testConnexionImpossible() throws InterruptedException {
        /*
         * Données de test.
         */
        final String premierMessageAttendu = Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");
        final String MessageAttenduAvantErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                Felix.CONFIGURATION.getString("ADRESSE_CHAT"), Felix.CONFIGURATION.getString("PORT_CHAT"));
        final String MessageAttenduApresErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"),
                Felix.CONFIGURATION.getString("ADRESSE_CHAT"), Felix.CONFIGURATION.getString("PORT_CHAT"));

        long timeout = Long.parseLong(Felix.CONFIGURATION.getString("CONNEXION_TIMEOUT"));

        // 1 : test de la valeur du message au départ
        try {
            this.texteMessage.waitText(premierMessageAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        // 2 : insertion de nouvelles valeurs
        /*texteIP.clearText();
        texteIP.typeText(ip);
        textePort.clearText();
        textePort.typeText(port);

        final String MessageAttenduAvantErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                ip, port);
        final String MessageAttenduApresErreur = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"),
                ip, port);*/

        boutonConnecter.clickMouse();

        // 3 : test de la valeur du message après erreur de connexion
        try {
            this.texteMessage.waitText(MessageAttenduAvantErreur);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);

        try {
            this.texteMessage.waitText(MessageAttenduApresErreur);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);
    }
}
