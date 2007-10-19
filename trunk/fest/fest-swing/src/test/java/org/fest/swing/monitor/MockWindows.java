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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_CLOSED;
import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_READY;
import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_SHOWING_BUT_NOT_READY;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_AS_CLOSED;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_AS_HIDDEN;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_AS_READY;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_AS_SHOWING;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_EXISTING;

/**
 * Understands a subclass of <code>{@link Windows}</code> which methods have been overriden to be public, allowing us to
 * create mocks.
 *
 * @author Alex Ruiz
 */
public class MockWindows extends Windows {

  enum MethodToMock {
    IS_CLOSED("isClosed"),
    IS_READY("isReady"),
    IS_SHOWING_BUT_NOT_READY("isShowingButNotReady"),
    MARK_AS_CLOSED("markAsClosed"),
    MARK_AS_HIDDEN("markAsHidden"),
    MARK_AS_READY("markAsReady"),
    MARK_AS_SHOWING("markAsShowing"),
    MARK_EXISTING("markExisting");

    final String methodName;

    private MethodToMock(String methodName) {
      this.methodName = methodName;
    }
  }

  private static final Map<MethodToMock, Method> METHODS_TO_MOCK = new HashMap<MethodToMock, Method>();
  
  static { populateMethodsToMock(); }

  private static void populateMethodsToMock() {
    try {
      mapMethod(IS_CLOSED, Component.class);
      mapMethod(IS_READY, Window.class);
      mapMethod(IS_SHOWING_BUT_NOT_READY, Window.class);
      mapMethod(MARK_AS_CLOSED, Window.class);
      mapMethod(MARK_AS_HIDDEN, Window.class);
      mapMethod(MARK_AS_READY, Window.class);
      mapMethod(MARK_AS_SHOWING, Window.class);
      mapMethod(MARK_EXISTING, Window.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void mapMethod(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    METHODS_TO_MOCK.put(method, method(method, parameterTypes));
  }
  
  private static Method method(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    return MockWindows.class.getMethod(method.methodName, parameterTypes);
  }

  public static Windows mock(MethodToMock...methodsToMock) throws Exception {
    Class<MockWindows> type = MockWindows.class;
    List<Method> methods = new ArrayList<Method>();
    for (MethodToMock method : methodsToMock)
      methods.add(METHODS_TO_MOCK.get(method));
    return createMock(type, methods.toArray(new Method[methods.size()]));
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
