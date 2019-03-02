#!/usr/bin/env groovy

import junit.framework.Test
import junit.textui.TestRunner
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.equalTo
import static org.junit.matchers.JUnitMatchers.*

class VerifyCertificatesDefaultIT extends GroovyTestCase {

  void testRootCertificate() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -subject -in " + dir + "/certs/root.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("CN=root.test"))
  }

  void testIntermediateCertificate() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -subject -in " + dir + "/certs/intermediate.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("CN=intermediate.test"))
  }

  void testServerCertificate() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -subject -in " + dir + "/certs/luksi1.test.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("CN=luksi1.test"))
  }

  void testServerCertificateSerialNumber() {
    def dir = System.getProperty("certificate_directory")
    def serialNumber = System.getProperty("serialnumber")
    def command = "openssl x509 -noout -subject -in " + dir + "/certs/luksi1.test.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("serialNumber=" + serialNumber))
  }

  void testCertificateRevocationList() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl crl -noout -text -in " + dir + "/crl/intermediate.crl"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(0, proc.exitValue())
    assertThat(proc.in.text, containsString("CN=intermediate.test"))
  }

  void testServerCertificateExpirationDate() {
    def dir = System.getProperty("certificate_directory")
    def days = System.getProperty("server_days") as Integer
    def expired = days*86400
    def valid = expired-30

    // the certificate should not have expired 1 day ago - 10 seconds, ie 86400-10 seconds ago
    def command1 = "openssl x509 -noout -checkend " + valid + " -in " + dir + "/certs/luksi1.test.crt"
    def proc1 = command1.execute()
    proc1.waitFor()
    assertEquals(0, proc1.exitValue())

    // the certificate should have expired 1 day ago, ie 86400 seconds ago
    def command2 = "openssl x509 -noout -checkend " + expired + " -in " + dir + "/certs/luksi1.test.crt"
    def proc2 = command2.execute()
    proc2.waitFor()
    assertEquals(proc2.exitValue(), 1)
  }
  
}
