package com.vvs.webserver.unittest.webserver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ WebServerStateTests.class, WebServerTest.class, MainTests.class})
public class AllWebServerTests {

}
