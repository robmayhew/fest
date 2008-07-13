/*
 * Created on Jul 12, 2008
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
package org.fest.swing.applet;

import java.applet.Applet;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Understands a <code>{@link WindowListener}</code> that stops and destroys an <code>{@link Applet}</code> when a 
 * window is closing.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
class StopAppletWindowListener extends WindowAdapter {
  private final Applet applet;

  StopAppletWindowListener(Applet applet) {
    this.applet = applet;
  }

  @Override public void windowClosing(WindowEvent e) {
    applet.stop();
    applet.destroy();
  }
}