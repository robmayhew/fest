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

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.task.FrameShowTask;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.task.DialogShowTask.packAndShow;
import static org.fest.swing.task.WindowDestroyTask.hideAndDispose;
import static org.fest.swing.testing.LookAndFeel.applySubstanceBusinessLookAndFeel;
import static org.fest.swing.testing.TestWindow.DEFAULT_WINDOW_LOCATION;

/**
 * Understands the base dialog for all GUI tests.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDialog extends JDialog {

  private static final Dimension DEFAULT_PREFERRED_SIZE = new Dimension(200, 100);

  private static final long serialVersionUID = 1L;

  static final Point DEFAULT_DIALOG_LOCATION = new Point(200, 200);

  /**
   * Creates a new <code>{@link TestDialog}</code> and displays it on the screen with the given frame as its owner. This
   * constructor will set the title of the dialog to be the same as its owner. This method is executed in the event
   * dispatch thread.
   * @param owner the owner of the dialog to create.
   * @return the created window.
   */
  public static TestDialog createAndDisplayInEDT(final Frame owner) {
    return execute(new GuiQuery<TestDialog>() {
      protected TestDialog executeInEDT() {
        TestDialog dialog = createInCurrentThread(owner);
        TestDialog.displayInCurrentThread(dialog, new Dimension(DEFAULT_PREFERRED_SIZE));
        return dialog;
      }
    });
  }

  /**
   * Creates a new <code>{@link TestDialog}</code> with the given frame as its owner. This constructor will set the title
   * of the dialog to be the same as its owner. This method is executed in the event dispatch thread.
   * @param owner the owner of the dialog to create.
   * @return the created window.
   */
  public static TestDialog createInEDT(final Frame owner) {
    return execute(new GuiQuery<TestDialog>() {
      protected TestDialog executeInEDT() {
        return createInCurrentThread(owner);
      }
    });
  }

  private static TestDialog createInCurrentThread(Frame owner) {
    return new TestDialog(owner);
  }

  /**
   * Creates a new </code>{@link TestDialog}</code> with the given frame as its owner. This constructor will set the 
   * title of the dialog to be the same as its owner.
   * @param owner the owner of the dialog to create.
   */
  protected TestDialog(Frame owner) {
    super(owner);
    setTitle(owner.getTitle());
    setLayout(new FlowLayout());
  }

  /**
   * Adds the given GUI components to this dialog. This method is <b>not</b> executed in the event dispatch thread.
   * @param components the components to add.
   */
  public void addComponents(Component...components) {
    for (Component c : components) add(c);
  }

  /**
   * Displays this dialog on the screen. This method is executed in the event dispatch thread.
   */
  public void display() {
    display(DEFAULT_PREFERRED_SIZE);
  }

  /**
   * Displays this dialog on the screen using the given dimension as its preferred size. This method is executed in the 
   * event dispatch thread.
   * @param preferredSize the preferred size to set to this dialog before displaying it on the screen.
   */
  public void display(final Dimension preferredSize) {
    displayInEDT(this, preferredSize);
  }
  
  private static void displayInEDT(final TestDialog dialog, final Dimension preferredSize) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        displayInCurrentThread(dialog, preferredSize);
      }
    });
  }
  
  /**
   * Displays the given dialog on the screen using the given dimension as its preferred size. This method is executed in 
   * the current thread where it is called.
   * @param dialog the dialog to display on the screen.
   * @param preferredSize the preferred size to set to the given dialog before displaying it on the screen.
   */
  protected static void displayInCurrentThread(TestDialog dialog, Dimension preferredSize) {
    showOwnerIfPossible(dialog.getOwner());
    dialog.setLocation(DEFAULT_DIALOG_LOCATION);
    packAndShow(dialog, preferredSize);
  }

  private static void showOwnerIfPossible(Window owner) {
    if (!(owner instanceof Frame)) return;
    Frame dialogOwner = (Frame)owner;
    dialogOwner.setLocation(DEFAULT_WINDOW_LOCATION);
    FrameShowTask.packAndShow(dialogOwner);
  }

  /**
   * Chooses the look and feel.
   */
  protected void chooseLookAndFeel() {
    applySubstanceBusinessLookAndFeel();
  }

  /**
   * Hides and disposes this dialog. This method is executed in the event dispatch thread.
   */
  public void destroy() {
    destroyInEDT(this);
  }

  private static void destroyInEDT(final TestDialog dialog) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        destroyInCurrentThread(dialog);
      }
    });
  }
  
  /**
   * Hides and disposes the given dialog. This method is executed in the current thread where it is called.
   * @param dialog the dialog to destroy.
   */
  protected static void destroyInCurrentThread(TestDialog dialog) {
    hideAndDispose(dialog);
  }
}
