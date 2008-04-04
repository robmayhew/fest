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

import static org.fest.assertions.Assertions.assertThat;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.ToolkitStub;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowEventQueueMapping}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowEventQueueMappingTest {

  private EventQueue eventQueue;
  private ToolkitStub toolkit;
  private TestFrame frame;
  private WindowEventQueueMapping mapping;
  private Map<EventQueue, Map<Window, Boolean>> queueMap;

  @BeforeMethod public void setUp() {
    eventQueue = new EventQueue();
    toolkit = ToolkitStub.createNew(eventQueue);
    frame = testFrame(toolkit);
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
    mapping.addQueueFor(frame);
    assertThat(queueMap).hasSize(1)
                        .keySetIncludes(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).hasSize(1)
                             .keySetIncludes(frame);
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
    mapping.addQueueFor(frame);
    mapping.removeMappingFor(frame);
    assertThat(queueMap).hasSize(1)
                        .keySetIncludes(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).isEmpty();
  }

  @Test(dependsOnMethods = "shouldRemoveComponentFromMapping")
  public void shouldRemoveComponentFromAllMappings() {
    EventQueue anotherEventQueue = new EventQueue();
    Map<Window, Boolean> windowMapping = new HashMap<Window, Boolean>();
    windowMapping.put(frame, true);
    queueMap.put(anotherEventQueue, windowMapping);
    mapping.removeMappingFor(frame);
    assertThat(windowMapping).isEmpty();
  }

  @Test(dependsOnMethods = "shouldAddQueueForWindow")
  public void shouldReturnWindows() {
    TestFrame anotherFrame = testFrame(toolkit);
    mapping.addQueueFor(frame);
    mapping.addQueueFor(anotherFrame);
    Collection<Window> windows = mapping.windows();
    assertThat(windows).containsOnly(frame, anotherFrame);
  }

  @Test(dependsOnMethods = "shouldAddQueueForWindow")
  public void shouldReturnEventQueues() {
    EventQueue anotherEventQueue = new EventQueue();
    ToolkitStub anotherToolkit = ToolkitStub.createNew(anotherEventQueue);
    TestFrame anotherFrame = testFrame(anotherToolkit);
    mapping.addQueueFor(frame);
    mapping.addQueueFor(anotherFrame);
    Collection<EventQueue> eventQueues = mapping.eventQueues();
    assertThat(eventQueues).containsOnly(eventQueue, anotherEventQueue);
  }

  private TestFrame testFrame(final Toolkit toolkit) {
    return new TestFrame(WindowEventQueueMappingTest.class) {
      private static final long serialVersionUID = 1L;

      @Override public Toolkit getToolkit() {
        return toolkit;
      }
    };
  }
}
