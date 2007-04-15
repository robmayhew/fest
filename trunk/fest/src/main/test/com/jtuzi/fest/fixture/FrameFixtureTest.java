/*
 * Created on Feb 10, 2007
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
package com.jtuzi.fest.fixture;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.jtuzi.fest.RobotFixture;
import com.jtuzi.fest.fixture.FrameFixture;

import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;


import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link FrameFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameFixtureTest {

  private FrameFixture frameFixture;
  private JFrame frame;
  
  @BeforeClass public void setUp() {
    frameFixture = new FrameFixture(new RobotFixture(), new JFrame());
    frame = frameFixture.targetCastedTo(JFrame.class);
    frameFixture.show();
  }

  @BeforeMethod public void init() {
    frame.setSize(new Dimension(200, 100));
  }

  @Test public void shouldPassIfWindowHasMatchingSize() {
    frameFixture.shouldHaveThisSize(frame.getSize());
  }
  
  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize", expectedExceptions = AssertionError.class)
  public void shouldFailIfWindowHasNotMatchingSize() {
    FluentDimension wrongSize = windowSize().addToWidth(50).addToHeight(30);
    frameFixture.shouldHaveThisSize(wrongSize);
  }

  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public void shouldResizeWindowToGivenSize() {
    FluentDimension newSize = windowSize().addToWidth(20).addToHeight(40);
    frameFixture.resizeTo(newSize);
    frameFixture.shouldHaveThisSize(newSize);
  }
  
  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public void shouldResizeToGivenWidth() {
    FluentDimension newSize = windowSize().addToWidth(50);
    frameFixture.resizeWidthTo(newSize.width);
    frameFixture.shouldHaveThisSize(newSize);
  }

  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public void shouldResizeToGivenHeight() {
    FluentDimension newSize = windowSize().addToHeight(50);
    frameFixture.resizeHeightTo(newSize.height);
    frameFixture.shouldHaveThisSize(newSize);
  }
  
  @Test public void shouldIconifyFrame() {
    frameFixture.iconify();
    assertTrue((frameFixture.target.getExtendedState() == ICONIFIED));
  }
  
  @Test(dependsOnMethods = "shouldIconifyFrame") 
  public void shouldDeiconifyFrame() {
    frameFixture.iconify();
    frameFixture.deiconify();
    assertTrue((frameFixture.target.getExtendedState() == NORMAL));
  }
  
  @Test public void shouldMaximizeFrame() {
    frameFixture.maximize();
    assertTrue((frameFixture.target.getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH);
  }

  @Test(dependsOnMethods = "shouldMaximizeFrame") 
  public void shouldNormalizeFrame() {
    frameFixture.maximize();
    frameFixture.normalize();
    assertTrue((frameFixture.target.getExtendedState() == NORMAL));
  }
  
  private FluentDimension windowSize() {
    return new FluentDimension(frame.getSize());
  }
  
  @AfterClass public void tearDown() {
    frameFixture.cleanUp();
  }
}
