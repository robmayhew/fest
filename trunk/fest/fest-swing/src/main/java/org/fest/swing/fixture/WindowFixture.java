/*
 * Created on Apr 10, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.Window;

/**
 * Understands simulation of user events and state verification of a <code>{@link Window}</code>.
 * @param <T> the type of window handled by this fixture. 
 *
 * @author Alex Ruiz 
 */
public interface WindowFixture<T extends Window> extends ComponentFixture<T> {

  /**
   * Shows the target window.
   * @return this fixture.
   */
  WindowFixture<T> show();
  
  /**
   * Shows the target window, resized to the given size.
   * @param size the given size.
   * @return this fixture.
   */
  WindowFixture<T> show(Dimension size);

  /**
   * Asserts that the size of the target window is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the target window is not equal to the given size. 
   */
  WindowFixture<T> requireSize(Dimension size);

  /**
   * Simulates a user resizing the target window horizontally.
   * @param width the width that the target window should have after being resized.
   * @return this fixture.
   */
  WindowFixture<T> resizeWidthTo(int width);

  /**
   * Simulates a user resizing the target window vertically.
   * @param height the height that the target window should have after being resized.
   * @return this fixture.
   */
  WindowFixture<T> resizeHeightTo(int height);

  /**
   * Simulates a user resizing the target window horizontally and/or vertically.
   * @param size the size (height and width) that the target window should have after being resized.
   * @return this fixture.
   */
  WindowFixture<T> resizeTo(Dimension size);
}
