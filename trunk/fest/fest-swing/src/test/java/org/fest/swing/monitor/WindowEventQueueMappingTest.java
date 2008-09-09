/*
 * Created on Mar 22, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.ToolkitStub;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link WindowEventQueueMapping}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowEventQueueMappingTest {

  private EventQueue eventQueue;
  private ToolkitStub toolkit;
  private MyWindow window;
  private WindowEventQueueMapping mapping;
  private Map<EventQueue, Map<Window, Boolean>> queueMap;

  @BeforeMethod public void setUp() {
    eventQueue = new EventQueue();
    toolkit = ToolkitStub.createNew(eventQueue);
    window = MyWindow.createNew(toolkit);
    mapping = new WindowEventQueueMapping();
    queueMap = mapping.queueMap;
  }

  @Test public void shouldAddQueueForToolkit() {
    mapping.addQueueFor(toolkit);
    assertThat(queueMap).hasSize(1)
                        .keySetIncludes(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).isEmpty();
  }

  @Test public void shouldAddQueueForWindow() {
    mapping.addQueueFor(window);
    assertThat(queueMap).hasSize(1)
                        .keySetIncludes(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).hasSize(1)
                             .keySetIncludes(window);
  }

  @Test(dependsOnMethods = "shouldAddQueueForWindow")
  public void shouldNotAddQueueForComponentThatIsNotWindow() {
    ComponentWithCustomEventQueue c = new ComponentWithCustomEventQueue(toolkit);
    mapping.addQueueFor(c);
    assertThat(queueMap).hasSize(1)
                        .keySetIncludes(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).isEmpty();
  }

  @Test(dependsOnMethods = "shouldAddQueueForWindow")
  public void shouldRemoveComponentFromMapping() {
    mapping.addQueueFor(window);
    mapping.removeMappingFor(window);
    assertThat(queueMap).hasSize(1)
                        .keySetIncludes(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).isEmpty();
  }

  @Test(dependsOnMethods = "shouldRemoveComponentFromMapping")
  public void shouldRemoveComponentFromAllMappings() {
    EventQueue anotherEventQueue = new EventQueue();
    Map<Window, Boolean> windowMapping = new HashMap<Window, Boolean>();
    windowMapping.put(window, true);
    queueMap.put(anotherEventQueue, windowMapping);
    mapping.removeMappingFor(window);
    assertThat(windowMapping).isEmpty();
  }

  @Test(dependsOnMethods = "shouldAddQueueForWindow")
  public void shouldReturnWindows() {
    TestWindow anotherWindow = MyWindow.createNew(toolkit);
    mapping.addQueueFor(window);
    mapping.addQueueFor(anotherWindow);
    Collection<Window> windows = mapping.windows();
    assertThat(windows).containsOnly(window, anotherWindow);
  }

  @Test(dependsOnMethods = "shouldAddQueueForWindow")
  public void shouldReturnEventQueues() {
    EventQueue anotherEventQueue = new EventQueue();
    ToolkitStub anotherToolkit = ToolkitStub.createNew(anotherEventQueue);
    TestWindow anotherWindow = MyWindow.createNew(anotherToolkit);
    mapping.addQueueFor(window);
    mapping.addQueueFor(anotherWindow);
    Collection<EventQueue> eventQueues = mapping.eventQueues();
    assertThat(eventQueues).containsOnly(eventQueue, anotherEventQueue);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew(final Toolkit toolkit) {
      return new MyWindow(toolkit);
    }
    
    private final Toolkit toolkit;

    private MyWindow(Toolkit toolkit) {
      super(WindowEventQueueMappingTest.class);
      this.toolkit = toolkit;
    }
    
    @Override public Toolkit getToolkit() {
      return toolkit;
    }
  }
}
