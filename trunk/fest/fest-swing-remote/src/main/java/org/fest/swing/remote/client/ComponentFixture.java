/*
 * Created on Oct 20, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.remote.client;

import java.awt.Component;

import org.fest.swing.core.MouseButton;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.MouseClickInfo;
import org.fest.swing.remote.core.Response;

import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.remote.core.FindComponentRequest.findByName;


/**
 * Understands simulation of user events on a <code>{@link Component}</code> and verification of the state of such
 * <code>{@link Component}</code>.
 * @param <T> the type of <code>{@link Component}</code> that this fixture can manage.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ComponentFixture<T extends Component> {

  private final Connection connection;
  
  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param connection 
   * @param name 
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public ComponentFixture(Connection connection, String name) {
    this.connection = connection;
    findComponentByName(name);
  }

  private void findComponentByName(String name) {
    Response response = connection.send(findByName(name));
    if (!response.success()) {
      Exception cause = response.cause();
    }
  }
  
  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> click();
  
  /**
   * Simulates a user right-clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> rightClick();
  
  /**
   * Simulates a user doble-clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> doubleClick();

  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doClick() {
    return doClick(LEFT_BUTTON, 1);
  }

  /**
   * Simulates a user doble-clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doDoubleClick() {
    return doClick(LEFT_BUTTON, 2);
  }

  /**
   * Simulates a user right-clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doRightClick() {
    return doClick(RIGHT_BUTTON, 1);
  }
  
  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doClick(MouseClickInfo mouseClickInfo) {
    return doClick(mouseClickInfo.button(), mouseClickInfo.times());
  }

  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @param button the mouse button to click.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doClick(MouseButton button) {
    return doClick(button, 1);
  }

  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doClick(MouseButton button, int times) {
//    robot.click(target, button, times);
    return this;
  }
}
