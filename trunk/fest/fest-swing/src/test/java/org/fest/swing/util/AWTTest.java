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

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.task.ComponentSetSizeTask.setSizeTask;
import static org.fest.swing.testing.TestWindow.showNewInTest;

/**
 * Tests for <code>{@link AWT}</code>.
 *
 * @author Alex Ruiz
 */
public class AWTTest {

  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

  @Test public void shouldReturnCenterPosition() {
    Component c = textField().createInEDT();
    Dimension size = new Dimension(80, 60);
    execute(setSizeTask(c, size));
    assertThat(sizeOf(c)).isEqualTo(size);
    Point center = AWT.centerOf(c);
    assertThat(center.x).isEqualTo(40);
    assertThat(center.y).isEqualTo(30);
  }

  @Test public void shouldReturnInsetsFromContainer() {
    TestWindow frame = showNewInTest(getClass());
    Insets insets = AWT.insetsFrom(frame);
    assertThat(insets).isEqualTo(insetsOf(frame));
    frame.destroy();
  }

  @Test public void shouldReturnEmptyInsetsIfExceptionThrown() {
    Insets insets = AWT.insetsFrom(null);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  @Test public void shouldReturnEmptyInsetsIfContainerInsetsIsNull() {
    TestWindow frame = new TestWindow(getClass()) {
      private static final long serialVersionUID = 1L;
      @Override public Insets getInsets() {
        return null;
      }
    };
    Insets insets = AWT.insetsFrom(frame);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  @Test public void shouldReturnFalseIfComponentIsNotAppletViewer() {
    assertThat(AWT.isAppletViewer(new JTextField())).isFalse();
  }

  @Test public void shouldReturnFalseIfComponentIsNull() {
    assertThat(AWT.isAppletViewer(null)).isFalse();
  }

  @Test public void shouldReturnTrueIfComponentIsSharedInvisibleFrame() {
    JDialog dialog = dialogWithSharedInvisibleFrameAsOwner();
    assertThat(AWT.isSharedInvisibleFrame(dialog.getOwner())).isTrue();
  }

  private JDialog dialogWithSharedInvisibleFrameAsOwner() {
    return new JDialog((Frame)null);
  }

  @Test public void shouldReturnFalseIfComponentIsNotSharedInvisibleFrame() {
    assertThat(AWT.isSharedInvisibleFrame(new JTextField())).isFalse();
  }

  @Test public void shouldReturnFalseIfComponentIsNotSharedInvisibleFrameAndNull() {
    assertThat(AWT.isSharedInvisibleFrame(null)).isFalse();
  }

  @Test public void shouldReturnNullAsNameIfComponentIsNull() {
    assertThat(AWT.quoteNameOf(null)).isNull();
  }

  @Test public void shouldReturnComponentNameInQuotes() {
    JTextField textField = new JTextField();
    textField.setName("firstName");
    assertThat(AWT.quoteNameOf(textField)).isEqualTo("'firstName'");
  }
}
