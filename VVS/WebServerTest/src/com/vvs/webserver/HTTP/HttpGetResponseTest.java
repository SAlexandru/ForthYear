package com.vvs.webserver.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.nio.file.Path;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>HttpGetResponseTest</code> contains tests for the class <code>{@link HttpGetResponse}</code>.
 *
 * @generatedBy CodePro at 1/3/15 4:40 PM
 * @author salexandru
 * @version $Revision: 1.0 $
 */
public class HttpGetResponseTest {
	/**
	 * Run the HttpGetResponse(HttpRequest,Path) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test
	public void testHttpGetResponse_1()
		throws Exception {
		HttpRequest request = new HttpRequest(new PipedInputStream());
		Path base = null;

		HttpGetResponse result = new HttpGetResponse(request, base);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.io.IOException: Pipe not connected
		//       at java.io.PipedInputStream.read(PipedInputStream.java:305)
		//       at java.io.PipedInputStream.read(PipedInputStream.java:377)
		//       at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
		//       at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
		//       at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
		//       at java.io.InputStreamReader.read(InputStreamReader.java:184)
		//       at java.io.BufferedReader.fill(BufferedReader.java:161)
		//       at java.io.BufferedReader.readLine(BufferedReader.java:324)
		//       at java.io.BufferedReader.readLine(BufferedReader.java:389)
		//       at com.vvs.webserver.HTTP.HttpRequest.<init>(HttpRequest.java:24)
		assertNotNull(result);
	}

	/**
	 * Run the HttpGetResponse(HttpRequest,Path) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test
	public void testHttpGetResponse_2()
		throws Exception {
		HttpRequest request = new HttpRequest(new PipedInputStream());
		Path base = null;

		HttpGetResponse result = new HttpGetResponse(request, base);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.io.IOException: Pipe not connected
		//       at java.io.PipedInputStream.read(PipedInputStream.java:305)
		//       at java.io.PipedInputStream.read(PipedInputStream.java:377)
		//       at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
		//       at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
		//       at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
		//       at java.io.InputStreamReader.read(InputStreamReader.java:184)
		//       at java.io.BufferedReader.fill(BufferedReader.java:161)
		//       at java.io.BufferedReader.readLine(BufferedReader.java:324)
		//       at java.io.BufferedReader.readLine(BufferedReader.java:389)
		//       at com.vvs.webserver.HTTP.HttpRequest.<init>(HttpRequest.java:24)
		assertNotNull(result);
	}

	/**
	 * Run the HttpGetResponse(HttpRequest,Path) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testHttpGetResponse_3()
		throws Exception {
		HttpRequest request = null;
		Path base = null;

		HttpGetResponse result = new HttpGetResponse(request, base);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_1()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_2()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_3()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_4()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_5()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_6()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_7()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_8()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_9()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_10()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_11()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Run the void send(OutputStream) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Test(expected = java.io.IOException.class)
	public void testSend_12()
		throws Exception {
		HttpGetResponse fixture = new HttpGetResponse(new HttpRequest(new PipedInputStream()), (Path) null);
		OutputStream out = new ByteArrayOutputStream();

		fixture.send(out);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 1/3/15 4:40 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(HttpGetResponseTest.class);
	}
}