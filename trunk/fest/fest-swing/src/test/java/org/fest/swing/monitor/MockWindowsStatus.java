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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.monitor.MockWindowsStatus.MethodToMock.CHECK_IF_READY;

/**
 * Understands a subclass of <code>{@link WindowStatus}</code> which methods have been overriden to be public,
 * allowing us to create mocks.
 * 
 * @author Alex Ruiz
 */
public class MockWindowsStatus extends WindowStatus {
  
  enum MethodToMock {
    CHECK_IF_READY("checkIfReady");
    
    final String methodName;

    private MethodToMock(String methodName) {
      this.methodName = methodName;
    }
  }
  
  private static final Map<MethodToMock, Method> METHODS_TO_MOCK = new HashMap<MethodToMock, Method>();
  
  static { populateMethodsToMock(); }

  private static void populateMethodsToMock() {
    try {
      mapMethod(CHECK_IF_READY, Window.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static void mapMethod(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    METHODS_TO_MOCK.put(method, method(method, parameterTypes));
  }
  
  private static Method method(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    return MockWindowsStatus.class.getMethod(method.methodName, parameterTypes);
  }

  public static WindowStatus mock(MethodToMock...methodsToMock) throws Exception {
    Class<MockWindowsStatus> type = MockWindowsStatus.class;
    Method[] methods = new Method[methodsToMock.length];
    for (int i = 0; i < methods.length; i++)
      methods[i] = METHODS_TO_MOCK.get(methodsToMock[i]);
    return createMock(type, methods);
  } 
  
  @Override public void checkIfReady(Window w) {}

  public MockWindowsStatus() {
    this(new Windows());
  }
  
  public MockWindowsStatus(Windows windows) {
    super(windows);
  }
}
