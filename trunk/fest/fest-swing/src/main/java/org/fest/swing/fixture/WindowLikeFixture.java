/*
 * Created on Dec 18, 2007
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
import java.awt.Point;

/**
 * Understands simulation of user events on a window-like component (not necessarily a subclass of 
 * <code>{@link java.awt.Window}</code>) and verification of the state of such window-like component.
 *
 * @author Yvonne Wang
 */
public interface WindowLikeFixture {

  /**
   * Simulates a user closing this fixture's window-like component.
   */
  public void close();
  
  /**
   * Simulates a user resizing horizontally this fixture's window-like component.
   * @param width the width that this fixture's window-like component should have after being resized.
   * @return this fixture.
   */
  WindowLikeFixture resizeWidthTo(int width);

  /**
   * Simulates a user resizing vertically this fixture's window-like component.
   * @param height the height that this fixture's window-like component should have after being resized.
   * @return this fixture.
   */
  WindowLikeFixture resizeHeightTo(int height);

  /**
   * Simulates a user resizing this fixture's window-like component.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  WindowLikeFixture resizeTo(Dimension size);

  /**
   * Asserts that the size of this fixture's window-like component is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's window-like component is not equal to the given size. 
   */
  WindowLikeFixture requireSize(Dimension size);
  
  /**
   * Simulates a user moving this fixture's window-like component to the given point.
   * @param p the point to move this fixture's window-like component to.
   * @return this fixture.
   */
  WindowLikeFixture moveTo(Point p);
}
