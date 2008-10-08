/*
 * Created on Oct 12, 2007
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
package org.fest.swing.util;

import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JTextField;

import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.task.ComponentSetSizeTask.setComponentSize;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AWT}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class AWTTest {

  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

  public void shouldReturnCenterPosition() {
    Component c = new JTextField();
    Dimension size = new Dimension(80, 60);
    setComponentSize(c, size);
    assertThat(sizeOf(c)).isEqualTo(size);
    Point center = AWT.centerOf(c);
    assertThat(center.x).isEqualTo(40);
    assertThat(center.y).isEqualTo(30);
  }

  @Test(groups = GUI)
  public void shouldReturnInsetsFromContainer() {
    Robot robot = robotWithNewAwtHierarchy();
    TestWindow window = TestWindow.createNew(getClass());
    try {
      robot.showWindow(window, new Dimension(500, 300));
      Insets insets = AWT.insetsFrom(window);
      assertThat(insets).isEqualTo(insetsOf(window));
    } finally {
      robot.cleanUp();
    }
  }

  public void shouldReturnEmptyInsetsIfExceptionThrown() {
    Insets insets = AWT.insetsFrom(null);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  public void shouldReturnEmptyInsetsIfContainerInsetsIsNull() {
    TestWindow window = WindowWithNullInsets.createNew();
    Insets insets = AWT.insetsFrom(window);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  private static class WindowWithNullInsets extends TestWindow {
    private static final long serialVersionUID = 1L;

    static WindowWithNullInsets createNew() {
      return new WindowWithNullInsets();
    }

    private WindowWithNullInsets() {
      super(AWTTest.class);
    }

    @Override public Insets getInsets() {
      return null;
    }
  }

  public void shouldReturnFalseIfComponentIsNotAppletViewer() {
    assertThat(AWT.isAppletViewer(textField().createNew())).isFalse();
  }

  public void shouldReturnFalseIfComponentIsNull() {
    assertThat(AWT.isAppletViewer(null)).isFalse();
  }

  public void shouldReturnTrueIfComponentIsSharedInvisibleFrame() {
    JDialog dialog = new JDialog((Frame)null);
    assertThat(AWT.isSharedInvisibleFrame(dialog.getOwner())).isTrue();
  }

  public void shouldReturnFalseIfComponentIsNotSharedInvisibleFrame() {
    assertThat(AWT.isSharedInvisibleFrame(new JTextField())).isFalse();
  }

  public void shouldReturnFalseIfComponentIsNotSharedInvisibleFrameAndNull() {
    assertThat(AWT.isSharedInvisibleFrame(null)).isFalse();
  }

  public void shouldReturnNullAsNameIfComponentIsNull() {
    assertThat(AWT.quoteNameOf(null)).isNull();
  }

  public void shouldReturnComponentNameInQuotes() {
    JTextField textField = new JTextField();
    textField.setName("firstName");
    assertThat(AWT.quoteNameOf(textField)).isEqualTo("'firstName'");
  }
}
