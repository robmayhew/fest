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
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint.EAST;
import static org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint.NORTH;
import static org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint.SOUTH;
import static org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint.WEST;

import org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JToolBarFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarFixtureTest extends ComponentFixtureTestCase<JToolBar> {

  private static class MyToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;

    public final JButton button;

    public MyToolBar(String name) {
      super(name);
      Action action = new AbstractAction() {
        private static final long serialVersionUID = 1L;
        public void actionPerformed(ActionEvent e) {}
      };
      button = add(action);
      button.setName("button");
      button.setText("A Button");
    }
  }
  
  private JFrame toolbarFrame;
  private BorderLayout borderLayout;
  private JToolBarFixture fixture;
  private MyToolBar target;

  @Test public void shouldFloatToolbar() {
    Window oldAncestor = toolbarAncestor();
    Point oldAncestorLocation = oldAncestor.getLocation();
    fixture.floatTo(whereToFloat());
    Window newAncestor = toolbarAncestor();
    assertThat(newAncestor).isNotSameAs(oldAncestor);
    Point newAncestorLocation = newAncestor.getLocation();
    // TODO add a inBetween(int, int) to IntAssert.
    assertThat(newAncestorLocation.x).isGreaterThan(oldAncestorLocation.x);
    assertThat(newAncestorLocation.y).isGreaterThan(oldAncestorLocation.y);
  }

  @Test(dependsOnMethods = "shouldFloatToolbar")
  public void shouldUnfloatToolbar() {
    Window oldAncestor = toolbarAncestor();
    fixture.floatTo(whereToFloat());
    fixture.unfloat();
    assertThat(toolbarAncestor()).isSameAs(oldAncestor);
  }
  
  @Test(dependsOnMethods = "shouldFloatToolbar", dataProvider = "unfloatConstraints")
  public void shouldUnfloatToolbarToGivenPosition(UnfloatConstraint constraint) {
    Window originalParent = toolbarAncestor();
    fixture.floatTo(whereToFloat());
    fixture.unfloat(constraint);
    assertThat(toolbarAncestor()).isSameAs(originalParent);
    assertThat(borderLayout.getLayoutComponent(constraint.value)).isSameAs(target);
  }

  private Point whereToFloat() {
    Rectangle bounds = toolbarAncestor().getBounds();
    int delta = 10;
    int x = bounds.x + bounds.width + delta;
    int y = bounds.y + bounds.height + delta;
    return new Point(x, y);
  }
  
  @DataProvider(name = "unfloatConstraints")
  public Object[][] unfloatConstraints() {
    return new Object[][] { 
        { NORTH }, 
        { EAST },
        { SOUTH },
        { WEST }
    };  
  }
  
  private Window toolbarAncestor() {
    return getWindowAncestor(fixture.target);
  }
  
  @Test public void shouldFindToolbarSubcomponents() {
    ComponentEvents events = ComponentEvents.attachTo(target.button);
    fixture.button("button").click();
    assertThat(events.clicked());
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
    borderLayout = new BorderLayout();
    toolbarFrame.setLayout(borderLayout);
    toolbarFrame.add(target, BorderLayout.NORTH);
    robot().showWindow(toolbarFrame, new Dimension(300, 200));
  }
}
