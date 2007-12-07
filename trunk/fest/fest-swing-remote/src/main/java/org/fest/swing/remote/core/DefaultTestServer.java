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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.logging.Level.SEVERE;

import static org.fest.swing.remote.core.RemoteActionFailure.failure;
import static org.fest.swing.remote.core.Response.failed;
import static org.fest.swing.remote.util.Serialization.*;
import static org.fest.swing.remote.util.Sockets.close;
import static org.fest.util.Strings.concat;

/**
 * Understands a server that accepts client requests that either simulate user interaction with the GUI under test or 
 * verify the state of a GUI component.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class DefaultTestServer implements TestServer {
  
  private static Logger logger = Logger.getLogger(DefaultTestServer.class.getName());

  private final ExecutorService executor = newSingleThreadExecutor();
  private final RequestDispatcher requestHandlers;
  
  private ServerSocket serverSocket;
  
  /**
   * Creates a new </code>{@link DefaultTestServer}</code>, connecting to the port specified by 
   * <code>{@link #DEFAULT_PORT}</code>.
   */
  public DefaultTestServer() {
    requestHandlers = new RequestDispatcher(this);
  }
  
  /** {@inheritDoc} */
  public void start() {
    start(DEFAULT_PORT);
  }
  
  /** {@inheritDoc} */
  public void start(int port) {
    serverSocket = createServerSocket(port);
    logger.info(concat("GUI Test Server started at port ", String.valueOf(port)));
    newSingleThreadExecutor().execute(new Runnable() {
      public void run() { service(); }
    });
  }
  
  private ServerSocket createServerSocket(int port) {
    try {
      return new ServerSocket(port);
    } catch (Exception e) {
      String errorMessage = concat("Cannot start server at port ", String.valueOf(port));
      logger.severe(errorMessage);
      throw new RemoteActionFailure(errorMessage, e);
    }
  }
  
  /** Listens for client requests. */
  private void service() {
    logger.info("Listening for client requests");
    while(!executor.isShutdown()) {
      try {
        final Socket client = serverSocket.accept();
        executor.execute(new Runnable() {
          public void run() { service(client); }
        });
      } catch (RejectedExecutionException ignored) {
      } catch (IOException e) {
        if (!executor.isShutdown()) {
          logger.log(SEVERE, concat("Unable connect to clients"), e);
          executor.shutdown();
        }
      }
    }
  }

  private void service(Socket client) {
    try {
      Response response = requestHandlers.dispatch(requestFrom(client));
      serialize(response, client.getOutputStream());
    } catch (Exception e) {
      String message = "Unable to handle client request";
      logger.log(SEVERE, concat(message), e);
      sendFailure(client, failure(message, e));
    } finally {
      close(client);
    }
  }

  private Request requestFrom(Socket client) throws Exception {
    return deserialize(client.getInputStream(), Request.class);
  }
  
  private void sendFailure(Socket client, RemoteActionFailure cause) {
    if (client == null || !client.isConnected()) return;
    try {
      serialize(failed(cause), client.getOutputStream());
    } catch (Exception e) {
      logger.log(SEVERE, "Unable to send failed response to client", e);
    }
  }
  
  /** {@inheritDoc} */
  public boolean isRunning() {
    return serverSocket != null && serverSocket.isBound() && !serverSocket.isClosed();
  }
  
  /** {@inheritDoc} */
  public void stop() {
    executor.shutdown();
    if (serverSocket == null) return;
    logger.info("Stopping server");
    try {
      serverSocket.close();
    } catch (IOException ignored) {
      logger.log(SEVERE, "Ignoring exception when stopping server", ignored);
    }
    logger.info("Server stopped");
  }
}
