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

import javax.swing.JInternalFrame;

/**
 * Understands creation of <code>{@link JInternalFrame}</code>s.
 *
 * @author Alex Ruiz
 */
public final class JInternalFrames {

  private JInternalFrames() {}

  public static JInternalFrameFactory internalFrame() {
    return new JInternalFrameFactory();
  }
  
  public static class JInternalFrameFactory {
    String name;

    public JInternalFrameFactory withName(String newName) {
      name = newName;
      return this;
    }
    
    public JInternalFrame createNew() {
      JInternalFrame internalFrame = new JInternalFrame();
      internalFrame.setName(name);
      return internalFrame;
    }
  }
}