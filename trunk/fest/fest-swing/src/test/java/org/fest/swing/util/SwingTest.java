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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.util;

import java.awt.Frame;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.testing.TestFrame.showInTest;

import org.fest.swing.testing.TestFrame;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Swing}</code>.
 *
 * @author Alex Ruiz 
 */
public class SwingTest {

  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0); 
  
  @Test public void shouldReturnInsetsFromContainer() {
    TestFrame frame = showInTest(getClass());
    Insets insets = Swing.insetsFrom(frame);
    assertThat(insets).isEqualTo(frame.getInsets());
    frame.destroy();
  }
  
  @Test public void shouldReturnEmptyInsetsIfExceptionThrown() {
    Insets insets = Swing.insetsFrom(null);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  @Test public void shouldReturnEmptyInsetsIfContainerInsetsIsNull() {
    TestFrame frame = new TestFrame(getClass()) {
      private static final long serialVersionUID = 1L;
      @Override public Insets getInsets() {
        return null;
      }
    };
    Insets insets = Swing.insetsFrom(frame);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }
  
  @Test public void shouldReturnFalseIfComponentIsNotAppletViewer() {
    assertThat(Swing.isAppletViewer(new JTextField())).isFalse();
  }

  @Test public void shouldReturnFalseIfComponentIsNull() {
    assertThat(Swing.isAppletViewer(null)).isFalse();
  }
  
  @Test public void shouldReturnTrueIfComponentIsSharedInvisibleFrame() {
    JDialog dialog = dialogWithSharedInvisibleFrameAsOwner(); 
    assertThat(Swing.isSharedInvisibleFrame(dialog.getOwner())).isTrue();
  }

  private JDialog dialogWithSharedInvisibleFrameAsOwner() {
    return new JDialog((Frame)null);
  }
  
  @Test public void shouldReturnFalseIfComponentIsNotSharedInvisibleFrame() {
    assertThat(Swing.isSharedInvisibleFrame(new JTextField())).isFalse();
  }
  
  @Test public void shouldReturnFalseIfComponentIsNotSharedInvisibleFrameAndNull() {
    assertThat(Swing.isSharedInvisibleFrame(null)).isFalse();
  }

  @Test public void shouldReturnNullAsNameIfComponentIsNull() {
    assertThat(Swing.quoteNameOf(null)).isNull();
  }

  @Test public void shouldReturnComponentNameInQuotes() {
    JTextField textField = new JTextField();
    textField.setName("firstName");
    assertThat(Swing.quoteNameOf(textField)).isEqualTo("'firstName'");
  }
}
