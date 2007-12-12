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

import static org.fest.util.Strings.concat;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.remote.core.RemoteActionFailure;

/**
 * Understands a registry of <code>{@link ComponentFixtureFactory}</code>.
 *
 * @author Alex Ruiz
 */
public final class ComponentFixtureFactories {

  private final Map<Class<?>, ComponentFixtureFactory> factories = new HashMap<Class<?>, ComponentFixtureFactory>();
  private final Map<Component, ComponentFixture<?>> fixtures = new WeakHashMap<Component, ComponentFixture<?>>();

  /**
   * Creates a fixture for the given <code>{@link Component}</code>
   * @param robot the robot to use to create the fixture.
   * @param component the given GUI component.
   * @return the created fixture.
   * @throws RemoteActionFailure if a fixture cannot be created for the given component.
   */
  public ComponentFixture<? extends Component> createFixture(RobotFixture robot, Component component) {
    if (fixtures.containsKey(component)) return fixtures.get(component);
    ComponentFixtureFactory factory = factoryFor(component.getClass());
    ComponentFixture<? extends Component> fixture = factory.fixtureFor(robot, component);
    fixtures.put(component, fixture);
    return fixture;
  }

  private ComponentFixtureFactory factoryFor(Class<?> type) {
    if (factories.containsKey(type)) return factories.get(type);
    if (type.equals(Component.class))
      throw new RemoteActionFailure(concat(
          "Unable to find a fixture factory for components of type ", type.getName()));
    return factoryFor(type.getSuperclass());
  }

  /**
   * Returns the singleton instance of this class.
   * @return the singleton instance of this class.
   */
  public static ComponentFixtureFactories instance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {
    static final ComponentFixtureFactories INSTANCE = new ComponentFixtureFactories();
  }

  private ComponentFixtureFactories() {
    register(new JButtonFixtureFactory());
  }

  private void register(ComponentFixtureFactory factory) {
    verifyAnotherFactoryRegistered(factory);
    factories.put(factory.supportedType(), factory);
  }

  private void verifyAnotherFactoryRegistered(ComponentFixtureFactory factory) {
    Class<? extends Component> key = factory.supportedType();
    if (!factories.containsKey(key)) return;
    throw new IllegalArgumentException(concat(
        "Unable to register factory ", factory, " for the type ", key.getName(), ". ",
        "Factory ", factories.get(key), " is already registered for such type."));
  }
}
