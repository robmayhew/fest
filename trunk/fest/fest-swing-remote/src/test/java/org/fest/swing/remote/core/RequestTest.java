/*
 * Created on Dec 1, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.remote.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.remote.util.Serialization;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.remote.core.Request.pingRequest;
import static org.fest.swing.remote.core.Request.Type.PING;

/**
 * Tests for <code>{@link Request}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class RequestTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  private Request request;
  
  @BeforeMethod public void setUp() {
    request = pingRequest();
  }
 
  @Test public void shouldAddGetAndRemoveValues() {
    request.addValue("firstName", "Frodo");
    request.addValue("lastName", "Baggins");
    assertThat(request.value("firstName", String.class)).isEqualTo("Frodo");
    assertThat(request.value("lastName", String.class)).isEqualTo("Baggins");
    request.removeValue("lastName");
    assertThat(request.value("firstName", String.class)).isEqualTo("Frodo");
    assertThat(request.value("lastName", String.class)).isNull();
    logger.info(request.toString());
  }
  
  @Test(dependsOnMethods = "shouldAddGetAndRemoveValues") 
  public void shouldBeSerializable() throws Exception {
    request.addValue("firstName", "Frodo");
    request.addValue("lastName", "Baggins");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Serialization.serialize(request, out);
    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    Request deserialized = Serialization.deserialize(in, Request.class);
    assertThat(deserialized).isNotNull().isNotSameAs(request);
    assertThat(deserialized.type()).isEqualTo(PING);
    assertThat(deserialized.value("firstName", String.class)).isEqualTo("Frodo");
    assertThat(deserialized.value("lastName", String.class)).isEqualTo("Baggins");
  }
}
