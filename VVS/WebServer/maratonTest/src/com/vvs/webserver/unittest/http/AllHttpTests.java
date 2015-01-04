package com.vvs.webserver.unittest.http;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContentTypeTest.class, HttpGetResponseTest.class, HttpRequestTests.class, HttpUtilityTest.class})
public class AllHttpTests {

}
