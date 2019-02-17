package camix.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite de tests unitaires JUnit 4 pour le programme Camix
 *
 * @version 1.0
 * @author Robin CARREZ
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ CanalChatTestEx1EasyMock.class, CanalChatTestEx1Mockito.class, ServiceChatTestEx2EasyMock.class })
public class CamixServiceTestSuite {
	/* empty class */
}
