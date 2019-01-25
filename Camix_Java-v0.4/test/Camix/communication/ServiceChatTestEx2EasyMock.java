package Camix.communication;

import camix.communication.ProtocoleChat;
import camix.service.CanalChat;
import camix.service.ClientChat;
import camix.service.ServiceChat;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.easymock.EasyMock.partialMockBuilder;

@RunWith(EasyMockRunner.class)
public class ServiceChatTestEx2EasyMock {

    // Crée un mock du client
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
     *
     *
     */
    @Test(timeout = 2000)
    public void informeDepartClientTest() throws IOException {

        // Variables de départ
        final ServiceChat service = new ServiceChat("mon_canal");

        String surnom = "un_surnom";
        String message = String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, surnom);

        // Ce qui est attendu
        EasyMock.expect(
            clientMock.donneSurnom()
        ).andReturn(
            surnom
        ).times(1);
        clientMock.envoieContacts(message);

        // Chargement des mock
        EasyMock.replay(this.clientMock);


        // Utilisation des méthodes à tester
        service.informeDepartClient(this.clientMock);

        // Assert pour effets de bords


        // Vérification des sollicitations faites aux mocks.
        EasyMock.verify(this.clientMock);
    }
}