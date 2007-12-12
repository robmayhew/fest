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
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;

import java.awt.Component;

import javax.swing.JButton;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.remote.testing.ComponentFixtureAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentFixtureFactories}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentFixtureFactoriesTest {

  private RobotFixture robot;
  private ComponentFixtureFactories factories;

  @BeforeMethod public void setUp() {
    robot = robotWithCurrentAwtHierarchy();
    factories = ComponentFixtureFactories.instance();
  }

  @Test public void shouldCreateFixtureForJButtonOnce() {
    class MyButton extends JButton {
      private static final long serialVersionUID = 1L;
      MyButton() { super("My Button"); }
    }
    MyButton button = new MyButton();
    ComponentFixture<? extends Component> created = factories.createFixture(robot, button);
    ComponentFixture<? extends Component> cached = factories.createFixture(robot, button);
    ComponentFixtureAssert fixture = new ComponentFixtureAssert(created);
    assertThat(fixture).is(JButtonFixture.class).hasRobot(robot).hasTarget(button).isSameAs(cached);
  }
}
