/*
 * Created on Oct 25, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.fest.swing.TestFrame;

/**
 * Understands an MDI frame.
 *
 * @author Alex Ruiz
 */
public class MDIFrame extends TestFrame {

  private static final long serialVersionUID = 1L;

  public static MDIFrame showInTest(Class testClass) {
    MDIFrame f = new MDIFrame(testClass);
    f.display();
    return f;
  }
  
  private JDesktopPane desktop;
  private JInternalFrame internalFrame;

  public MDIFrame(Class testClass) {
    super(testClass);
  }

  @Override protected void beforeDisplayed() {
    desktop = new JDesktopPane();
    createInternalFrame();
    desktop.add(internalFrame);
    setContentPane(desktop);
  }

  private void createInternalFrame() {
    internalFrame = new JInternalFrame("Internal Frame");
    internalFrame.setIconifiable(true);
    internalFrame.setPreferredSize(new Dimension(200, 100));
    internalFrame.setVisible(true);
  }

  @Override protected void chooseLookAndFeel() {}

  public JDesktopPane desktop() { return desktop; }
  public JInternalFrame internalFrame() { return internalFrame; }
  
}
