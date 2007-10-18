/*
 * Created on Oct 17, 2007
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
import java.awt.EventQueue;
import java.awt.Window;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.monitor.MockContext.MethodToMock.ADD_CONTEXT_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.ALL_EVENT_QUEUES;
import static org.fest.swing.monitor.MockContext.MethodToMock.EVENT_QUEUE_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.LOOKUP_EVENT_QUEUE_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.REMOVE_CONTEXT_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.ROOT_WINDOWS;

/**
 * Understands a subclass of <code>{@link Context}</code> which methods have been overriden to be public, allowing us to
 * create mocks.
 *
 * @author Alex Ruiz
 */
public class MockContext extends Context {

  enum MethodToMock {
    ADD_CONTEXT_FOR("addContextFor"),
    ALL_EVENT_QUEUES("allEventQueues"),
    EVENT_QUEUE_FOR("eventQueueFor"),
    LOOKUP_EVENT_QUEUE_FOR("lookupEventQueueFor"),
    REMOVE_CONTEXT_FOR("removeContextFor"),
    ROOT_WINDOWS("rootWindows");
    
    final String methodName;

    private MethodToMock(String methodName) {
      this.methodName = methodName;
    }
  }
  
  private static final Map<MethodToMock, Method> METHODS_TO_MOCK = new HashMap<MethodToMock, Method>();
  
  static { populateMethodsToMock(); }

  private static void populateMethodsToMock() {
    try {
      mapMethod(ADD_CONTEXT_FOR, Component.class);
      mapMethod(ALL_EVENT_QUEUES);
      mapMethod(EVENT_QUEUE_FOR, Component.class);
      mapMethod(LOOKUP_EVENT_QUEUE_FOR, Component.class);
      mapMethod(REMOVE_CONTEXT_FOR, Component.class);
      mapMethod(ROOT_WINDOWS);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static void mapMethod(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    METHODS_TO_MOCK.put(method, method(method, parameterTypes));
  }
  
  private static Method method(MethodToMock method, Class<?>... parameterTypes) throws Exception {
    return MockContext.class.getMethod(method.methodName, parameterTypes);
  }

  public static Context mock(MethodToMock...methodsToMock) throws Exception {
    Class<MockContext> type = MockContext.class;
    Method[] methods = new Method[methodsToMock.length];
    for (int i = 0; i < methods.length; i++)
      methods[i] = METHODS_TO_MOCK.get(methodsToMock[i]);
    return createMock(type, methods);
  }

  @Override public void addContextFor(Component component) {}

  @Override public Collection<EventQueue> allEventQueues() { return null; }

  @Override public EventQueue eventQueueFor(Component c) { return null; }

  @Override public EventQueue lookupEventQueueFor(Component c) { return null; }

  @Override public void removeContextFor(Component component) { }

  @Override public Collection<Window> rootWindows() { return null; }
  
  public MockContext() {}
}
