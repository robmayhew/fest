/*
 * Created on Apr 2, 2008
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
import java.util.HashMap;
import java.util.Map;

import static org.fest.swing.core.EventMode.*;

/**
 * Understands event generators for different operation modes.
 *
 * @author Yvonne Wang
 */
class InputEventGenerators {

  private final Map<EventMode, InputEventGenerator> eventGeneratorMap = new HashMap<EventMode, InputEventGenerator>();
  private final Settings settings;
  private final AWTEventGenerator awtEventGenerator;

  InputEventGenerators(AWTEventPoster eventPoster) {
    this.settings = eventPoster.settings();
    RobotEventGenerator robotEventGenerator;
    try {
      robotEventGenerator = robotEventGenerator();
      eventGeneratorMap.put(ROBOT, robotEventGenerator);
    } catch (AWTException e) {
      settings.eventMode(AWT);
    }
    awtEventGenerator = new AWTEventGenerator(eventPoster);
    eventGeneratorMap.put(AWT, awtEventGenerator);
  }

  RobotEventGenerator robotEventGenerator() throws AWTException {
    return new RobotEventGenerator(settings);
  }
  
  InputEventGenerator current() {
    InputEventGenerator inputEventGenerator = eventGeneratorMap.get(settings.eventMode());
    if (inputEventGenerator != null) return inputEventGenerator;
    return awtEventGenerator;
  }
}
