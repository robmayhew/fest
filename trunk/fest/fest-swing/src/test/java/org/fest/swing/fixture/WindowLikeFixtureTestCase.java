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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;

import org.fest.swing.core.Condition;

import org.testng.annotations.Test;

/**
 * Understands test methods for implementations of <code>{@link WindowLikeFixture}</code>.
 * @param <T> the type of window tested by this test class. 
 *
 * @author Alex Ruiz 
 */
public abstract class WindowLikeFixtureTestCase<T extends Component> extends ComponentFixtureTestCase<T> {

  @Test public final void shouldPassIfWindowHasMatchingSize() {
    windowLikeFixture().requireSize(target().getSize());
  }
  
  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldFailIfWindowHasNotMatchingSize() {
    FluentDimension wrongSize = windowSize().addToWidth(50).addToHeight(30);
    try {
      windowLikeFixture().requireSize(wrongSize);
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      Dimension windowSize = target().getSize();
      assertThat(errorMessage).contains(property("size"), expected(wrongSize), actual(windowSize));
    }
  }

  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldResizeWindowToGivenSize() {
    FluentDimension newSize = windowSize().addToWidth(20).addToHeight(40);
    windowLikeFixture().resizeTo(newSize);
    windowLikeFixture().requireSize(newSize);
  }
  
  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldResizeToGivenWidth() {
    FluentDimension newSize = windowSize().addToWidth(50);
    windowLikeFixture().resizeWidthTo(newSize.width);
    windowLikeFixture().requireSize(newSize);
  }

  @Test(dependsOnMethods = "shouldPassIfWindowHasMatchingSize")
  public final void shouldResizeToGivenHeight() {
    FluentDimension newSize = windowSize().addToHeight(50);
    windowLikeFixture().resizeHeightTo(newSize.height);
    windowLikeFixture().requireSize(newSize);
  }
  
  @Test public final void shouldCloseWindow() {
    windowLikeFixture().close();
    pause(new Condition("Window is closed") {
      @Override public boolean test() {
        return !target().isVisible();
      }
    });
  }
  
  @Test public final void shouldMoveWindow() {
    Point p = new FluentPoint(windowLocationOnScreen()).addToX(10).addToY(10);
    windowLikeFixture().moveTo(p);
    assertThat(windowLocationOnScreen()).isEqualTo(p);
  }

  private Point windowLocationOnScreen() {
    return target().getLocationOnScreen();
  }
  
  @Override protected boolean targetBlocksMainWindow() { return true; }
  @Override protected boolean addTargetToWindow() { return false; }

  protected final FluentDimension windowSize() {
    return new FluentDimension(target().getSize());
  }

  protected abstract Component target();
  
  protected final WindowLikeFixture windowLikeFixture() { return (WindowLikeFixture)fixture(); }
}
