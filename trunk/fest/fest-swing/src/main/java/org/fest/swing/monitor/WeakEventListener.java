/*
 * Created on Oct 8, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.lang.ref.WeakReference;

import static java.awt.Toolkit.getDefaultToolkit;
import static org.fest.util.Objects.areEqual;

/**
 * Understands an event listener that wraps a given <code>{@link AWTEventListener}</code> and:
 * <ul>
 * <li>attaches itself to the default toolkit</li>
 * <li>dispatches any given event to the wrapped listener</li>
 * <li>removes itself from the default toolkit when the wrapped listener gets garbage-collected</li>
 * </ul>
 * <p>
 * Adapted from <code>abbot.util.WeakAWTEventListener</code> from 
 * <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class WeakEventListener implements AWTEventListener {

  private final WeakReference<AWTEventListener> listenerReference;

  static WeakEventListener attachAsWeakEventListener(AWTEventListener listener, long eventMask) {
    WeakEventListener l = new WeakEventListener(listener);
    getDefaultToolkit().addAWTEventListener(l, eventMask);
    return l;
  }
  
  WeakEventListener(AWTEventListener listener) {
    listenerReference = new WeakReference<AWTEventListener>(listener);
  }

  /**
   * Dispatches the given event to the wrapped event listener. If the wrapped listener is <code>null</code> 
   * (garbage-collected,) this listener will remove itself from the default toolkit.
   * @param e the event dispatched in the AWT.
   */
  public void eventDispatched(AWTEvent e) {
    AWTEventListener listener = listenerReference.get();
    if (listener == null) {
      dispose();
      return;
    }
    listener.eventDispatched(e);
  }
  
  void dispose() {
    getDefaultToolkit().removeAWTEventListener(this);
  }

  /**
   * Removes the wrapped listener from the <code>{@link WeakReference}</code> (to simulate garbage collection). This 
   * method should be used only for testing purposes.
   */
  void removeListener() {
    listenerReference.clear();
  }

  @Override public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    if (!(obj instanceof WeakEventListener)) return false;
    WeakEventListener other = (WeakEventListener)obj;
    return areEqual(listenerReference.get(), other.listenerReference.get());
  }

  @Override public int hashCode() {
    return 1;
  }
}
