package felix;

import felix.controleur.ControleurFelix;
import felix.vue.VueChat;
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
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class FelixConnexionPossible {
    /**
     * Vue connexion à tester
     */
    private static VueConnexion vueConnexion;

    /**
     * Vue chat à tester
     */
    private static VueChat vueChat;

    /**
     * La fenetre de la vue
     */
    private static JFrameOperator fenetre;

    /**
     * La fenetre de la vue chat
     */
    private static JFrameOperator fenetreChat;

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
    private static JLabelOperator messageInfo;

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

        // Création de la vue chat nécessaire aux tests


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
        this.messageInfo = new JLabelOperator(this.fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_FIELD_MESSAGE")));
        Assert.assertNotNull("Le champ du message n'est pas accessible.", this.messageInfo);

        // Récupération du bouton d'ajout d'un produit à la vente (par nom)
        this.boutonConnexion = new JButtonOperator(this.fenetre, new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION")));
        Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", this.boutonConnexion);
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
        final String messageChargementAttendu = String.format(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                Felix.CONFIGURATION.getString("ADRESSE_CHAT"), Felix.CONFIGURATION.getString("PORT_CHAT"));
        this.messageInfo.setText(messageChargementAttendu);

        // Démarrage du test
        // Clique sur le bouton
        this.boutonConnexion.clickMouse();

        try {
            // Vérification du changement de libellé
            //Assert.assertEquals("Le resultat actuel et celui attendu sont différents", messageChargementAttendu, this.messageInfo.getText());
            this.messageInfo.waitText(messageChargementAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        Thread.sleep(timeout);

        // Récupération de la fenêtre de la vue de connexion (par nom)
        this.fenetreChat = new JFrameOperator(new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CHAT_TITRE")));
        Assert.assertNotNull("La fenêtre de la vue chat n'est pas accessible.", fenetreChat);
        Thread.sleep(timeout);
    }
}
