package lrg.patrools.uniformity;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsLACalls {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for LACalls");
		suite.addTestSuite(LACallsTests.class);
		suite.addTestSuite(LAMaxCallsTests.class);
		suite.addTestSuite(LAMinCallsTests.class);
		suite.addTestSuite(LASumCallsTests.class);
		return suite;
	}

}
