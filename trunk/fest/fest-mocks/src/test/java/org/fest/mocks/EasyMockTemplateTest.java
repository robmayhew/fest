/*
 * Created on Apr 22, 2007
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
package org.fest.mocks;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.createMock;

/**
 * Unit tests for <code>{@link EasyMockTemplate}</code>.
 *
 * @author Alex Ruiz
 */
public class EasyMockTemplateTest {

  private static interface Server {
    void process(String arg);
  }
  
  private static class Client {
    final Server server;
    
    Client(Server server) {
      this.server = server;
    }
    
    void delegateProcessToServer(String arg) {
      server.process(arg);
    }
  }
  
  private Client client;
  private Server server;
  
  @BeforeMethod public void setUp() {
    server = createMock(Server.class);
    client = new Client(server);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksIsNull() {
    new EasyMockTemplate(new Object[0]) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksContainsNull() {
    Object[] mocks = new Object[] { server, null };
    new EasyMockTemplate(mocks) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksIsEmpty() {
    new EasyMockTemplate((Object[])null) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };    
  }
  
  @Test public void shouldSetAndVerifyExpectations() {
    final String arg = "name";
    EasyMockTemplate template = new EasyMockTemplate(server) {

      @Override protected void expectations() {
        server.process(arg);  
      }
      
      @Override protected void codeToTest() {
        client.delegateProcessToServer(arg);
      }
    };
    template.run();
  }
}
