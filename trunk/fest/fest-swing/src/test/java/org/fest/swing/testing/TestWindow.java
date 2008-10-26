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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.task.FrameShowTask.packAndShow;
import static org.fest.swing.task.WindowDestroyTask.hideAndDispose;
import static org.fest.swing.testing.LookAndFeel.applySubstanceBusinessLookAndFeel;

/**
 * Understands the base window for all GUI tests.
 *
 * @author Alex Ruiz
 */
public class TestWindow extends JFrame {

  private static final long serialVersionUID = 1L;

  static final Point DEFAULT_WINDOW_LOCATION = new Point(100, 100);

  /**
   * Creates a new <code>{@link TestWindow}</code> and displays it on the screen. This method is executed in the event 
   * dispatch thread.
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class 
   * will be used as the title of the created window.
   * @return the created window.
   */
  public static TestWindow createAndShowNewWindow(final Class<?> testClass) {
    return execute(new GuiQuery<TestWindow>() {
      protected TestWindow executeInEDT() {
        TestWindow window = createInCurrentThread(testClass);
        TestWindow.displayInCurrentThread(window);
        return window;
      }
    });
  }

  /**
   * Creates a new <code>{@link TestWindow}</code>. This method is executed in the event dispatch thread.
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   * will be used as the title of the created window.
   * @return the created window.
   */
  public static TestWindow createNewWindow(final Class<?> testClass) {
    return execute(new GuiQuery<TestWindow>() {
      protected TestWindow executeInEDT() {
        return createInCurrentThread(testClass);
      }
    });
  }

  private static TestWindow createInCurrentThread(Class<?> testClass) {
    return new TestWindow(testClass);
  }

  /**
   * Creates a new </code>{@link TestWindow}</code>.
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   * will be used as the title of the created window.
   */
  protected TestWindow(Class<?> testClass) {
    setTitle(testClass.getSimpleName());
    setLayout(new FlowLayout());
    chooseLookAndFeel();
  }

  /**
   * Adds the given GUI components to this window. This method is <b>not</b> executed in the event dispatch thread.
   * @param components the components to add.
   */
  public void addComponents(Component...components) {
    for (Component c : components) add(c);
  }

  /**
   * Displays this window on the screen. This method is executed in the event dispatch thread.
   */
  public void display() {
    displayInEDT(this);
  }

  private static void displayInEDT(final TestWindow window) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        displayInCurrentThread(window);
      }
    });
  }

  /**
   * Displays the given window on the screen. This method is executed in the current thread where it is called.
   * @param window the window to display on the screen.
   */
  protected static void displayInCurrentThread(TestWindow window) {
    window.setLocation(DEFAULT_WINDOW_LOCATION);
    packAndShow(window);
  }

  /**
   * Displays this window on the screen using the given dimension as its preferred size. This method is executed in the 
   * event dispatch thread.
   * @param preferredSize the preferred size to set to this window before displaying it on the screen.
   */
  public void display(Dimension preferredSize) {
    displayInEDT(this, preferredSize);
  }

  private static void displayInEDT(final TestWindow window, final Dimension preferredSize) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        displayInCurrentThread(window, preferredSize);
      }
    });
  }

  /**
   * Displays the given window on the screen using the given dimension as its preferred size. This method is executed in 
   * the current thread where it is called.
   * @param window the window to display on the screen.
   * @param preferredSize the preferred size to set to the given window before displaying it on the screen.
   */
  protected static void displayInCurrentThread(TestWindow window, Dimension preferredSize) {
    window.setLocation(DEFAULT_WINDOW_LOCATION);
    packAndShow(window, preferredSize);
  }

  /**
   * Chooses the look and feel.
   */
  protected void chooseLookAndFeel() {
    applySubstanceBusinessLookAndFeel();
  }

  /**
   * Hides and disposes this window. This method is executed in the event dispatch thread.
   */
  public void destroy() {
    destroyInEDT(this);
  }

  private static void destroyInEDT(final TestWindow window) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        destroyInCurrentThread(window);
      }
    });
  }
  
  /**
   * Hides and disposes the given window. This method is executed in the current thread where it is called.
   * @param window the window to destroy.
   */
  protected static void destroyInCurrentThread(TestWindow window) {
    hideAndDispose(window);
  }
}
