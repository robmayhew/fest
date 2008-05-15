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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.finder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.testing.TestFrame;

import static java.util.concurrent.TimeUnit.SECONDS;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.util.AWT.centerOf;

/**
 * Tests for <code>{@link JFileChooserFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JFileChooserFinderTest {

  private Robot robot;
  private MyFrame frame;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    frame = new MyFrame();
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

  @Test public void shouldFindFileChooserBeforeGivenTimeoutExpires() {
    new Thread() {
      @Override public void run() {
        pause(2000);
        clickBrowseButton();
      }
    }.start();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().withTimeout(5, SECONDS).using(robot);
    assertThat(found.target).isSameAs(frame.fileChooser);
  }

  private void clickBrowseButton() {
    JButton button = frame.browseButton;
    robot.click(button, centerOf(button), MouseButton.LEFT_BUTTON, 1);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldFailIfFileChooserNotFound() {
    JFileChooserFinder.findFileChooser().using(robot);
  }
  
  @Test public void shouldFindFileChooserByName() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser("fileChooser").using(robot);
    assertThat(found.target).isSameAs(frame.fileChooser);    
  }
  
  @Test public void shouldFindFileChooserUsingMatcher() {
    clickBrowseButton();
    GenericTypeMatcher<JFileChooser> matcher = new GenericTypeMatcher<JFileChooser>( ) {
      protected boolean isMatching(JFileChooser fileChooser) {
        return "fileChooser".equals(fileChooser.getName());
      }
    };
    JFileChooserFixture found = JFileChooserFinder.findFileChooser(matcher).using(robot);
    assertThat(found.target).isSameAs(frame.fileChooser);    
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JButton browseButton = new JButton("Browse");
    final JFileChooser fileChooser = new JFileChooser();
    
    public MyFrame() {
      super(JFileChooserFinderTest.class);
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
