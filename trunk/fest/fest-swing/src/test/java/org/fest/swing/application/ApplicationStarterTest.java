/*
 * Created on May 23, 2008
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
package org.fest.swing.application;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.util.Collections.list;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.fest.swing.application.JavaApp.ArgumentObserver;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ApplicationStarter}</code>.
 *
 * @author Yvonne Wang
 */
public class ApplicationStarterTest {

  private Robot robot;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldLaunchApplicationWithoutArguments() {
    ApplicationStarter.application(JavaApp.class).start();
    assertFrameIsShowing();
  }

  @Test public void shouldLaunchApplicationWithoutArgumentsUsingFQCN() {
    ApplicationStarter.application(JavaApp.class.getName()).start();
    assertFrameIsShowing();
  }

  @Test public void shouldLaunchApplicationUsingArguments() {
    final List<String> arguments = new ArrayList<String>();
    ArgumentObserver observer = new ArgumentObserver() {
      public void arguments(String[] args) {
        arguments.addAll(list(args));
      }
    };
    JavaApp.add(observer);
    ApplicationStarter.application(JavaApp.class).withArgs("arg1", "arg2").start();
    assertFrameIsShowing();
    assertThat(arguments).containsOnly("arg1", "arg2");
  }

  private void assertFrameIsShowing() {
    FrameFixture frame = WindowFinder.findFrame(new GenericTypeMatcher<Frame>() {
      protected boolean isMatching(Frame frame) {
        return "Java Application".equals(frame.getTitle()) && frame.isShowing();
      }
    }).using(robot);
    assertThat(frame).isNotNull();
  }
}
