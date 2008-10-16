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
import javax.swing.UIManager;

import org.fest.swing.task.WindowDestroyTask;
import org.fest.swing.timing.Condition;

import static javax.swing.SwingUtilities.invokeLater;

import static org.fest.swing.testing.TestWindow.WindowIsDisplayedCondition.untilIsShowing;
import static org.fest.swing.timing.Pause.pause;

/**
 * Understands the base window for all GUI tests.
 *
 * @author Alex Ruiz
 */
public class TestWindow extends JFrame {


  private static final long serialVersionUID = 1L;

  static final Point DEFAULT_LOCATION = new Point(100, 100);

  public static TestWindow showNewInTest(final Class<?> testClass) {
    TestWindow window = create(testClass);
    window.display();
    return window;
  }

  public static TestWindow createNew(final Class<?> testClass) {
    return create(testClass);
  }

  private static TestWindow create(final Class<?> testClass) {
    return new TestWindow(testClass);
  }

  protected TestWindow(Class<?> testClass) {
    setTitle(testClass.getSimpleName());
    setLayout(new FlowLayout());
    chooseLookAndFeel();
  }

  public void addComponents(Component...components) {
    for (Component c : components) add(c);
  }

  public void display() {
    invokeLater(new Runnable() {
      public void run() {
        beforeDisplayed();
        pack();
        setLocation(DEFAULT_LOCATION);
        setVisible(true);
      }
    });
    pause(untilIsShowing(this));
  }

  public void display(final Dimension size) {
    invokeLater(new Runnable() {
      public void run() {
        beforeDisplayed();
        setPreferredSize(size);
        pack();
        setLocation(DEFAULT_LOCATION);
        setVisible(true);
      }
    });
    pause(untilIsShowing(this));
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

  static class WindowIsDisplayedCondition extends Condition {
    private TestWindow window;

    static Condition untilIsShowing(TestWindow window) {
      return new WindowIsDisplayedCondition(window);
    }

    private WindowIsDisplayedCondition(TestWindow window) {
      super("Window is displayed");
      this.window = window;
    }

    public boolean test() {
      return window.isShowing();
    }

    @Override protected void done() {
      window = null;
    }
  }
}
