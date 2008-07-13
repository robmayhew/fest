/*
 * Created on Jul 8, 2008
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

import org.fest.swing.core.MouseButton;

/**
 * Understands simulation of mouse input on a GUI component.
 *
 * @author Alex Ruiz 
 */
public interface MouseInputSimulationFixture {

  /**
   * Simulates a user clicking this fixture's GUI component.
   * @return this fixture.
   */
  MouseInputSimulationFixture click();

  /**
   * Simulates a user clicking this fixture's GUI component.
   * @param button the button to click.
   * @return this fixture.
   */
  MouseInputSimulationFixture click(MouseButton button);

  /**
   * Simulates a user clicking this fixture's GUI component.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  MouseInputSimulationFixture click(MouseClickInfo mouseClickInfo);

  /**
   * Simulates a user double-clicking this fixture's GUI component.
   * @return this fixture.
   */
  MouseInputSimulationFixture doubleClick();

  /**
   * Simulates a user right-clicking this fixture's GUI component.
   * @return this fixture.
   */
  MouseInputSimulationFixture rightClick();
}