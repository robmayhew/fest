/*
 * Created on Dec 10, 2007
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
package org.fest.swing.remote.factory;

import java.awt.Component;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;

/**
 * Understands a factory of <code>{@link ComponentFixture}</code>s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface ComponentFixtureFactory {

  /**
   * Creates a <code>{@link ComponentFixture}</code> for the given GUI <code>{@link Component}</code>.
   * @param robot the robot to use to simulate user input on the given GUI component.
   * @param c the given GUI component.
   * @return the created fixture.
   */
  ComponentFixture<? extends Component> fixtureFor(RobotFixture robot, Component c);

  /**
   * Returns the type of component this factory this factory can create fixtures for.
   * @return the type of component this factory this factory can create fixtures for.
   */
  Class<? extends Component> supportedType();
}
