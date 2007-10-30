/*
 * Created on Oct 29, 2007
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
package org.fest.swing.fixture.util;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;
import org.fest.swing.TestFrame;
import org.fest.swing.fixture.JFileChooserFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JFileChooserFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class JFileChooserFinderTest {

  private RobotFixture robot;
  private TestFrame frame;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    frame = new TestFrame(getClass());
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldFindFileChooser() {
    JFileChooser fileChooser = showFileChooser();
    JFileChooserFixture found = JFileChooserFinder.find().using(robot);
    assertThat(found.target).isSameAs(fileChooser);
  }

  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldFailIfFileChooserNotFound() {
    JFileChooserFinder.find().using(robot);
  }
  
  private JFileChooser showFileChooser() {
    final JFileChooser fileChooser = new JFileChooser();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        fileChooser.showOpenDialog(frame);
      }
    });
    return fileChooser;
  }
  
}
