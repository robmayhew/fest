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

import java.awt.Component;
import java.awt.Point;

/**
 * Understands generation of input events.
 *
 * @author Alex Ruiz
 */
interface InputEventGenerator {

  void pressMouse(int buttons);

  void pressMouse(Component c, Point where, int buttons);

  void releaseMouse(int buttons);

  void moveMouse(Component c, int x, int y);

  void pressKey(int keyCode, char keyChar);

  void releaseKey(int keyCode);

}