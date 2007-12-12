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

import static org.easymock.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.remote.core.PingRequest.pingRequest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link RequestDispatcher}</code>.
 *
 * @author Alex Ruiz
 */
public class RequestDispatcherTest {

  private TestServer testServer;
  private RequestDispatcher dispatcher;

  @BeforeMethod public void setUp() {
    testServer = createMock(TestServer.class);
    dispatcher = new RequestDispatcher(testServer, robotWithNewAwtHierarchy());
  }

  @Test public void shouldHaveHandlerForPingRequests() {
    Request request = pingRequest();
    RequestHandler handler = dispatcher.handlerFor(request);
    assertThat(handler).isInstanceOf(PingRequestHandler.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfRequestIsNull() {
    dispatcher.handlerFor(null);
  }
}
