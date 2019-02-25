#!/usr/bin/env groovy

import junit.framework.Test
import junit.textui.TestRunner
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.equalTo
import static org.junit.matchers.JUnitMatchers.*

class VerifyOIDCertificateIT extends GroovyTestCase {

  void testOID() {
    def dir = System.getProperty("certificate_directory")
    def command = "openssl x509 -noout -text -in " + dir + "/certs/luksi1.test.crt"
    def proc = command.execute()
    proc.waitFor()
    assertEquals(proc.exitValue(), 0)
    assertThat(proc.in.text, containsString("serialNumber=SE0000000000-S000123456789"))
  }

}

