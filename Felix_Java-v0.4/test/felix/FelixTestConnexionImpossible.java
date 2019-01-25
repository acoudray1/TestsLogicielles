package felix;

import felix.controleur.ControleurFelix;
import felix.vue.Fenetre;
import felix.vue.VueConnexion;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.*;
import org.netbeans.jemmy.util.NameComponentChooser;

import javax.swing.*;
import java.awt.*;

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
public class FelixTestConnexionImpossible {

    /**
     * Vue Connexion nécessaire au test
     */
    private VueConnexion vueConnexion;

    private static ClassReference application;

    private static String[] parameter;

    /**
     * La fenêtre de la vue.
     */
    private JFrameOperator fenetre;

    /**
     * Les champs texte pour l'adresse IP, le port, message.
     */
    private JTextFieldOperator texteIP, textePort, texteMessage;

    /**
     * Le bouton connecter de la vue.
     */
    private JButtonOperator boutonConnecter;

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
    @Before
    public void setUp() throws Exception
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
     * Fermeture de la vue connexion.
     *
     * <p>Code exécuté après les tests.</p>
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

        if (this.vueConnexion != null) {
            this.vueConnexion.ferme();
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
        // Index pour la récupération des widgets.
        Integer index = 0;

        // Récupération de la fenêtre de la vue de connexion.
        this.fenetre = new JFrameOperator(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE"));
        Assert.assertNotNull("La fenêtre de la vue connexion n'est pas accessible.", this.fenetre);

        // Récupération du champ de saisie de l'adresse IP
        this.texteIP = new JTextFieldOperator(this.fenetre, index++);
        Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", this.texteIP);

        // Récupération du champ de saisie du port
        this.textePort = new JTextFieldOperator(this.fenetre, index++);
        Assert.assertNotNull("Le champ de saisie du port n'est pas accessible.", this.textePort);

        // Récupération du champ de saisie du message
        this.texteMessage = new JTextFieldOperator(this.fenetre, index++);
        Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", this.texteIP);

        // Ré-initialisation de l'index pour la récupération des boutons.
        index = 0;

        // Récupération du bouton d'ajout d'un produit à la vente.
        this.boutonConnecter = new JButtonOperator(this.fenetre, index++);
        Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", this.boutonConnecter);

    }

    /**
     * Test du MESSAGE_CONNEXION avant et après appui sur le botuon connecter (Connexion Impossible)
     *
     * <p></p>
     *
     * <p></p>
     */
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

        // Premier test de la valeur du message au départ
        try {
            this.texteMessage.waitText(premierMessageAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Informations attendus incorrectes");
        }

        boutonConnecter.clickMouse();

        // Deuxieme test de la valeur du message après erreur de connexion
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
