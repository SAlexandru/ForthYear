package com.vvs.webserver.unittest.webserver;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vvs.webserver.WebServerState;

public class WebServerStateTests {

	@Test
	public void testWebServereToString() {
		assertEquals(WebServerState.MAINTENANCE.toString(), "MAINTENANCE"); 
		assertEquals(WebServerState.RUNNING.toString(),    "RUNNING");
		assertEquals(WebServerState.STOPPED.toString(),    "STOPPED");
	}
	
	@Test
	public void testWebServerFromValue() {
		assertEquals(WebServerState.MAINTENANCE, WebServerState.valueOf("MAINTENANCE")); 
		assertEquals(WebServerState.RUNNING,     WebServerState.valueOf("RUNNING"));
		assertEquals(WebServerState.STOPPED,     WebServerState.valueOf("STOPPED"));
	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWebServerThrow() {
		WebServerState.valueOf("....");
	}

}