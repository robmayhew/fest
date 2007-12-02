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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

import static org.fest.swing.remote.core.Response.failed;
import static org.fest.swing.remote.util.Serialization.deserialize;
import static org.fest.swing.remote.util.Serialization.serialize;
import static org.fest.swing.remote.util.Sockets.close;
import static org.fest.util.Strings.concat;

/**
 * Understands a server that accepts client requests that either simulate user interaction with the GUI under test or 
 * verify the state of a GUI component.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DefaultTestServer implements TestServer {
  
  /** Default port. */
  public static final int DEFAULT_PORT = 4123;

  private static Logger logger = Logger.getLogger(DefaultTestServer.class.getName());
  
  private final int port;
  private final RequestHandlers requestHandlers;

  private ServerSocket serverSocket;
  
  /**
   * Creates a new </code>{@link DefaultTestServer}</code>, connecting to the port specified by 
   * <code>{@link #DEFAULT_PORT}</code>.
   */
  public DefaultTestServer() {
    this(DEFAULT_PORT);
  }
  
  /**
   * Creates a new </code>{@link DefaultTestServer}</code>.
   * @param port the port to connect to.
   */
  public DefaultTestServer(int port) {
    this.port = port;
    requestHandlers = new RequestHandlers(this);
  }

  /** @see org.fest.swing.remote.core.TestServer#start() */
  public void start() {
    serverSocket = createServerSocket(port);
    logger.info(concat("GUI Test Server started at port ", asString(port)));
    service();    
  }
  
  private ServerSocket createServerSocket(int port) {
    try {
      return new ServerSocket(port);
    } catch (Exception e) {
      String errorMessage = concat("Cannot start server at port ", asString(port));
      logger.severe(errorMessage);
      throw new RemoteActionFailure(errorMessage, e);
    }
  }
  
  private String asString(int i) { return String.valueOf(i); }
  
  /** Listens for client requests. */
  private void service() {
    logger.info("Listening for client requests");
    while(true) {
      Socket client = null;
      try {
        client = serverSocket.accept();
        service(client);
      } catch (Exception e) {
        sendFailure(client, e);
      } finally {
        close(client);
      }
    }
  }

  private void service(Socket client) throws Exception {
    Request request = deserialize(client.getInputStream(), Request.class);
    Response response = requestHandlers.handlerFor(request).process(request);
    serialize(response, client.getOutputStream());
  }
  
  private void sendFailure(Socket client, Exception cause) {
    logger.log(SEVERE, "Unable to handle client request", cause);
    if (client == null || !client.isConnected()) return;
    try {
      serialize(failed(cause), client.getOutputStream());
    } catch (Exception e) {
      logger.log(SEVERE, "Unable to send failed response to client", e);
    }
  }
  
  /** @see org.fest.swing.remote.core.TestServer#isRunning() */
  public boolean isRunning() {
    return serverSocket != null && serverSocket.isBound();
  }
  
  /** @see org.fest.swing.remote.core.TestServer#stop() */
  public void stop() {
    logger.info("Stopping server");
    if (serverSocket == null) return;
    try {
      serverSocket.close();
    } catch (IOException ignored) {
      logger.log(SEVERE, "Ignoring exception when stopping server", ignored);
    }
    logger.info("Server stopped");
  }
}
