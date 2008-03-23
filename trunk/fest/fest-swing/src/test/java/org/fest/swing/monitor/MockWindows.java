/*
 * Created on Oct 14, 2007
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

import java.awt.Component;
import java.awt.Window;

import static org.easymock.classextension.EasyMock.createMock;

/**
 * Understands a subclass of <code>{@link Windows}</code> which methods have been overriden to be public, allowing us to
 * create mocks.
 *
 * @author Alex Ruiz
 */
public class MockWindows extends Windows {

  public static Windows mock() {
    return createMock(MockWindows.class);
  }
  
  @Override public void markExisting(Window w) {}

  @Override public void markAsClosed(Window w) {}

  @Override public void markAsHidden(Window w) {}

  @Override public void markAsReady(Window w) {}

  @Override public void markAsShowing(Window w) {}
  
  @Override public boolean isClosed(Component c) { return false; }
  
  @Override public boolean isReady(Window w) { return false; }

  @Override public boolean isShowingButNotReady(Window w) { return false; }

  public MockWindows() {}
}
