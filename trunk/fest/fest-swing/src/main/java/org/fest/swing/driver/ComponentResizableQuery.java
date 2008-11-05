/*
 * Created on Nov 5, 2008
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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Understands an action that indicates whether it is possible for the user to resize the given component.
 *
 * @author Alex Ruiz
 */
final class ComponentResizableQuery {

  @RunsInCurrentThread
  static boolean isResizable(Component c) {
    if (c instanceof Dialog) return ((Dialog)c).isResizable();
    if (c instanceof Frame) return ((Frame)c).isResizable();
    if (c instanceof JInternalFrame) return ((JInternalFrame)c).isResizable();
    return false;
  }
  
  private ComponentResizableQuery() {}
}
