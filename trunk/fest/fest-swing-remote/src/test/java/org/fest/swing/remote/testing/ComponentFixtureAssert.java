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
package org.fest.swing.remote.testing;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Component;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;

/**
 * Understands assertion methods related to <code>{@link ComponentFixture}</code>s.
 *
 * @author Alex Ruiz
 */
public class ComponentFixtureAssert implements AssertExtension {

  private final ComponentFixture<? extends Component> fixture;

  /**
   * Creates a new </code>{@link ComponentFixtureAssert}</code>.
   * @param fixture the fixture to verify.
   */
  public ComponentFixtureAssert(ComponentFixture<? extends java.awt.Component> fixture) {
    this.fixture = fixture;
  }

  public ComponentFixtureAssert is(Class<? extends ComponentFixture<? extends Component>> type) {
    assertThat(fixture).isInstanceOf(type);
    return this;
  }

  public ComponentFixtureAssert hasRobot(RobotFixture robot) {
    assertThat(fixture.robot).isSameAs(robot);
    return this;
  }

  public ComponentFixtureAssert hasTarget(Component target) {
    assertThat(fixture.target).isSameAs(target);
    return this;
  }

  public ComponentFixtureAssert isSameAs(ComponentFixture<?> fixture) {
    assertThat(this.fixture).isSameAs(fixture);
    return this;
  }
}
