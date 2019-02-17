package camix.service;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import camix.communication.ProtocoleChat;

@RunWith(EasyMockRunner.class)
public class ServiceChatTestQuitter {

	// Crée un mock du client
	@Mock
	private ClientChat clientMock;

	@Before
	public void setUp() throws Exception {
		/* rien faire */
	}

	@After
	public void tearDown() throws Exception {
		/* rien faire */
	}

	@Test(timeout = 2000)
	public void quitterChatTest() throws IOException {

		// Variables de départ
		final ServiceChat service = new ServiceChat("mon_canal");
		String surnom = "un_surnom";
		final String messageSortie = String.format(ProtocoleChat.MESSAGE_INFORMATION_SORTIE_CHAT);
		final String message = String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, surnom);

		// Ce qui est attendu
		// Je voulais mocker l'envoie de message pour qu'il n'appelle pas la connexion
		// mais je ne sais pas comment faire pour que la méthode ne s'exécute pas
		clientMock.envoieMessage(messageSortie);
		EasyMock.expect(clientMock.donneSurnom()).andReturn(surnom).times(1);
		clientMock.envoieContacts(message);
		clientMock.fermeConnexion();

		// Chargement des mock
		EasyMock.replay(this.clientMock);

		// Utilisation des méthodes à tester
		service.quitterChat(this.clientMock);

		// Vérification des sollicitations faites aux mocks.
		EasyMock.verify(this.clientMock);
	}

}
