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
package org.fest.swing.remote.core;

import java.net.ServerSocket;
import java.net.Socket;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;
import static org.fest.swing.remote.core.Request.pingRequest;
import static org.fest.swing.remote.core.Response.Status.SUCCESS;
import static org.fest.swing.remote.core.TestServer.DEFAULT_PORT;
import static org.fest.swing.remote.core.TestServerUtils.waitUntilStarts;
import static org.fest.swing.remote.util.Serialization.deserialize;
import static org.fest.swing.remote.util.Serialization.serialize;

/**
 * Tests for <code>{@link DefaultTestServer}</code>.
 *
 * @author Alex Ruiz
 */
public class DefaultTestServerTest {

  private DefaultTestServer server;
  
  @BeforeMethod public void setUp() {
    server = new DefaultTestServer();
  }
  
  @Test public void shouldStartAtDefaultPortAndStop() {
    startServerAsynchronously();
    assertServerStarted(DEFAULT_PORT);
  }

  @Test(dependsOnMethods = "shouldStartAtDefaultPortAndStop") 
  public void shouldStartAtGivenPortAndStop() {
    final int expectedPort = 4000;
    newSingleThreadExecutor().execute(new Runnable() {
      public void run() { server.start(expectedPort); }
    });
    waitUntilStarts(server);
    assertServerStarted(expectedPort);
  }
  
  private void assertServerStarted(int expectedPort) {
    ServerSocketAssert socket = new ServerSocketAssert(serverSocket());
    assertThat(socket).isConnectedTo(expectedPort);
    assertThat(server.isRunning()).isTrue();
    server.stop();
    assertThat(socket).isClosed();
    assertThat(server.isRunning()).isFalse();
  }

  private ServerSocket serverSocket() {
    return field("serverSocket").ofType(ServerSocket.class).in(server).get();
  }
  
  @Test(dependsOnMethods = "shouldStartAtGivenPortAndStop") 
  public void shouldProcessClientRequest() throws Exception {
    startServerAsynchronously();
    try {
      Socket client = new Socket("localhost", DEFAULT_PORT);
      serialize(pingRequest(), client.getOutputStream());
      Response response = deserialize(client.getInputStream(), Response.class);
      assertThat(response.status()).isEqualTo(SUCCESS);
      client.close();
    } finally {
      server.stop();
    }
  }

  private void startServerAsynchronously() {
    newSingleThreadExecutor().execute(new Runnable() {
      public void run() { server.start(); }
    });
    waitUntilStarts(server);
  }
}
