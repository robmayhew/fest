/*
 * Created on Jun 3, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.finder.WindowFinder.findDialog;

/**
 * Tests lookup of a modal dialog. This test tries to reproduce the problem reported at
 * <a href="http://groups.google.com/group/easytesting/browse_thread/thread/c42bd103c28d6a1a" target="_blank">this mailing list message</a>.
 *
 * @author Alex Ruiz
 */
public class ModalDialogLookupTest {

  private Robot robot;
  private MyWindow frame;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = MyWindow.createNew();
    robot.showWindow(frame);
  }
  
  @Test public void shouldShowModalDialogAndNotBlock() {
    FrameFixture frameFixture = new FrameFixture(robot, frame);
    frameFixture.button("launch").click();
    DialogFixture dialogFixture = findDialog(TestDialog.class).using(robot);
    assertThat(dialogFixture.target).isSameAs(frame.dialog);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final JButton button = new JButton("Launch");
    final TestDialog dialog = TestDialog.createNew(this);
    
    private MyWindow() {
      super(ModalDialogLookupTest.class);
      button.setName("launch");
      add(button);
      dialog.setModal(true);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          dialog.setVisible(true);
        }
      });
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
