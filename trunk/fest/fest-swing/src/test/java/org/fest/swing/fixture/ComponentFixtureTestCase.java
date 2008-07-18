/*
 * Created on Jul 13, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;

import org.testng.annotations.BeforeMethod;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.driver.ComponentDriver;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Understands test methods for implementations of <code>{@link ComponentFixture}</code>.
 * @param <T> the type of component tested by this test class. 
 *
 * @author Alex Ruiz
 */
public abstract class ComponentFixtureTestCase<T extends Component> {

  private Robot robot;
  private ComponentFinder finder;
  private Settings settings;

  @BeforeMethod public final void setUp() {
    robot = createMock(Robot.class);
    finder = createMock(ComponentFinder.class);
    settings = new Settings();
    onSetUp();
  }

  abstract void onSetUp();

  Robot robot() { return robot; }

  ComponentFinder finder() { return finder; }
  
  Settings settings() { return settings; }

  abstract T target();

  abstract ComponentDriver driver();

  @SuppressWarnings("unchecked") Class<T> targetType() {
    return (Class<T>) target().getClass();
  }

  private abstract class FixtureCreationTemplate extends EasyMockTemplate {
    FixtureCreationTemplate() {
      super(robot(), finder());
    }
    
    protected final void expectations() {
      expect(robot().finder()).andReturn(finder());
      expect(robot().settings()).andReturn(settings());
      expectComponentLookup();
    }
    
    abstract void expectComponentLookup();
    
    protected final void codeToTest() {
      ComponentFixture<T> fixture = fixture();
      assertThat(fixture.component()).isSameAs(target());
    }
    
    abstract ComponentFixture<T> fixture();
  }

  abstract class FixtureCreationByTypeTemplate extends FixtureCreationTemplate {
    void expectComponentLookup() {
      expect(finder().findByType(targetType(), requireShowing())).andReturn(target());
    }
  }
  
  abstract class FixtureCreationByNameTemplate extends FixtureCreationTemplate {
    private final String name = "c";

    final void expectComponentLookup() {
      expect(finder().findByName(name, targetType(), requireShowing())).andReturn(target());
    }

    final ComponentFixture<T> fixture() {
      return fixtureWithName(name);
    }

    abstract ComponentFixture<T> fixtureWithName(String name);
  }
  
  @SuppressWarnings("unchecked")
  void replaceInputSimulatorIn(Object fixture) {
    assertThat(fixture).isInstanceOf(ComponentFixture.class);
    ComponentFixture<? extends Component> componentFixture = (ComponentFixture<? extends Component>)fixture;
    componentFixture.inputSimulator(new InputSimulator(driver(), target()));
  }
  
  private boolean requireShowing() {
    return settings().componentLookupScope().requireShowing();
  }
}