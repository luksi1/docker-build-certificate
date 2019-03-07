#!/usr/bin/env groovy

import junit.framework.Test
import junit.textui.TestRunner
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*
import static org.junit.matchers.JUnitMatchers.*

class VerifyCertificateBundlesIT extends GroovyTestCase {

  void testKeystore() {
    def dir = System.getProperty("certificate_directory")
    def password = System.getProperty("password")
    def command = "keytool -list -v -keystore " + dir + "/jks/keystore.jks -storepass " + password
    def proc = command.execute()
    proc.waitFor()
    def stdout = proc.in.text
    assertEquals(proc.exitValue(), 0)
    assertThat(stdout, containsString("CN=luksi1.test"))
    //assertThat(proc.in.text, containsString("Alias name: server"))
  }

  void testTruststore() {
    def dir = System.getProperty("certificate_directory")
    def password = System.getProperty("password")
    def command = "keytool -list -v -keystore " + dir + "/jks/truststore.jks -storepass " + password
    def proc = command.execute()
    proc.waitFor()
    def stdout = proc.in.text
    assertEquals(proc.exitValue(), 0)
    assertThat(stdout, containsString("CN=root.test"))
    assertThat(stdout, containsString("Alias name: root"))
    assertThat(stdout, containsString("CN=intermediate.test"))
    assertThat(stdout, containsString("Alias name: intermediate"))

  }

  void testServerPKCS12() {
    def dir = System.getProperty("certificate_directory")
    def password = System.getProperty("password")
    def command = "keytool -list -v -storetype pkcs12 -keystore " + dir + "/pkcs12/luksi1.test.p12 -storepass " + password
    def proc = command.execute()
    proc.waitFor()
    def stdout = proc.in.text
    assertEquals(proc.exitValue(), 0)
    assertThat(stdout, containsString("CN=luksi1.test"))
    assertThat(stdout, containsString("CN=intermediate.test"))
    assertThat(stdout, containsString("CN=root.test"))
  }

  void testPKCS12FilePermissions() {
    def dir = System.getProperty("certificate_directory")
    def command = "/bin/ls -l " + dir + "/pkcs12/luksi1.test.p12"
    def proc = command.execute()
    proc.waitFor()
    def stdout = proc.in.text
    assertEquals(proc.exitValue(), 0)
    assertThat(stdout, containsString("-r--------"))
  }
 
}

