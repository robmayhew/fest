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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;

import java.awt.Component;

import javax.swing.JButton;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.remote.testing.ComponentFixtureAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JButtonFixtureFactory}</code>.
 *
 * @author Alex Ruiz
 */
public class JButtonFixtureFactoryTest {

  private JButtonFixtureFactory factory;

  @BeforeMethod public void setUp() {
    factory = new JButtonFixtureFactory();
  }

  @Test public void shouldSupportCreationOfJButtons() {
    assertThat(factory.supportedType()).isEqualTo(JButton.class);
  }

  @Test public void shouldCreateJButtonFixture() {
    JButton button = new JButton("Press Me");
    RobotFixture robot = robotWithNewAwtHierarchy();
    ComponentFixture<? extends Component> createdFixture = factory.fixtureFor(robot, button);
    ComponentFixtureAssert fixture = new ComponentFixtureAssert(createdFixture);
    assertThat(fixture).is(JButtonFixture.class).hasRobot(robot).hasTarget(button);
  }
}
