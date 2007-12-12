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
package org.fest.swing.remote.client;

import static java.util.logging.Level.SEVERE;
import static org.fest.swing.remote.core.PingRequest.pingRequest;
import static org.fest.swing.remote.core.RemoteActionFailure.failure;
import static org.fest.swing.remote.util.Serialization.deserialize;
import static org.fest.swing.remote.util.Serialization.serialize;
import static org.fest.swing.remote.util.System.LINE_SEPARATOR;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import org.fest.swing.remote.core.RemoteActionFailure;
import org.fest.swing.remote.core.Request;
import org.fest.swing.remote.core.Response;

/**
 * Understands a client connection.
 *
 * @author Alex Ruiz
 */
public final class Connection {

  private static Logger logger = Logger.getLogger(Connection.class.getName());

  private Socket socket;

  /**
   * Connects to given named host at the given port.
   * @param host the name of the host to connect to.
   * @param port the port to connect to.
   * @throws RemoteActionFailure if the connection to the host could not be established.
   */
  public void connect(String host, int port) {
    try {
      logger.info(concat("Connecting to host ", quote(host), " at port ", String.valueOf(port)));
      socket = new Socket(host, port);
      pingHost();
      logger.info("Connected");
    } catch (Exception e) {
      throw logAndThrow("Cannot connect to host", e);
    }
  }

  private void pingHost() {
    Response response = send(pingRequest());
    if (response == null) throw new RemoteActionFailure("Ping response not returned");
    if (response.successful()) return;
    throw logAndThrow("Cannot ping server", response.cause());
  }

  /**
   * Indicates whether this connection is connected to the host.
   * @return <code>true</code> if this connection is connected to the host, <code>false</code> otherwise.
   */
  public boolean isConnected() {
    return socket != null && socket.isConnected() && !socket.isClosed();
  }

  /**
   * Closes this connection.
   * @throws RemoteActionFailure if the connection cannot be closed.
   */
  public void close() {
    if (socket == null) return;
    try {
      logger.info("Closing connection to host");
      socket.close();
      logger.info("Connection to host closed");
    } catch (IOException e) {
      throw logAndThrow("Cannot close connection", e);
    }
  }

  /**
   * Sends the given request to the host.
   * @param request the request to send.
   * @return the response from the host.
   * @throws IllegalArgumentException if the request is <code>null</code>.
   * @throws RemoteActionFailure if the request cannot be sent or the response cannot be read.
   */
  public Response send(Request request) {
    validate(request);
    try {
      serialize(request, socket.getOutputStream());
      return deserialize(socket.getInputStream(), Response.class);
    } catch (Exception e) {
      throw logAndThrow(concat("Unable to send request to server:", LINE_SEPARATOR, request), e);
    }
  }

  private void validate(Request request) {
    if (request == null) throw new IllegalArgumentException("Request should not be null");
  }

  private RemoteActionFailure logAndThrow(String message, Exception e) {
    logger.log(SEVERE, message, e);
    throw failure(message, e);
  }
}
