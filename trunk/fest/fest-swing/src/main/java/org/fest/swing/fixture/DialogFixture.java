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
package org.fest.swing.fixture;

import java.awt.Dialog;
import java.awt.Dimension;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link Dialog}</code> and verification of the state of such
 * <code>{@link Dialog}</code>.
 * 
 * @author Alex Ruiz
 */
public class DialogFixture extends WindowFixture<Dialog> {

  /**
   * Creates a new </code>{@link DialogFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param dialogName the name of the <code>Dialog</code> to find.
   * @throws ComponentLookupException if a <code>Dialog</code> having a matching name could not be found. 
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public DialogFixture(String dialogName) {
    super(dialogName, Dialog.class);
  }
  
  /**
   * Creates a new </code>{@link DialogFixture}</code>.
   * @param robot performs simulation of user events on a <code>Dialog</code>.
   * @param dialogName the name of the <code>Dialog</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a dialog having a matching name could not be found. 
   */
  public DialogFixture(RobotFixture robot, String dialogName) {
    super(robot, dialogName, Dialog.class);
  }
  
  /**
   * Creates a new </code>{@link DialogFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Dialog</code> to be managed by this fixture.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public DialogFixture(Dialog target) {
    super(target);
  }
  
  /**
   * Creates a new </code>{@link DialogFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Dialog</code>.
   * @param target the <code>Dialog</code> to be managed by this fixture.
   */
  public DialogFixture(RobotFixture robot, Dialog target) {
    super(robot, target);
  }

  /**
   * Shows the <code>{@link Dialog}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final DialogFixture show() {
    return (DialogFixture)super.show();
  }
  
  /**
   * Shows the <code>{@link Dialog}</code> managed by this fixture, resized to the given size.
   * @param size the size to resize the managed <code>Dialog</code> to.
   * @return this fixture.
   */
  @Override public final DialogFixture show(Dimension size) {
    return (DialogFixture)super.show(size);
  }

  /**
   * Simulates a user clicking the <code>{@link Dialog}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final DialogFixture click() {
    return (DialogFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link Dialog}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final DialogFixture focus() {
    return (DialogFixture)super.focus();
  }

  /**
   * Simulates a user resizing horizontally the <code>{@link Dialog}</code> managed by this fixture.
   * @param width the width that the managed <code>Dialog</code> should have after being resized.
   * @return this fixture.
   */
  @Override public final DialogFixture resizeWidthTo(int width) {
    return (DialogFixture)super.resizeWidthTo(width);
  }

  /**
   * Simulates a user resizing vertically the <code>{@link Dialog}</code> managed by this fixture.
   * @param height the height that the managed <code>Dialog</code> should have after being resized.
   * @return this fixture.
   */
  @Override public final DialogFixture resizeHeightTo(int height) {
    return (DialogFixture)super.resizeHeightTo(height);
  }

  /**
   * Simulates a user resizing the <code>{@link Dialog}</code> managed by this fixture.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  @Override public final DialogFixture resizeTo(Dimension size) {
    return (DialogFixture)super.resizeTo(size);
  }

  /**
   * Asserts that the size of the <code>{@link Dialog}</code> managed by this fixture is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the managed <code>Dialog</code> is not equal to the given size. 
   */
  @Override public final DialogFixture requireSize(Dimension size) {
    return (DialogFixture)super.requireSize(size);
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link Dialog}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  @Override public final DialogFixture pressKeys(int... keyCodes) {
    return (DialogFixture)super.pressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link Dialog}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Dialog</code> is not visible.
   */
  @Override public final DialogFixture requireVisible() {
    return (DialogFixture)super.requireVisible();
  }
  
  /**
   * Asserts that the <code>{@link Dialog}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Dialog</code> is visible.
   */
  @Override public final DialogFixture requireNotVisible() {
    return (DialogFixture)super.requireNotVisible();
  }

  /**
   * Asserts that the <code>{@link Dialog}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Dialog</code> is disabled.
   */
  @Override public final DialogFixture requireEnabled() {
    return (DialogFixture)super.requireEnabled();
  }
  
  /**
   * Asserts that the <code>{@link Dialog}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Dialog</code> is enabled.
   */
  @Override public final DialogFixture requireDisabled() {
    return (DialogFixture)super.requireDisabled();
  }

  /**
   * Asserts that the <code>{@link Dialog}</code> managed by this fixture is modal.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Dialog</code> is not modal.
   */
  public final DialogFixture requireModal() {
    assertThat(target.isModal()).isTrue();
    return this;
  }
}
