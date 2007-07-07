/*
 * Created on Jul 5, 2007
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JToolBarFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JToolBarFixtureTest extends ComponentFixtureTestCase<JToolBar> {

  private static class MyToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;

    public final JButton button;

    public MyToolBar(String name) {
      super(name);
      Action action = new AbstractAction() {
        private static final long serialVersionUID = 1L;
        public void actionPerformed(ActionEvent e) {
          
        }
      };
      button = add(action);
      button.setText("A Button");
    }
  }
  
  private JFrame toolbarFrame;
  private JToolBarFixture fixture;
  private MyToolBar target;

  @Test public void shouldFloatToolbar() {
    Window originalParent = getWindowAncestor(fixture.target);
    Point originalParentLocation = originalParent.getLocation();
    Point newLocation = new Point(originalParentLocation.x + 200, originalParentLocation.y + 100);
    fixture.floatTo(newLocation);
    Window newParent = getWindowAncestor(fixture.target);
    assertThat(newParent).isNotSameAs(originalParent);
    Point newParentLocation = newParent.getLocation();
    assertThat(newParentLocation.x).isGreaterThan(originalParentLocation.x);
    assertThat(newParentLocation.y).isGreaterThan(originalParentLocation.y);
  }

  @Test(dependsOnMethods = "shouldFloatToolbar")
  public void shouldUnfloatToolbar() {
    Window originalParent = getWindowAncestor(fixture.target);
    fixture.floatTo(new Point(200, 200));
    fixture.unfloat();
    assertThat(getWindowAncestor(fixture.target)).isSameAs(originalParent);
  }
  
  @Test(dependsOnMethods = "shouldFloatToolbar")
  public void shouldUnfloatToolbarToGivenPosition() {
    Window originalParent = getWindowAncestor(fixture.target);
    fixture.floatTo(new Point(200, 200));
    fixture.unfloat(JToolBarFixture.UnfloatConstraint.SOUTH);
    assertThat(getWindowAncestor(fixture.target)).isSameAs(originalParent);
  }

  @Override protected boolean targetBlocksMainWindow() { return true; }
  @Override protected boolean addTargetToWindow() { return false; }
  
  protected JToolBar createTarget() {
    target = new MyToolBar("target");
    target.setFloatable(true);
    return target;
  }

  protected ComponentFixture<JToolBar> createFixture() {
    showToolbarFrame();
    fixture = new JToolBarFixture(robot(), "target");
    return fixture;
  }

  private void showToolbarFrame() {
    toolbarFrame = new JFrame();
    toolbarFrame.setLayout(new BorderLayout());
    toolbarFrame.add(target, BorderLayout.NORTH);
    robot().showWindow(toolbarFrame, new Dimension(300, 200));
  }
}
