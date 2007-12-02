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
package org.fest.swing.remote.util;

import java.io.IOException;
import java.net.Socket;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link Sockets}</code>.
 *
 * @author Alex Ruiz
 */
public class SocketsTest {
  
  @Test public void shouldNotCloseNullSocket() {
    // should not throw exceptions.
    Sockets.close(null);
  }

  @Test public void shouldCloseGivenSocket() {
    final Socket socket = createMock(Socket.class);
    new EasyMockTemplate(socket) {
      @Override protected void expectations() {
        try {
          socket.close();
        } catch (IOException e) {
          throw soften(e);
        }
        expectLastCall();
      }

      @Override protected void codeToTest() {
        Sockets.close(socket);
      }
    }.run();
  }

  @Test public void shouldCloseGivenSocketAndIgnoreException() {
    final Socket socket = createMock(Socket.class);
    new EasyMockTemplate(socket) {
      @Override protected void expectations() {
        try {
          socket.close();
        } catch (IOException e) {
          throw soften(e);
        }
        expectLastCall().andThrow(new IOException("Some I/O error"));
      }

      @Override protected void codeToTest() {
        Sockets.close(socket);
      }
    }.run();
  }

  private RuntimeException soften(IOException e) {
    return new RuntimeException(e);
  }
}
