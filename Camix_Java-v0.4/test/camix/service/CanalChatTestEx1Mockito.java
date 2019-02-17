package camix.service;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import camix.service.CanalChat;
import camix.service.ClientChat;

@RunWith(MockitoJUnitRunner.class)
public class CanalChatTestEx1Mockito {

	@Mock
	private ClientChat mockClient;

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
		CanalChat canal = new CanalChat("Chat");

		String id = "1";

		Mockito.when(mockClient.donneId()).thenReturn(id);

		canal.ajouteClient(mockClient);

		Assert.assertEquals(1, (int) canal.donneNombreClients());

		Assert.assertTrue(canal.estPresent(this.mockClient));

		Mockito.verify(this.mockClient, Mockito.times(3)).donneId();

		Mockito.verifyNoMoreInteractions(this.mockClient);

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
		CanalChat canal = new CanalChat("Chat");

		String id = "1";

		Mockito.when(mockClient.donneId()).thenReturn(id);

		canal.ajouteClient(mockClient);

		canal.ajouteClient(mockClient);

		Assert.assertEquals(1, (int) canal.donneNombreClients());

		Assert.assertTrue(canal.estPresent(this.mockClient));

		Mockito.verify(this.mockClient, Mockito.times(4)).donneId();

		Mockito.verifyNoMoreInteractions(this.mockClient);
	}

	/**
	 * Test l'ajout d'un nouveau client dans le canal de discussion
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.estPresent
	 * @methods CanalChat.donneNombreCLients
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void ajoutClientTest_clientNonPresent_v2() {
		CanalChat canal = new CanalChat("Chat");

		String id = "1";

		Mockito.when(mockClient.donneId()).thenReturn(id);

		canal.ajouteClient(mockClient);

		String attributConcerne = "clients";
		Field attribut;

		try {
			attribut = CanalChat.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);

			Assert.assertTrue(((Hashtable) attribut.get(canal)).containsKey(id));

			Assert.assertEquals(1, ((Hashtable) attribut.get(canal)).size());

			Mockito.verify(this.mockClient, Mockito.times(2)).donneId();

			Mockito.verifyNoMoreInteractions(this.mockClient);

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
	 * Utilisation de l'introspection pour satisfaire les effets de bords
	 *
	 * @methods ClientChat.donneId
	 * @methods CanalChat.ajouteClient
	 * @methods CanalChat.estPresent
	 * @methods CanalChat.donneNombreCLients
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void ajoutClientTest_clientPresent_v2() {
		CanalChat canal = new CanalChat("Chat");

		String id = "1";

		Mockito.when(mockClient.donneId()).thenReturn(id);

		canal.ajouteClient(mockClient);

		canal.ajouteClient(mockClient);

		String attributConcerne = "clients";
		Field attribut;

		try {
			attribut = CanalChat.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);

			Assert.assertTrue(((Hashtable) attribut.get(canal)).containsKey(id));

			Assert.assertEquals(1, ((Hashtable) attribut.get(canal)).size());

			Mockito.verify(this.mockClient, Mockito.times(3)).donneId();

			Mockito.verifyNoMoreInteractions(this.mockClient);

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void ajoutClientTest_clientPresent_v2_1() {
		CanalChat canal = new CanalChat("Chat");

		String id = "1";

		Mockito.when(mockClient.donneId()).thenReturn(id);

		String attributConcerne = "clients";
		Field attribut;

		try {
			attribut = CanalChat.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);

			((Hashtable) attribut.get(canal)).put(id, this.mockClient);

			canal.ajouteClient(mockClient);

			Assert.assertTrue(((Hashtable) attribut.get(canal)).containsKey(id));

			Assert.assertEquals(1, ((Hashtable) attribut.get(canal)).size());

			Mockito.verify(this.mockClient, Mockito.times(1)).donneId();

			Mockito.verifyNoMoreInteractions(this.mockClient);

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
	@SuppressWarnings("rawtypes")
	@Test
	public void ajoutClientTest_clientNonPresent_v3() {
		CanalChat canalMock = Mockito.spy(new CanalChat("Chat"));

		String id = "1";

		Mockito.when(mockClient.donneId()).thenReturn(id);

		Mockito.doReturn(false).when(canalMock).estPresent(mockClient);

		canalMock.ajouteClient(mockClient);

		String attributConcerne = "clients";
		Field attribut;

		try {
			attribut = CanalChat.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);

			Assert.assertTrue(((Hashtable) attribut.get(canalMock)).containsKey(id));

			Assert.assertEquals(1, ((Hashtable) attribut.get(canalMock)).size());

			Mockito.verify(this.mockClient, Mockito.times(1)).donneId();

			Mockito.verifyNoMoreInteractions(this.mockClient);

			Mockito.verify(canalMock, Mockito.times(1)).estPresent(mockClient);

			Mockito.verifyNoMoreInteractions(this.mockClient);

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
	@SuppressWarnings("rawtypes")
	@Test
	public void ajoutClientTest_clientPresent_v3() {
		CanalChat canalMock = Mockito.spy(new CanalChat("Chat"));

		String id = "1";

		Mockito.doReturn(true).when(canalMock).estPresent(mockClient);

		canalMock.ajouteClient(mockClient);

		String attributConcerne = "clients";
		Field attribut;

		try {
			attribut = CanalChat.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);

			Assert.assertFalse(((Hashtable) attribut.get(canalMock)).containsKey(id));

			Assert.assertEquals(0, ((Hashtable) attribut.get(canalMock)).size());

			Mockito.verifyNoMoreInteractions(this.mockClient);

			Mockito.verify(canalMock, Mockito.times(1)).estPresent(mockClient);

			Mockito.verifyNoMoreInteractions(this.mockClient);

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

}
