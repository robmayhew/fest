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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.MouseButton;
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
  private MyFrame frame;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldFindFileChooser() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().using(robot);
    assertThat(found.target).isSameAs(frame.fileChooser);
  }

  private void clickBrowseButton() {
    robot.click(frame.browseButton, MouseButton.LEFT_BUTTON, 1);
  }

  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldFailIfFileChooserNotFound() {
    JFileChooserFinder.findFileChooser().using(robot);
  }
  
  @Test public void shouldFindFileChooserByName() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser("fileChooser").using(robot);
    assertThat(found.target).isSameAs(frame.fileChooser);    
  }
  
  private static class MyFrame extends TestFrame {
    
    private static final long serialVersionUID = 1L;

    JButton browseButton = new JButton("Browse");
    JFileChooser fileChooser = new JFileChooser();
    
    public MyFrame(Class testClass) {
      super(testClass);
      setUp();
    }

    private void setUp() {
      browseButton.setName("browse");
      browseButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          fileChooser.showOpenDialog(MyFrame.this);
        }
      });
      add(browseButton);
      fileChooser.setName("fileChooser");
    }
  }
  
}
