/*
 * Created on Dec 17, 2007
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
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;

import org.fest.swing.testing.TestFrame;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JInternalFrameFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixtureTest extends WindowLikeFixtureTestCase<JInternalFrame> {

  private TestFrame mainFrame;
  private JInternalFrameFixture fixture;
  private MyInternalFrame target;
  private JDesktopPane desktop;

  protected JInternalFrame createTarget() {
    target = new MyInternalFrame();
    return target;
  }
  
  protected ComponentFixture<JInternalFrame> createFixture() {
    showMainFrame();
    fixture = new JInternalFrameFixture(robot(), "target");
    return fixture;
  }

  private void showMainFrame() {
    mainFrame = new TestFrame(getClass());
    desktop = new JDesktopPane();
    add(target);
    mainFrame.setContentPane(desktop);
    robot().showWindow(mainFrame, new Dimension(400, 300));
  }

  @Test public void shouldIconifyAndDeiconifyInternalFrame() {
    fixture.iconify();
    assertThat(target.isIcon()).isTrue();
    fixture.deiconify();
    assertThat(target.isIcon()).isFalse();
  }
  
  @Test public void shouldMaximizeInternalFrame() {
    fixture.maximize();
    assertThat(target.isMaximum()).isTrue();
  }

  @Test(dependsOnMethods = "shouldMaximizeInternalFrame") 
  public void shouldNormalizeInternalFrame() {
    fixture.maximize();
    fixture.normalize();
    assertThat(target.isIcon()).isFalse();
    assertThat(target.isMaximum()).isFalse();
  }
  
  @Test public void shouldMoveInternalFrameToFront() {
    add(new MyInternalFrame());
    fixture.moveToFront();
    assertThat(desktop.getComponentZOrder(target)).isEqualTo(0);
  }
  
  @Test(dependsOnMethods = "shouldMoveInternalFrameToFront")
  public void shouldMoveInternalFrameToBack() {
    add(new MyInternalFrame());
    fixture.moveToFront();
    fixture.moveToBack();
    assertThat(desktop.getComponentZOrder(target)).isEqualTo(1);
  }

  private void add(final MyInternalFrame frame) {
    robot().invokeAndWait(new Runnable() {
      public void run() {
        frame.setVisible(true);
        desktop.add(frame);
        frame.toFront();        
      }
    });
  }

  protected Component target() {
    return target;
  }
  
  private static class MyInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private static int index;
    
    MyInternalFrame() {
      super(concat("Internal Frame ", String.valueOf(index++)), true, true, true, true);
      setName("target");
      setSize(200, 100);
    }
  }
}
