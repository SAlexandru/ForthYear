package lrg.patrools.tests;

import lrg.patrools.tests.util.TestUtil;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.*;


public class TestExample {
	
	@Before
	public void setUp() {
		TestUtil.importProject("Project1", "TestDataProject1.zip");
	}
	
	@After
	public void tearDown() {
		TestUtil.deleteProject("Project1");
	}
	
	@Test
	public void test1() throws JavaModelException {
		IJavaProject theProject = TestUtil.getProject("Project1");
		Assert.assertNotNull((IType)theProject.findType("test1.A"));
	}
	
}
