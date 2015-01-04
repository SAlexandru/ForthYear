package com.vvs.webserver.unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.vvs.webserver.unittest.http.AllHttpTests;
import com.vvs.webserver.unittest.webserver.AllWebServerTests;

@RunWith(Suite.class)
@SuiteClasses({AllWebServerTests.class, AllHttpTests.class})
public class AllTests {

}
