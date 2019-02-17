package camix.service;

import static org.easymock.EasyMock.partialMockBuilder;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class CanalChatTestEx1EasyMock {

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

	/**
	 * Test l'ajout d'un nouveau client dans le canal de discussion
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.estPresent
	 * @methods CanalChat.donneNombreCLients
	 */
	@Test
	public void ajoutClientTest_clientNonPresent_v1() {
		// Initialisation du chat
		final CanalChat canal = new CanalChat("mon_canal");

		final String id = "1";

		final int nbreClientAttendu = 1;

		// On donne le nbre de fois que doit être exécuté
		EasyMock.expect(clientMock.donneId()).andReturn(id).times(3);

		// Chargement du mock du client
		EasyMock.replay(this.clientMock);

		canal.ajouteClient(clientMock);

		Assert.assertTrue("Client présent", canal.estPresent(clientMock));

		Assert.assertEquals("Nombre de clients", (long) nbreClientAttendu, (long) canal.donneNombreClients());

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
	public void ajoutClientTest_clientPresent_v1() {
		final CanalChat canal = new CanalChat("mon_canal");

		final String id = "1";

		final int nbreClientAttendu = 1;

		EasyMock.expect(clientMock.donneId()).andReturn(id).times(4);

		// Chargement du mock du client
		EasyMock.replay(this.clientMock);

		canal.ajouteClient(clientMock);

		canal.ajouteClient(clientMock);

		Assert.assertTrue("Client présent", canal.estPresent(clientMock));

		Assert.assertEquals("Nombre de clients", (long) nbreClientAttendu, (long) canal.donneNombreClients());

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
	@Test
	public void ajoutClientTest_clientNonPresent_v2() {
		final CanalChat canal = new CanalChat("mon_canal");

		final String id = "1";

		final int nbreClientAttendu = 1;

		/* Pour l'accès à la liste de clients (privé) */
		String clients = "clients";
		Field attribut;

		EasyMock.expect(clientMock.donneId()).andReturn(id).times(2);

		// Chargement du mock du client
		EasyMock.replay(this.clientMock);

		try {
			canal.ajouteClient(clientMock);
			attribut = CanalChat.class.getDeclaredField(clients);
			attribut.setAccessible(true);
			Hashtable<String, ClientChat> canalActuel = (Hashtable<String, ClientChat>) attribut.get(canal);

			Assert.assertEquals("Nombre de clients", nbreClientAttendu, canalActuel.size());
			Assert.assertTrue("Contient bien le client", canalActuel.contains(clientMock));
			Assert.assertTrue("Contient un id", canalActuel.containsKey(id));

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
	 * Utilisation de l'introspection pour satisfaire les effets de bords
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.estPresent
	 * @methods CanalChat.donneNombreCLients
	 */
	@Test
	public void ajoutClientTest_clientPresent_v2() {
		final CanalChat canal = new CanalChat("mon_canal");

		final String id = "1";

		final int nbreClientAttendu = 1;

		/* Pour l'accès à la liste de clients (privé) */
		String clients = "clients";
		Field attribut;

		EasyMock.expect(clientMock.donneId()).andReturn(id).times(3);

		// Chargement du mock du client
		EasyMock.replay(this.clientMock);

		try {
			canal.ajouteClient(clientMock);
			canal.ajouteClient(clientMock);
			attribut = CanalChat.class.getDeclaredField(clients);
			attribut.setAccessible(true);
			Hashtable<String, ClientChat> canalActuel = (Hashtable<String, ClientChat>) attribut.get(canal);

			Assert.assertEquals("Nombre de clients", nbreClientAttendu, canalActuel.size());
			Assert.assertTrue("Contient bien le client", canalActuel.contains(clientMock));
			Assert.assertTrue("Contient un id", canalActuel.containsKey(id));

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
	 * Utilisation de l'introspection pour satisfaire les préconditions
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.put
	 */
	@Test
	public void ajoutClientTest_clientPresent_v2_1() {

		final CanalChat canal = new CanalChat("mon_canal");

		final String id = "1";

		final int nbreClientAttendu = 1;

		/* Pour l'accès à la liste de clients (privé) */
		String clients = "clients";
		Field attribut;

		EasyMock.expect(clientMock.donneId()).andReturn(id).times(1);

		// Chargement du mock du client
		EasyMock.replay(this.clientMock);

		try {
			attribut = CanalChat.class.getDeclaredField(clients);
			attribut.setAccessible(true);
			Hashtable<String, ClientChat> canalActuel = (Hashtable<String, ClientChat>) attribut.get(canal);

			// Ajout du client manuellement
			canalActuel.put(id, clientMock);

			// Ajout d'un client
			canal.ajouteClient(clientMock);

			Assert.assertEquals("Nombre de clients", nbreClientAttendu, canalActuel.size());
			Assert.assertTrue("Contient bien le client", canalActuel.contains(clientMock));
			Assert.assertTrue("Contient un id", canalActuel.containsKey(id));

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
	 * Utilisation de l'introspection pour satisfaire les préconditions
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.put
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void ajoutClientTest_clientNonPresent_v3() {

		// Crée un mock partiel du canal
		CanalChat canalMock = EasyMock.partialMockBuilder(CanalChat.class).addMockedMethod("estPresent")
				.withConstructor("Chat").createMock();

		String id = "1";

		EasyMock.expect(clientMock.donneId()).andReturn(id).times(1);

		EasyMock.expect(canalMock.estPresent(clientMock)).andReturn(false);

		// Chargement du mock du client et du canal
		EasyMock.replay(this.clientMock);

		EasyMock.replay(canalMock);

		// Ajout du client
		canalMock.ajouteClient(clientMock);

		String attributConcerne = "clients";
		Field attribut;

		try {
			attribut = CanalChat.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);

			// Test de l'ajout du client
			Assert.assertTrue(((Hashtable) attribut.get(canalMock)).containsKey(id));

			Assert.assertEquals(1, ((Hashtable) attribut.get(canalMock)).size());

			// Vérification des sollicitations faites aux mocks.
			EasyMock.verify(this.clientMock);

			EasyMock.verify(canalMock);

		} catch (SecurityException e) {
			Assert.fail("Problème de sécurité sur la réflexion.");
		} catch (NoSuchFieldException e) {
			Assert.fail("Attribut concerné non existant.");
		} catch (IllegalArgumentException e) {
			Assert.fail("Arguments de la méthode de réflexion invalides.");
		} catch (IllegalAccessException e) {
			Assert.fail("Accès illégal à l'attribut concerné.");
		}

	}

	/**
	 * Test l'ajout d'un nouveau client dans le canal de discussion
	 *
	 * Utilisation de l'introspection pour satisfaire les préconditions
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.put
	 */
	@Test
	public void ajoutClientTest_clientPresent_v3() {

		// Crée un mock partiel du canal
		CanalChat canalMock = partialMockBuilder(CanalChat.class).withConstructor("mon_canal")
				.addMockedMethod("estPresent").createMock();

		final int nbreClientAttendu = 0;

		EasyMock.expect(canalMock.estPresent(clientMock)).andReturn(true).times(1);

		// Chargement du mock du client et du canal
		EasyMock.replay(this.clientMock);
		EasyMock.replay(canalMock);

		// Ajout du client
		canalMock.ajouteClient(clientMock);

		// Test de l'ajout du client
		Assert.assertEquals("Nombre de clients", nbreClientAttendu, (int) canalMock.donneNombreClients());

		// Vérification des sollicitations faites aux mocks.
		EasyMock.verify(this.clientMock);
		EasyMock.verify(canalMock);
	}

}
