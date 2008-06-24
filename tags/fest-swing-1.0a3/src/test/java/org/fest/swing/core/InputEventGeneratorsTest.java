/*
 * Created on Apr 3, 2008
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
package org.fest.swing.core;

import java.awt.AWTException;
import java.awt.Toolkit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.EventMode.*;

/**
 * Tests for <code>{@link InputEventGenerators}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class InputEventGeneratorsTest {

  private AWTEventPoster eventPoster;
  private Settings settings;
  private InputEventGenerators generators;
  
  @BeforeMethod public void setUp() {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    InputState inputState = new InputState(toolkit);
    settings = new Settings();
    eventPoster = new AWTEventPoster(toolkit, inputState, WindowMonitor.instance(), settings);
  }

  @Test public void shouldHaveRobotModeByDefault() {
    settings.eventMode(AWT);
    generators = new InputEventGenerators(eventPoster);
    assertThat(settings.eventMode()).isEqualTo(ROBOT);
  }
  
  @Test public void shouldHaveAWTModeIfRobotCreationFails() {
    settings.eventMode(ROBOT);
    simulateRobotFailure();
    assertThat(settings.eventMode()).isEqualTo(AWT);
  }

  @Test public void shouldReturnRobotEventGeneratorIsEventModeIsRobot() {
    generators = new InputEventGenerators(eventPoster);
    settings.eventMode(ROBOT);
    assertThat(generators.current()).isInstanceOf(RobotEventGenerator.class);
  }

  @Test public void shouldReturnAWTEventGeneratorIsEventModeIsAWT() {
    generators = new InputEventGenerators(eventPoster);
    settings.eventMode(AWT);
    assertThat(generators.current()).isInstanceOf(AWTEventGenerator.class);
  }

  @Test public void shouldAlwaysReturnAWTEventGeneratorIfRobotCreationFailed() {
    simulateRobotFailure();
    settings.eventMode(ROBOT);
    assertThat(generators.current()).isInstanceOf(AWTEventGenerator.class);
  }

  private void simulateRobotFailure() {
    generators = new InputEventGenerators(eventPoster) {
      @Override RobotEventGenerator robotEventGenerator() throws AWTException {
        throw new AWTException("Testing");
      }
    };
  }
}
