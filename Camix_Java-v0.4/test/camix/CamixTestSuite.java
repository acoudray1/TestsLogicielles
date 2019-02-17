package camix;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import camix.service.CamixServiceTestSuite;

/**
 * Suite de tests unitaires JUnit 4 pour le programme Camix
 *
 * @version 4.1
 * @author Axel COUDRAY
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ CamixServiceTestSuite.class })
public class CamixTestSuite {
	/* empty class */
}
