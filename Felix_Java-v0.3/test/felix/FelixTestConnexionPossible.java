package felix;

import felix.controleur.ControleurFelix;
import felix.vue.Fenetre;
import felix.vue.VueChat;
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

public class FelixTestConnexionPossible {
    /**
     * Vue Connexion nécessaire au test
     */
    private static VueConnexion vueConnexion;

    /**
     * Mock d'un contrôleur nécessaire aux tests.
     */
    private ControleurFelix controleurMock;


    /**
     * La fenêtre de la vue connexion
     */
    private static JFrameOperator fenetreConnexion;

    /**
     * La fenetre de la vue chat
     */
    private static JFrameOperator fenetreChat;

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
        fenetreConnexion = new JFrameOperator(new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE")));
        Assert.assertNotNull("La fenêtre de la vue connexion n'est pas accessible.", fenetreConnexion);

        // Récupération du champ de saisie de l'adresse IP (par nom)
        texteIP = new JTextFieldOperator(fenetreConnexion, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_IP")));
        Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", texteIP);

        // Récupération du champ de saisie du port (par nom)
        textePort = new JTextFieldOperator(fenetreConnexion, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_SAISIE_PORT")));
        Assert.assertNotNull("Le champ de saisie du port n'est pas accessible.", textePort);

        // Récupération du message (par nom)
        texteMessage = new JTextFieldOperator(fenetreConnexion, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE")));
        Assert.assertNotNull("Le champ du message n'est pas accessible.", texteIP);

        // Récupération du bouton d'ajout d'un produit à la vente (par nom)
        boutonConnecter = new JButtonOperator(fenetreConnexion, new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION")));
        Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", boutonConnecter);
    }

    /**
     * Test du MESSAGE_CONNEXION avant et après appui sur le bouton connecter (Connexion Possible)
     *
     */
    @Test
    public void testConnexionPossible() throws InterruptedException {
        /*
         * Données de test.
         * (Les valeurs permettant la connexion à Camix sont les valeurs par défauts du chat)
         */
        long timeout = Long.parseLong(Felix.CONFIGURATION.getString("CONNEXION_TIMEOUT"));

        Thread.sleep(timeout);

        final String messageChargementAttendu = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                Felix.CONFIGURATION.getString("ADRESSE_CHAT"), Felix.CONFIGURATION.getString("PORT_CHAT"));

        boutonConnecter.clickMouse();

        try {
            this.texteMessage.waitText(messageChargementAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);

        // Récupération de la fenêtre de la vue de connexion (par nom)
        fenetreChat = new JFrameOperator(new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CHAT_TITRE")));
        Assert.assertNotNull("La fenêtre de la vue chat n'est pas accessible.", fenetreChat);

        Thread.sleep(timeout);
    }
}
