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

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;

import org.fest.swing.testing.TestFrame;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JInternalFrameFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixtureTest extends ComponentFixtureTestCase<JInternalFrame> {

  private TestFrame mainFrame;
  private JInternalFrameFixture fixture;
  private MyInternalFrame target;

  @Test public void shouldCloseInternalFrame() {
    fixture.close();
    assertThat(target.isClosed()).isTrue();
  }
  
  @Test public final void shouldPassIfInternalFrameHasMatchingSize() {
    fixture.requireSize(target.getSize());
  }
  
  @Test(dependsOnMethods = "shouldPassIfInternalFrameHasMatchingSize")
  public final void shouldFailIfInternalFrameHasNotMatchingSize() {
    FluentDimension wrongSize = internalFrameSize().addToWidth(50).addToHeight(30);
    try {
      fixture.requireSize(wrongSize);
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      Dimension windowSize = target.getSize();
      assertThat(errorMessage).contains(property("size"), expected(wrongSize), actual(windowSize));
    }
  }

  @Test(dependsOnMethods = "shouldPassIfInternalFrameHasMatchingSize")
  public final void shouldResizeInternalFrameToGivenSize() {
    FluentDimension newSize = internalFrameSize().addToWidth(20).addToHeight(40);
    fixture.resizeTo(newSize);
    fixture.requireSize(newSize);
  }

  private FluentDimension internalFrameSize() {
    return new FluentDimension(target.getSize());
  }

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
    JDesktopPane desktop = new JDesktopPane(); 
    target.setVisible(true);
    desktop.add(target);
    mainFrame.setContentPane(desktop);
    robot().showWindow(mainFrame, new Dimension(400, 300));
  }

  @Override protected boolean targetBlocksMainWindow() { return true; }
  @Override protected boolean addTargetToWindow() { return false; }
  
  private static class MyInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    MyInternalFrame() {
      super("Internal Frame", true, true, true, true);
      setName("target");
      setSize(200, 100);
    }
  }
}
