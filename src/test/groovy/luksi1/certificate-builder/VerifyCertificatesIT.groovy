#!/usr/bin/env groovy

import junit.framework.Test
import junit.textui.TestRunner
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.equalTo
import static org.junit.matchers.JUnitMatchers.*

class VerifyCertificatesIT extends GroovyTestCase {

  void testRootCertificate() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -text -in " + dir + "/certs/root.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("CN=root.test"))
  }

  void testIntermediateCertificate() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -text -in " + dir + "/certs/intermediate.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("CN=intermediate.test"))
  }

  void testServerCertificate() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -text -in " + dir + "/certs/luksi1.test.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("CN=luksi1.test"))
  }

  // void testCertificateRevocationList() {
  //   def dir = System.getProperty("certificate_directory")
  //   def command = "openssl crl -noout -text -in " + dir + "/intermediate/crl/intermediate.crl"
  //   def proc = command.execute()
  //   proc.waitFor()
  //   assertEquals(proc.exitValue(), 0)
  //   assertThat(proc.in.text, containsString("CN=intermediate.test"))
  // }

  
}
