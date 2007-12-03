/*
 * Created on Dec 2, 2007
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
package org.fest.swing.remote.client;

import java.net.Socket;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.remote.core.DefaultTestServer;
import org.fest.swing.remote.core.TestServer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;

/**
 * Tests for <code>{@link Connection}</code>.
 *
 * @author Alex Ruiz
 */
public class ConnectionTest {

  private TestServer server;
  private Connection connection;
  
  @BeforeClass public void setUp() {
    server = new DefaultTestServer();
    server.start();
    connection = new Connection();
  }
  
  @Test public void shouldConnectToServerAndClose() {
    String host = "localhost";
    int port = TestServer.DEFAULT_PORT;
    connection.connect(host, port);
    SocketAssert socket = new SocketAssert(connectionSocket());
    assertThat(socket).isConnectedTo(host, port);
    assertThat(connection.isConnected()).isTrue();
    connection.close();
    assertThat(socket).isClosed();
  }
  
  private Socket connectionSocket() {
    return field("socket").ofType(Socket.class).in(connection).get();
  }
  
  @AfterClass public void tearDown() {
    if (server != null) server.stop();
  }
}
