/*
 * Created on Dec 11, 2007
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
 * Understands a template for <code>{@link ComponentFixtureFactory}</code>.
 * @param <T> the type of component this factory supports.
 *
 * @author Alex Ruiz
 */
abstract class ComponentFixtureFactoryTemplate<T extends Component> implements ComponentFixtureFactory {

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public final ComponentFixture<? extends Component> fixtureFor(RobotFixture robot, Component c) {
    return fixture(robot, (T)c);
  }

  abstract ComponentFixture<T> fixture(RobotFixture robot, T c);

  /** @see org.fest.swing.remote.factory.ComponentFixtureFactory#supportedType() */
  public final Class<? extends Component> supportedType() {
    return supported();
  }

  abstract Class<T> supported();
}
