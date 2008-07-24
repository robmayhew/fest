/*
 * Created on Jun 14, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link ComponentFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class ComponentFixtureTest {

  private Robot robot;
  private Settings settings;
  private Class<JTextField> type;
  private String name;
  private JTextField target;

  @BeforeMethod public void setUp() {
    robot = createMock(Robot.class);
    settings = new Settings();
    type = JTextField.class;
    name = "textBox";
    target = new JTextField();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRobotIsNullWhenLookingUpComponentByType() {
    new ConcreteComponentFixture(null, type);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTypeIsNullWhenLookingUpComponentByType() {
    new ConcreteComponentFixture(robot, (Class<? extends JTextField>)null);
  }

  public void shouldLookupComponentByType() {
    final ComponentFinder finder = finder();
    new EasyMockTemplate(robot, finder) {
      protected void expectations() {
        expect(robot.settings()).andReturn(settings);
        expect(robot.finder()).andReturn(finder);
        expect(finder.findByType(type, requireShowing())).andReturn(target);
      }

      protected void codeToTest() {
        assertHasCorrectTarget(new ConcreteComponentFixture(robot, type));
      }
    }.run();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRobotIsNullWhenLookingUpComponentByName() {
    new ConcreteComponentFixture(null, name, type);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTypeIsNullWhenLookingUpComponentByName() {
    new ConcreteComponentFixture(robot, name, null);
  }

  public void shouldLookupComponentByName() {
    final ComponentFinder finder = finder();
    new EasyMockTemplate(robot, finder) {
      protected void expectations() {
        expect(robot.settings()).andReturn(settings);
        expect(robot.finder()).andReturn(finder);
        expect(finder.findByName(name, type, requireShowing())).andReturn(target);
      }

      protected void codeToTest() {
        assertHasCorrectTarget(new ConcreteComponentFixture(robot, name, type));
      }
    }.run();
  }

  private ComponentFinder finder() {
    return createMock(ComponentFinder.class);
  }

  private boolean requireShowing() {
    return settings.componentLookupScope().requireShowing();
  }

  private void assertHasCorrectTarget(ConcreteComponentFixture fixture) {
    assertThat(fixture.target).isSameAs(target);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRobotIsNullWhenPassingTarget() {
    new ConcreteComponentFixture(null, target);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTargetIsNullWhenPassingTarget() {
    new ConcreteComponentFixture(robot, (JTextField)null);
  }

  public void shouldReturnFontFixtureWithFontFromTarget() {
    FontFixture fontFixture = fixture().font();
    assertThat(fontFixture.target()).isSameAs(target.getFont());
    assertThat(fontFixture.description()).contains("javax.swing.JTextField")
                                         .contains("property:'font'");
  }

  public void shouldReturnColorFixtureWithBackgroundFromTarget() {
    ColorFixture colorFixture = fixture().background();
    assertThat(colorFixture.target()).isSameAs(target.getBackground());
    assertThat(colorFixture.description()).contains("javax.swing.JTextField")
                                          .contains("property:'background'");
  }

  public void shouldReturnColorFixtureWithForegroundFromTarget() {
    ColorFixture colorFixture = fixture().foreground();
    assertThat(colorFixture.target()).isSameAs(target.getForeground());
    assertThat(colorFixture.description()).contains("javax.swing.JTextField")
                                          .contains("property:'foreground'");
  }

  public void shouldCastTarget() {
    JTextField castedTarget = fixture().targetCastedTo(JTextField.class);
    assertThat(castedTarget).isSameAs(target);
  }

  private ComponentFixture<JTextField> fixture() {
    return new ConcreteComponentFixture(robot, target);
  }

  private static class ConcreteComponentFixture extends ComponentFixture<JTextField> {
    public ConcreteComponentFixture(Robot robot, Class<? extends JTextField> type) {
      super(robot, type);
    }

    public ConcreteComponentFixture(Robot robot, JTextField target) {
      super(robot, target);
    }

    public ConcreteComponentFixture(Robot robot, String name, Class<? extends JTextField> type) {
      super(robot, name, type);
    }
  }
}

