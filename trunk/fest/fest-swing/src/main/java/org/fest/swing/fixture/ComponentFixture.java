/*
 * Created on Apr 10, 2007
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

/**
 * Understands simulation of user events and state verification of a <code>{@link Component}</code>.
 * @param <T> the type of component handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public interface ComponentFixture<T extends Component> {

  /**
   * Simulates a user clicking the target component.
   * @return this fixture.
   */
  ComponentFixture<T> click();

  /**
   * Gives the focus to the target component.
   * @return this fixture.
   */
  ComponentFixture<T> focus();
  
  /**
   * Asserts that the target component is visible.
   * @return this fixture.
   * @throws AssertionError if the target component is not visible.
   */
  ComponentFixture<T> shouldBeVisible();
  
  /**
   * Asserts that the target component is not visible.
   * @return this fixture.
   * @throws AssertionError if the target component is visible.
   */
  ComponentFixture<T> shouldNotBeVisible();
}
