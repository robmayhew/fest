/*
 * Created on Sep 11, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.testing;

import java.awt.*;

import javax.swing.JDialog;
import javax.swing.UIManager;

import org.fest.swing.core.GuiQuery;

/**
 * Understands the base window for all GUI tests.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDialog extends JDialog {

  private static final long serialVersionUID = 1L;

  public static TestDialog showInTest(final Frame owner) {
    return new GuiQuery<TestDialog>() {
      protected TestDialog executeInEDT() {
        TestDialog d = new TestDialog(owner);
        d.display();
        return d;
      }
    }.run();
  }
  
  public TestDialog(Frame owner) {
    super(owner);
    setLayout(new FlowLayout());
  }

  public void addComponents(Component...components) {
    for (Component c : components) add(c);
  }
  
  public void display() {
    display(new Dimension(400, 200));
  }
  
  public void display(final Dimension size) {
    Window owner = getOwner();
    if (owner instanceof TestWindow && !owner.isShowing()) ((TestWindow)owner).display();
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        beforeDisplayed();
        chooseLookAndFeel();
        setPreferredSize(size);
        pack();
        setVisible(true);
        return null;
      }
    }.run();
  }
  
  protected void beforeDisplayed() {}

  protected void chooseLookAndFeel() {
    lookNative();
  }
  
  private void lookNative() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
      ignored.printStackTrace();
    }
  }
  
  public void destroy() {
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        setVisible(false);
        dispose();
        return null;
      }
    }.run();
  }
}
