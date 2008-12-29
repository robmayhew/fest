/*
 * Created on Dec 22, 2008
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
package org.fest.javafx;

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.javafx.launcher.ScriptLauncher.launch;
import static org.fest.swing.core.matcher.FrameByTitleMatcher.withTitle;
import static org.fest.swing.finder.WindowFinder.findFrame;

import java.awt.Component;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.javafx.scene.JSGPanelImpl;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGParent;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.fx.FXNode;

/**
 * Understands testing button clicks in a JavaFX UI.
 *
 * @author Alex Ruiz
 */
@Test
public class ClickButtonTest {
  
  private Robot robot;
  private java.awt.Robot realRobot;
  private FrameFixture calculator;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() throws Exception {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    realRobot = new java.awt.Robot();
    launch("/org/fest/javafx/Calculator.fx");
    calculator = findFrame(withTitle("Calculator")).using(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldUpdateTextBoxWithPressedNumber() {
    JFrame frame = calculator.targetCastedTo(JFrame.class);
    JSGPanelImpl panel = (JSGPanelImpl)frame.getLayeredPane().getComponent(0);   
    SGNode scene = panel.getScene();
    SGParent parent = scene.getParent();
    FXNode nodeWithButton8 = nodeWithButton("8", parent);
    click(nodeWithButton8);
    FXNode nodeWithTextBox = nodeWithTextBox(parent);
    SGText textNode = (SGText)nodeWithTextBox.getLeaf();
    assertThat(textNode.getText()).isEqualTo("8");
    FXNode nodeWithButton6 = nodeWithButton("6", parent);
    click(nodeWithButton6);
    assertThat(textNode.getText()).isEqualTo("86");
    Pause.pause(3000);
  }
  
  private static FXNode nodeWithButton(String text, SGParent root) {
    for (SGNode child : root.getChildren()) {
      if (child instanceof FXNode) {
        FXNode fxNode = (FXNode) child;
        SGNode leaf = fxNode.getLeaf();
        if (leaf instanceof SGComponent) {
          SGComponent componentNode = (SGComponent) leaf;
          Component component = componentNode.getComponent();
          if (component instanceof JButton) {
            JButton button = (JButton) component;
            if (text.equals(button.getText())) return fxNode;
          }
        }
      }
      if (child instanceof SGParent) {
        SGParent newRoot = (SGParent) child;
        FXNode fxNode = nodeWithButton(text, newRoot);
        if (fxNode != null) return fxNode;
      }
    }
    return null;
  }

  private static FXNode nodeWithTextBox(SGParent root) {
    for (SGNode child : root.getChildren()) {
      if (child instanceof FXNode) {
        FXNode fxNode = (FXNode) child;
        SGNode leaf = fxNode.getLeaf();
        if (leaf instanceof SGText) return fxNode;
      }
      if (child instanceof SGParent) {
        SGParent newRoot = (SGParent) child;
        FXNode fxNode = nodeWithTextBox(newRoot);
        if (fxNode != null) return fxNode;
      }
    }
    return null;
  }

  private void click(FXNode node) {
    moveMouseTo(node);
    realRobot.mousePress(BUTTON1_MASK);
    realRobot.mouseRelease(BUTTON1_MASK);
    robot.waitForIdle();
  }

  private void moveMouseTo(FXNode fxNode) {
    Rectangle2D boundsInScene = fxNode.getBoundsInScene();
    int centerX = (int)boundsInScene.getCenterX();
    int centerY = (int)boundsInScene.getCenterY();
    Point p = fxNode.getPanel().getLocationOnScreen();
    p.translate(centerX, centerY);
    realRobot.mouseMove(p.x, p.y);
  }
}
