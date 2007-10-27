/*
 * Created on Oct 26, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.hierarchy.MockChildrenFinder.MethodToMock.CHILDREN_OF;

/**
 * Understands a subclass of <code>{@link ChildrenFinder}</code> which methods have been overriden to be public,
 * allowing us to create mocks.
 * 
 * @author Alex Ruiz
 */
public class MockChildrenFinder extends ChildrenFinder {

  enum MethodToMock {
    CHILDREN_OF("childrenOf");
    
    final String methodName;

    private MethodToMock(String methodName) {
      this.methodName = methodName;
    }
  }
  
  private static final Map<MethodToMock, Method> METHODS_TO_MOCK = new HashMap<MethodToMock, Method>();
  
  static { populateMethodsToMock(); }

  private static void populateMethodsToMock() {
    try {
      mapMethod(CHILDREN_OF, Component.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static void mapMethod(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    METHODS_TO_MOCK.put(method, method(method, parameterTypes));
  }
  
  private static Method method(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    return MockChildrenFinder.class.getMethod(method.methodName, parameterTypes);
  }

  public static ChildrenFinder mock(MethodToMock...methodsToMock) throws Exception {
    Class<MockChildrenFinder> type = MockChildrenFinder.class;
    Method[] methods = new Method[methodsToMock.length];
    for (int i = 0; i < methods.length; i++)
      methods[i] = METHODS_TO_MOCK.get(methodsToMock[i]);
    return createMock(type, methods);
  }

  
  @Override public Collection<Component> childrenOf(Component c) { return null; }

  public MockChildrenFinder() {}
}
