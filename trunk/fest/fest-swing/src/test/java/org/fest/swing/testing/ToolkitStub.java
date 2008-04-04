/*
 * Created on Mar 22, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.testing;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

import org.easymock.classextension.EasyMock;

/**
 * Understands a stub of <code>{@link Toolkit}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class ToolkitStub extends Toolkit {

  private static final List<String> METHODS_NOT_TO_MOCK = new ArrayList<String>();

  private Map<AWTEventListener, Long> eventListeners;

  static {
    METHODS_NOT_TO_MOCK.add("createMock");
    METHODS_NOT_TO_MOCK.add("eventQueue");
    METHODS_NOT_TO_MOCK.add("eventListeners");
    METHODS_NOT_TO_MOCK.add("addAWTEventListener");
    METHODS_NOT_TO_MOCK.add("removeAWTEventListener");
    METHODS_NOT_TO_MOCK.add("eventListenersUnderEventMask");
    METHODS_NOT_TO_MOCK.add("contains");
    METHODS_NOT_TO_MOCK.add("getSystemEventQueueImpl");
    METHODS_NOT_TO_MOCK.add("getScreenInsets");
  }

  private EventQueue eventQueue;

  public static ToolkitStub createNew() {
    return createNew(new EventQueue());
  }

  public static ToolkitStub createNew(EventQueue eventQueue) {
    Map<String, Method> methodMap = new HashMap<String, Method>();
    for(Method method : ToolkitStub.class.getMethods()) {
      String name = method.getName();
      if (!METHODS_NOT_TO_MOCK.contains(name))
        methodMap.put(name, method);
    }
    Collection<Method> methods = methodMap.values();
    Method[] methodsToMock = methods.toArray(new Method[methods.size()]);
    ToolkitStub stub =  EasyMock.createMock(ToolkitStub.class, methodsToMock);
    stub.eventQueue(eventQueue);
    stub.eventListeners = new HashMap<AWTEventListener, Long>();
    return stub;
  }

  public ToolkitStub() {}

  public void eventQueue(EventQueue eventQueue) {
    this.eventQueue = eventQueue;
  }

  @Override public void addAWTEventListener(AWTEventListener listener, long eventMask) {
    eventListeners().put(listener, eventMask);
  }

  @Override public void removeAWTEventListener(AWTEventListener listener) {
    eventListeners().remove(listener);
  }

  public <T extends AWTEventListener> List<T> eventListenersUnderEventMask(long eventMask, Class<T> type) {
    List<T> listeners = new ArrayList<T>();
    for (AWTEventListener listener : eventListeners().keySet()) {
      if (!type.isInstance(listener)) continue;
      if (eventListeners().get(listener).longValue() != eventMask) continue;
      listeners.add(type.cast(listener));
    }
    return listeners;
  }

  public boolean contains(AWTEventListener listener, long eventMask) {
    if (!eventListeners.containsKey(listener)) return false;
    long storedMask = eventListeners.get(listener);
    return storedMask == eventMask;
  }

  protected EventQueue getSystemEventQueueImpl() {
    return eventQueue;
  }

  private Map<AWTEventListener, Long> eventListeners() {
    return eventListeners;
  }

  @Override public Insets getScreenInsets(GraphicsConfiguration gc) throws HeadlessException {
    return new Insets(0, 0, 0, 0);
  }
}
