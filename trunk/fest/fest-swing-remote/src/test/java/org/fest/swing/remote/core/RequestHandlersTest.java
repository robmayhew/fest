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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.remote.core.Request.pingRequest;

/**
 * Tests for <code>{@link RequestHandlers}</code>.
 *
 * @author Alex Ruiz
 */
public class RequestHandlersTest {

  private TestServer testServer;
  private RequestHandlers handlers;
  
  @BeforeMethod public void setUp() {
    testServer = createMock(TestServer.class);
    handlers = new RequestHandlers(testServer);
  }
  
  @Test public void shouldHaveHandlerForPingRequests() {
    Request request = pingRequest();
    RequestHandler handler = handlers.handlerFor(request);
    assertThat(handler).isInstanceOf(PingRequestHandler.class);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRequestIsNull() {
    handlers.handlerFor(null);
  }
}
