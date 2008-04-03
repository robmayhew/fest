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

import java.awt.*;
import java.awt.Robot;

import static java.lang.String.valueOf;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.util.AWT.locationOnScreenOf;
import static org.fest.swing.util.Platform.*;
import static org.fest.util.Strings.concat;

/**
 * Understands input event generation using a AWT <code>{@link Robot}</code>.
 *
 * @author Alex Ruiz
 */
class RobotEventGenerator implements InputEventGenerator {

  private static final int KEY_INPUT_DELAY = 200;

  private final Robot robot;
  private final Settings settings;

  RobotEventGenerator(Settings settings) throws AWTException {
    this.settings = settings;
    robot = new Robot();
    settings.attachTo(robot);
    if (isWindows() || isOSX()) pause(500);
  }

  Robot robot() { return robot; }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where, int buttons) {
    moveMouse(c, where.x, where.y);
    pressMouse(buttons);
  }

  /** ${@inheritDoc} */
  public void pressMouse(int buttons) {
    robot.mousePress(buttons);
  }

  /** ${@inheritDoc} */
  public void releaseMouse(int buttons) {
    robot.mouseRelease(buttons);
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component c, int x, int y) {
    try {
      Point point = locationOnScreenOf(c);
      if (point == null) return;
      point.translate(x, y);
      robot.mouseMove(point.x, point.y);
    } catch (IllegalComponentStateException e) {}
  }

  /** ${@inheritDoc} */
  public void pressKey(int keyCode, char keyChar) {
    try {
      robot.keyPress(keyCode);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(concat("Invalid key code '", valueOf(keyCode), "'"));
    }
  }

  /** ${@inheritDoc} */
  public void releaseKey(int keyCode) {
    robot.keyRelease(keyCode);
    if (!isOSX()) return;
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (KEY_INPUT_DELAY > delayBetweenEvents) pause(KEY_INPUT_DELAY - delayBetweenEvents);
    return;
  }
}
