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

import java.awt.Window;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.classextension.EasyMock.createMock;

/**
 * Understands a subclass of <code>{@link Windows}</code> which methods have been overriden to be public, allowing us to
 * create mocks.
 *
 * @author Alex Ruiz
 */
public class MockWindows extends Windows {
  
  static final String MARK_AS_CLOSED = "markAsClosed";
  static final String MARK_AS_HIDDEN = "markAsHidden";
  static final String MARK_AS_READY = "markAsReady";
  static final String MARK_AS_SHOWING = "markAsShowing";
  
  private static final Map<String, Method> METHODS_TO_MOCK = new HashMap<String, Method>();
  
  static { populateMethodsToMock(); }

  private static void populateMethodsToMock() {
    try {
      Class<MockWindows> type = MockWindows.class;
      METHODS_TO_MOCK.put(MARK_AS_CLOSED, type.getMethod(MARK_AS_CLOSED, Window.class));
      METHODS_TO_MOCK.put(MARK_AS_HIDDEN, type.getMethod(MARK_AS_HIDDEN, Window.class));
      METHODS_TO_MOCK.put(MARK_AS_READY, type.getMethod(MARK_AS_READY, Window.class));
      METHODS_TO_MOCK.put(MARK_AS_SHOWING, type.getMethod(MARK_AS_SHOWING, Window.class));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Windows mock(String...methodsToMock) throws Exception {
    Class<MockWindows> type = MockWindows.class;
    List<Method> methods = new ArrayList<Method>();
    for (String methodName : methodsToMock)
      methods.add(METHODS_TO_MOCK.get(methodName));
    return createMock(type, methods.toArray(new Method[methods.size()]));
  }
  
  @Override public void markAsClosed(Window w) {}

  @Override public void markAsHidden(Window w) {}

  @Override public void markAsReady(Window w) {}

  @Override public void markAsShowing(Window w) {}

  public MockWindows() {}
}
