/*
 * Created on Nov 20, 2007
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

import java.awt.Container;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;

/**
 * Understands a concrete subclass of <code>{@link ContainerFixture}</code> to be used for testing only.
 * @param <T> the type of container to handle. 
 *
 * @author Alex Ruiz
 */
public class ConcreteContainerFixture<T extends Container> extends ContainerFixture<T> {

  public ConcreteContainerFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  public ContainerFixture<T> click() { return null; }
  public ContainerFixture<T> click(MouseButton button) { return null; }
  public ComponentFixture<T> click(MouseClickInfo mouseClickInfo) { return null; }
  public ContainerFixture<T> doubleClick() { return null; }
  public ContainerFixture<T> focus() { return null; }
  public ContainerFixture<T> pressAndReleaseKeys(int... keyCodes) { return null; }
  public ContainerFixture<T> requireDisabled() { return null; }
  public ContainerFixture<T> requireEnabled() { return null; }
  public ContainerFixture<T> requireEnabled(Timeout timeout) { return null; }
  public ContainerFixture<T> requireVisible() { return null; }
  public ComponentFixture<T> requireNotVisible() { return null; }
  public ComponentFixture<T> rightClick() { return null; }
  public ComponentFixture<T> pressKey(int keyCode) { return null; }
  public ComponentFixture<T> releaseKey(int keyCode) { return null; }
}
