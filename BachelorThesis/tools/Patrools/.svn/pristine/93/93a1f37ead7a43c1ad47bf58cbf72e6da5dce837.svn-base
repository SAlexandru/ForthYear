package lrg.patrools.uniformity;

import junit.framework.TestCase;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.patrools.tests.util.TestUtil;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import plugins.Wrapper;

public class LACallsTests extends TestCase {

	@Before
	public void setUp() {
		TestUtil.importProject("PatroolsUniformityProjectTests", "PatroolsUniformityProjectTests.zip");
	}
	
	@After
	public void tearDown() {
		TestUtil.deleteProject("PatroolsUniformityProjectTests");
	}

	@Test
	public void testLAExternalCase() throws JavaModelException {
		IJavaProject theProject = TestUtil.getProject("PatroolsUniformityProjectTests");
		IType theClass;
		Assert.assertNotNull(theClass = (IType)theProject.findType("test2.Client"));
		Wrapper theClassWrapper = Wrapper.createInstance(theClass);
		theClassWrapper.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
		GroupEntity methodGroup = theClassWrapper.getGroup("method group");
		for(int i = 0; i < methodGroup.size(); i++) {
			AbstractEntityInterface theClient = methodGroup.getElementAt(i);
			if(theClient.getName().equals("one")) {
				Assert.assertEquals(0.5, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("callToConstructorIngnored")) {
				Assert.assertEquals(-1, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("callToPureLibraryMethodsIgnored")) {
				Assert.assertEquals(-1, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("callToThis")) {
				Assert.assertEquals(0, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("callToSpecificMethods")) {
				Assert.assertEquals(0, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("callTotoStringOverriden")) {
				//Not clear how to avoid library methods that are overriden in the application code ?
				Assert.assertEquals(-1, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.001);
			}
			if(theClient.getName().equals("callToequalsNotOverriden")) {
				Assert.assertEquals(-1, (Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
		}
		Assert.assertEquals(0.16, (Double)theClassWrapper.getProperty("avg class invocations level of abstraction").getValue(),0.01);
	}

	@Test
	public void testLASpecialCase() throws JavaModelException {
		IJavaProject theProject = TestUtil.getProject("PatroolsUniformityProjectTests");
		IType theClass;
		Assert.assertNotNull(theClass = (IType)theProject.findType("test3.SpecialClient"));
		Wrapper theClassWrapper = Wrapper.createInstance(theClass);
		theClassWrapper.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
		GroupEntity methodGroup = theClassWrapper.getGroup("method group");
		for(int i = 0; i < methodGroup.size(); i++) {
			AbstractEntityInterface theClient = methodGroup.getElementAt(i);
			if(theClient.getName().equals("callViaSuperType")) {
				assertEquals(0,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("callViaSpecificType")) {
				assertEquals(0,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
		}
		Assert.assertEquals(0, (Double)theClassWrapper.getProperty("avg class invocations level of abstraction").getValue(),0.01);
	}
	
	@Test
	public void testLAIntraCase() throws JavaModelException {
		IJavaProject theProject = TestUtil.getProject("PatroolsUniformityProjectTests");
		IType theClass;
		Assert.assertNotNull(theClass = (IType)theProject.findType("test4.A"));
		Wrapper theClassWrapper = Wrapper.createInstance(theClass);
		theClassWrapper.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
		GroupEntity methodGroup = theClassWrapper.getGroup("method group");
		for(int i = 0; i < methodGroup.size(); i++) {
			AbstractEntityInterface theClient = methodGroup.getElementAt(i);
			if(theClient.getName().equals("firstMethod")) {
				assertEquals(1,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("secondMethod")) {
				assertEquals(1,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("aMethod")) {
				assertEquals(-1,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
		}
		Assert.assertEquals(1, (Double)theClassWrapper.getProperty("avg class invocations level of abstraction").getValue(),0.01);

		Assert.assertNotNull(theClass = (IType)theProject.findType("test4.C"));
		 theClassWrapper = Wrapper.createInstance(theClass);
		theClassWrapper.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
		methodGroup = theClassWrapper.getGroup("method group");
		for(int i = 0; i < methodGroup.size(); i++) {
			AbstractEntityInterface theClient = methodGroup.getElementAt(i);
			if(theClient.getName().equals("someMethod")) {
				assertEquals(0,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
		}
		Assert.assertEquals(0, (Double)theClassWrapper.getProperty("avg class invocations level of abstraction").getValue(),0.01);

		Assert.assertNotNull(theClass = (IType)theProject.findType("test4.CD"));
		 theClassWrapper = Wrapper.createInstance(theClass);
		theClassWrapper.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
		methodGroup = theClassWrapper.getGroup("method group");
		for(int i = 0; i < methodGroup.size(); i++) {
			AbstractEntityInterface theClient = methodGroup.getElementAt(i);
			if(theClient.getName().equals("aMethod")) {
				assertEquals(-1,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("firstMethod")) {
				assertEquals(-1,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
			if(theClient.getName().equals("secondMethod")) {
				assertEquals(0.5,(Double)theClient.getProperty("avg invocations level of abstraction").getValue(),0.01);
			}
		}
		Assert.assertEquals(0.5, (Double)theClassWrapper.getProperty("avg class invocations level of abstraction").getValue(),0.01);
	
	}


}
