/*
 * Created on Apr 3, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Toolkit;

import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link AWTEventGenerator}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class AWTEventGeneratorTest extends InputEventGeneratorTestCase {

  private AWTEventGenerator generator;

  @Override void onSetUp() throws Exception {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    InputState inputState = new InputState(toolkit);
    AWTEventPoster eventPoster = new AWTEventPoster(toolkit, inputState, WindowMonitor.instance(), new Settings());
    generator = new AWTEventGenerator(eventPoster);
  }

  @Override InputEventGenerator generator() {
    return generator;
  }

  public void shouldUseInputStateFromEventPoster() {
    assertThat(generator.inputState()).isSameAs(generator.eventPoster().inputState());
  }
}
