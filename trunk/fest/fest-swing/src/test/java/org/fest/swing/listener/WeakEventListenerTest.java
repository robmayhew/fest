/*
 * Created on Oct 8, 2007
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
package org.fest.swing.listener;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static java.awt.AWTEvent.WINDOW_EVENT_MASK;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.ToolkitUtils.isListenerInToolkit;

/**
 * Tests for <code>{@link WeakEventListener}</code>.
 *
 * @author Alex Ruiz
 */
public class WeakEventListenerTest {

  private WeakEventListener listener;

  private static final long EVENT_MASK = WINDOW_EVENT_MASK;
  private WrappedEventListener wrapped;
  
  @BeforeTest public void setUp() {
    wrapped = new WrappedEventListener();
  }

  @Test public void shouldWrapListenerAndAddItselfToToolkitWithGivenMask() {
    listener = WeakEventListener.attachAsWeakEventListener(wrapped, EVENT_MASK);
    assertThat(toolkitHasListenerUnderTest()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldWrapListenerAndAddItselfToToolkitWithGivenMask")
  public void shouldDispatchEventsToWrappedListener() {
    AWTEvent event = awtEvent();
    listener = WeakEventListener.attachAsWeakEventListener(wrapped, EVENT_MASK);
    listener.eventDispatched(event);
    assertThat(wrapped.dispatchedEvent).isSameAs(event);
  }

  @Test(dependsOnMethods = "shouldWrapListenerAndAddItselfToToolkitWithGivenMask")
  public void shouldRemoveItselfFromToolkitIfWrappedListenerIsNull() {
    listener = WeakEventListener.attachAsWeakEventListener(wrapped, EVENT_MASK);
    listener.removeListener();
    listener.eventDispatched(awtEvent());
    assertThat(toolkitHasListenerUnderTest()).isFalse();
  }
  
  private AWTEvent awtEvent() {
    return new AWTEvent(new Object(), 0) {
      private static final long serialVersionUID = 1L;
    };
  }
  
  private boolean toolkitHasListenerUnderTest() {
    return isListenerInToolkit(listener, EVENT_MASK);
  }
  
  private static class WrappedEventListener implements AWTEventListener {
    AWTEvent dispatchedEvent;
    
    public void eventDispatched(AWTEvent event) {
      dispatchedEvent = event;
    }
  }
}
