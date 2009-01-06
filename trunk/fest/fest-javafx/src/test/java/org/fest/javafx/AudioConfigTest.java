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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.javafx.desktop.launcher.JavaFxClassLauncher.launch;

import javax.swing.JFrame;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.timing.Pause;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.javafx.scene.JSGPanelImpl;
import com.sun.scenario.scenegraph.SGNode;

/**
 * @author Michael Huettermann
 */
public class AudioConfigTest {
  
  private Robot robot;
  private JFrame frame;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() throws Exception {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    frame=launch(AudioConfigStage.class);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test
  public void testMe() {
	  assertThat(0==0);
  }
  
  @Test
  public void testBla() {
	  JSGPanelImpl panel = (JSGPanelImpl) frame.getLayeredPane()
				.getComponent(0);
		SGNode scene = 
			panel.getScene();
		Pause.pause(30000);
	}
}
