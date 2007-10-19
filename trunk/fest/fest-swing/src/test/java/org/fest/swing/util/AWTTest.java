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

import java.awt.Insets;

import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.TestFrame;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link AWT}</code>.
 *
 * @author Alex Ruiz 
 */
public class AWTTest {

  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0); 
  
  @Test public void shouldReturnInsetsFromContainer() {
    TestFrame frame = new TestFrame(getClass());
    frame.beVisible();
    Insets insets = AWT.insetsFrom(frame);
    assertThat(insets).isEqualTo(frame.getInsets());
    frame.beDisposed();
  }
  
  @Test public void shouldReturnEmptyInsetsIfExceptionThrown() {
    Insets insets = AWT.insetsFrom(null);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  @Test public void shouldReturnEmptyInsetsIfContainerInsetsIsNull() {
    TestFrame frame = new TestFrame(getClass()) {
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
}
