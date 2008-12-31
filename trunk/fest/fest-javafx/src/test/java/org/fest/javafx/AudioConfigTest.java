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
import static org.fest.javafx.desktop.launcher.ScriptLauncher.launch;
import static org.fest.swing.core.matcher.FrameByTitleMatcher.withTitle;
import static org.fest.swing.finder.WindowFinder.findFrame;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.fixture.FrameFixture;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Michael Huettermann
 */
public class AudioConfigTest {
  
  private Robot robot;
  private FrameFixture main;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() throws Exception {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    launch("/org/fest/javafx/AudioConfigStage.fx");
    main = findFrame(withTitle("Audio")).using(robot);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test
  public void testMe() {
	  assertThat(0==0);
  }
  
}
