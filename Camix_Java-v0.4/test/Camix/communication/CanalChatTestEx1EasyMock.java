package Camix.communication;

import camix.service.CanalChat;
import camix.service.ClientChat;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.Hashtable;

import static org.junit.Assert.fail;


@RunWith(EasyMockRunner.class)
public class CanalChatTestEx1EasyMock {

    @Mock
    private ClientChat clientMock;

    @Before
    public void setUp() throws Exception
    {
        /* rien faire */
    }

    @After
    public void tearDown() throws Exception
    {
        /* rien faire */
    }

    /**
     * Test l'ajout d'un nouveau client dans le canal de discussion
     *
     * @methods ClientChat.donneId
     * @methods CanalChat.ajouteClient
     * @methods CanalChat.estPresent
     * @methods CanalChat.donneNombreCLients
     */
    @Test
    public void ajoutClientTest_clientNonPresent_v1(){
        final CanalChat canal = new CanalChat("mon_canal");

        final String id ="1";

        final int nbreClientAttendu = 1;

        EasyMock.expect(
                clientMock.donneId()
        ).andReturn(
              id
        ).times(3);

        // Chargement du mock du client
        EasyMock.replay(this.clientMock);

        canal.ajouteClient(clientMock);

        Assert.assertTrue(
            "Client présent",
            canal.estPresent(clientMock)
        );

        Assert.assertEquals(
            "Nombre de clients",
                (long) nbreClientAttendu,
                (long) canal.donneNombreClients()
        );

        // Vérification des sollicitations faites au mock.
        EasyMock.verify(this.clientMock);

    }

    /**
     * Test l'ajout d'un client existant dans le canal de discussion
     *
     * @methods ClientChat.donneId
     * @methods CanalChat.ajouteClient
     * @methods CanalChat.estPresent
     * @methods CanalChat.donneNombreCLients
     */
    @Test
    public void ajoutClientTest_clientPresent_v1(){
        final CanalChat canal = new CanalChat("mon_canal");

        final String id = "1";

        final int nbreClientAttendu = 1;

        EasyMock.expect(
                clientMock.donneId()
        ).andReturn(
                id
        ).times(4);

        // Chargement du mock du client
        EasyMock.replay(this.clientMock);

        canal.ajouteClient(clientMock);

        canal.ajouteClient(clientMock);

        Assert.assertTrue(
                "Client présent",
                canal.estPresent(clientMock)
        );

        Assert.assertEquals(
                "Nombre de clients",
                (long) nbreClientAttendu,
                (long) canal.donneNombreClients()
        );

        // Vérification des sollicitations faites au mock.
        EasyMock.verify(this.clientMock);

    }

    /**
     * Test l'ajout d'un nouveau client dans le canal de discussion
     *
     * @methods ClientChat.donneId
     * @methods CanalChat.ajouteClient
     * @methods CanalChat.estPresent
     * @methods CanalChat.donneNombreCLients
     */
    public void ajoutClientTest_clientNonPresent_v2(){
        final CanalChat canal = new CanalChat("mon_canal");

        final String id ="1";

        final int nbreClientAttendu = 1;

        /* Pour l'accès à la liste de clients (privé) */
        String clients = "clients";
        Field attribut;

        EasyMock.expect(
                clientMock.donneId()
        ).andReturn(
                id
        ).times(2);

        // Chargement du mock du client
        EasyMock.replay(this.clientMock);

        canal.ajouteClient(clientMock);

        // TODO: A compléter introspection
        try {
            attribut = ClientChat.class.getDeclaredField(clients);
            attribut.setAccessible(true);
            Hashtable<String, ClientChat> clientActuel = (Hashtable<String, ClientChat>) attribut.get(this.clientMock);

            Assert.assertEquals("Nombre de clients", clientActuel.size(), 1);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        // Vérification des sollicitations faites au mock.
        EasyMock.verify(this.clientMock);
    }

    /**
     * Test l'ajout d'un nouveau client dans le canal de discussion
     *
     * @methods ClientChat.donneId
     * @methods CanalChat.ajouteClient
     * @methods CanalChat.estPresent
     * @methods CanalChat.donneNombreCLients
     */
    public void ajoutClientTest_clientPresent_v2(){
        final CanalChat canal = new CanalChat("mon_canal");

        final String id ="1";

        final int nbreClientAttendu = 1;

        EasyMock.expect(
                clientMock.donneId()
        ).andReturn(
                id
        ).times(3);

        // Chargement du mock du client
        EasyMock.replay(this.clientMock);

        canal.ajouteClient(clientMock);

        canal.ajouteClient(clientMock);

        // TODO: A compléter introspection

        // Vérification des sollicitations faites au mock.
        EasyMock.verify(this.clientMock);
    }
}
