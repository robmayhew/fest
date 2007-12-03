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

import org.fest.assertions.AssertExtension;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Understands assertion methods releated to sockets.
 *
 * @author Alex Ruiz
 */
public final class ServerSocketAssert implements AssertExtension {

  private final ServerSocket socket;

  /**
   * Creates a new </code>{@link ServerSocketAssert}</code>.
   * @param socket the socket to verify.
   */
  public ServerSocketAssert(ServerSocket socket) {
    this.socket = socket;
  }

  public ServerSocketAssert isConnectedTo(int port) {
    assertThat(socket.isBound()).isTrue();
    assertThat(socket.getLocalPort()).isEqualTo(port);
    assertThat(socket.isClosed()).isFalse();
    return this;
  }
  
  public ServerSocketAssert isClosed() {
    assertThat(socket.isClosed()).isTrue();
    return this;
  }
}
