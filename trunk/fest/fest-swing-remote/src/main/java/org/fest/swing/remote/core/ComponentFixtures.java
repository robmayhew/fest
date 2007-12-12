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
package org.fest.swing.remote.core;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.fest.swing.fixture.ComponentFixture;

/**
 * Understands a registry of <code>{@link ComponentFixture}</code>s.
 *
 * @author Alex Ruiz
 */
public class ComponentFixtures {

  private static final Map<UUID, ComponentFixture<? extends Component>> fixtures =
    new HashMap<UUID, ComponentFixture<? extends Component>>();

  /**
   * Registers the given fixture using a universally unique identifier (UUID) as key.
   * @param fixture the fixture to register.
   * @return the UUID used to register the given fixture.
   */
  public UUID add(ComponentFixture<? extends Component> fixture) {
    UUID id = UUID.randomUUID();
    fixtures.put(id, fixture);
    return id;
  }

  /**
   * Indicates whether a <code>{@link ComponentFixture}</code> has been registered with the given id.
   * @param id the id of the component fixture to verify.
   * @return <code>true</code> if this registry contains a component fixture under the given id, <code>false</code>
   * otherwise.
   */
  public boolean containsFixture(UUID id) {
    return fixtures.containsKey(id);
  }

  /**
   * Returns the <code>{@link ComponentFixture}</code> stored under the given universally unique identifier (UUID).
   * @param id the given UUID.
   * @return the component fixture stored under the given UUID, or <code>null</code> if a fixture cannot be found.
   */
  public ComponentFixture<? extends Component> fixtureWithId(UUID id) {
    return fixtures.get(id);
  }

  /**
   * Returns the singleton instance of this class.
   * @return the singleton instance of this class.
   */
  public static ComponentFixtures instance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {
    static final ComponentFixtures INSTANCE = new ComponentFixtures();
  }

  private ComponentFixtures() {}
}
