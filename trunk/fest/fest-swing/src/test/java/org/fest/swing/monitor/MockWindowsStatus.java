/*
 * Created on Oct 18, 2007
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
package org.fest.swing.monitor;

import java.awt.Window;

import static org.easymock.classextension.EasyMock.createMock;

/**
 * Understands a subclass of <code>{@link WindowStatus}</code> which methods have been overriden to be public,
 * allowing us to create mocks.
 * 
 * @author Alex Ruiz
 */
public class MockWindowsStatus extends WindowStatus {
  
  public static WindowStatus mock() throws Exception {
    return createMock(MockWindowsStatus.class);
  } 
  
  @Override public void checkIfReady(Window w) {}

  public MockWindowsStatus() {
    this(new Windows());
  }
  
  public MockWindowsStatus(Windows windows) {
    super(windows);
  }
}
