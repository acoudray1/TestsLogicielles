package felix;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite de tests unitaires pour le programme Felix
 *
 * @version 1.0
 * @author Axel COUDRAY
 *
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FelixConnexionImpossible.class,
        FelixConnexionPossible.class,
        FelixTestQuitter.class
})
public class FelixTestSuite {


}
