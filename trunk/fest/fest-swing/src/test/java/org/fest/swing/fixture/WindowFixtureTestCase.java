/*
 * Created on Jul 3, 2007
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

import java.awt.Dimension;
import java.awt.Window;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;

import org.fest.swing.core.Condition;

import org.testng.annotations.Test;

/**
 * Understands test methods for subclasses of <code>{@link WindowFixture}</code>.
 * @param <T> the type of window tested by this test class. 
 *
 * @author Alex Ruiz 
 */
public abstract class WindowFixtureTestCase<T extends Window> extends ComponentFixtureTestCase<T> {

  @Test public final void shouldPassIfWindowHasMatchingSize() {
    windowFixture().requireSize(windowFixture().target.getSize());
  }
  
  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldFailIfWindowHasNotMatchingSize() {
    FluentDimension wrongSize = windowSize().addToWidth(50).addToHeight(30);
    try {
      windowFixture().requireSize(wrongSize);
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      Dimension windowSize = windowFixture().target.getSize();
      assertThat(errorMessage).contains(property("size"), expected(wrongSize), actual(windowSize));
    }
  }

  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldResizeWindowToGivenSize() {
    FluentDimension newSize = windowSize().addToWidth(20).addToHeight(40);
    windowFixture().resizeTo(newSize);
    windowFixture().requireSize(newSize);
  }
  
  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldResizeToGivenWidth() {
    FluentDimension newSize = windowSize().addToWidth(50);
    windowFixture().resizeWidthTo(newSize.width);
    windowFixture().requireSize(newSize);
  }

  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldResizeToGivenHeight() {
    FluentDimension newSize = windowSize().addToHeight(50);
    windowFixture().resizeHeightTo(newSize.height);
    windowFixture().requireSize(newSize);
  }
  
  @Test public final void shouldCloseWindow() {
    windowFixture().close();
    robot().wait(new Condition("Window is closed") {
      @Override public boolean test() {
        return !windowFixture().target.isVisible();
      }
    });
  }
  
  @Override protected boolean addTargetToWindow() { return false; }

  @Override protected boolean targetBlocksMainWindow() { return true; }

  protected final FluentDimension windowSize() {
    return new FluentDimension(windowFixture().target.getSize());
  }

  protected final WindowFixture<T> windowFixture() { return (WindowFixture<T>)fixture(); }
}
