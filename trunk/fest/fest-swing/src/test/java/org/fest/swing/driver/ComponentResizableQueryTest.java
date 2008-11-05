/*
 * Created on Nov 5, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.edt.CheckThreadViolationRepaintManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JButtons.button;
import static org.fest.swing.factory.JDialogs.dialog;
import static org.fest.swing.factory.JFrames.frame;
import static org.fest.swing.factory.JInternalFrames.internalFrame;

/**
 * Tests for <code>{@link ComponentResizableQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class ComponentResizableQueryTest {

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }
  
  public void shouldReturnIsResizableIfFrameIsResizable() {
    JFrame frame = frame().createNew();
    assertThat(ComponentResizableQuery.isResizable(frame)).isTrue();
  }

  public void shouldReturnIsNotResizableIfFrameIsNotResizable() {
    JFrame frame = frame().resizable(false).createNew();
    assertThat(ComponentResizableQuery.isResizable(frame)).isFalse();
  }

  public void shouldReturnIsResizableIfDialogIsResizable() {
    JDialog dialog = dialog().createNew();
    assertThat(ComponentResizableQuery.isResizable(dialog)).isTrue();
  }

  public void shouldReturnIsNotResizableIfDialogIsNotResizable() {
    JDialog dialog = dialog().resizable(false).createNew();
    assertThat(ComponentResizableQuery.isResizable(dialog)).isFalse();
  }
  
  public void shouldReturnIsResizableIfJInternalFrameIsResizable() {
    JInternalFrame internalFrame = internalFrame().resizable(true).createNew();
    assertThat(ComponentResizableQuery.isResizable(internalFrame)).isTrue();
  }

  public void shouldReturnIsNotResizableIfJInternalFrameIsNotResizable() {
    JInternalFrame internalFrame = internalFrame().resizable(false).createNew();
    assertThat(ComponentResizableQuery.isResizable(internalFrame)).isFalse();
  }
  
  public void shouldReturnIsNotResizableIfComponentIsNotWindow() {
    assertThat(ComponentResizableQuery.isResizable(button().createNew())).isFalse();
  }
}
