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

import org.fest.swing.core.Condition;
import org.fest.swing.task.WindowDestroyTask;

import static javax.swing.SwingUtilities.invokeLater;

import static org.fest.swing.core.Pause.pause;

/**
 * Understands the base window for all GUI tests.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDialog extends JDialog {

  private static final long serialVersionUID = 1L;

  public static TestDialog showInTest(Frame owner) {
    TestDialog dialog = create(owner);
    dialog.display();
    return dialog;
  }

  public static TestDialog createNew(Frame owner) {
    return create(owner);
  }

  private static TestDialog create(Frame owner) {
    return new TestDialog(owner);
  }

  protected TestDialog(Frame owner) {
    super(owner);
    setTitle(owner.getTitle());
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
    showOwnerIfPossible(owner);
    invokeLater(new Runnable() {
      public void run() {
        beforeDisplayed();
        setPreferredSize(size);
        pack();
        setLocation(100, 100);
        setVisible(true);
      }
    });
    pause(new Condition("dialog is displayed") {
      public boolean test() {
        return TestDialog.this.isShowing();
      }
    });
  }

  private void showOwnerIfPossible(Window owner) {
    if (!(owner instanceof TestWindow) || owner.isShowing()) return;
    ((TestWindow)owner).display();
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
    WindowDestroyTask.destroy(this);
  }
}
