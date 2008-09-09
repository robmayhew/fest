/*
 * Created on Aug 28, 2008
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
package org.fest.swing.factory;

import javax.swing.JToolBar;

import static javax.swing.SwingConstants.HORIZONTAL;

/**
 * Understands creation of <code>{@link JToolBar}</code>s.
 *
 * @author Alex Ruiz
 */
public final class JToolBars {

  private JToolBars() {}

  public static JToolBarFactory toolBar() {
    return new JToolBarFactory();
  }
  
  public static class JToolBarFactory {
    int orientation = HORIZONTAL;
    String name;

    public JToolBarFactory withOrientation(int newOrientation) {
      orientation = newOrientation;
      return this;
    }
    
    public JToolBarFactory withName(String newName) {
      name = newName;
      return this;
    }
    
    public JToolBar createNew() {
      JToolBar toolBar = new JToolBar();
      toolBar.setOrientation(orientation);
      toolBar.setName(name);
      return toolBar;
    }
  }
}