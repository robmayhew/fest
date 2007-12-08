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

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link Dialog}</code> and verification of the state of such
 * <code>{@link Dialog}</code>.
 * 
 * @author Alex Ruiz
 */
public class DialogFixture extends WindowFixture<Dialog> {

  /**
   * Creates a new <code>{@link DialogFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param dialogName the name of the <code>Dialog</code> to find.
   * @throws ComponentLookupException if a <code>Dialog</code> having a matching name could not be found.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public DialogFixture(String dialogName) {
    super(dialogName, Dialog.class);
  }
  
  /**
   * Creates a new <code>{@link DialogFixture}</code>.
   * @param robot performs simulation of user events on a <code>Dialog</code>.
   * @param dialogName the name of the <code>Dialog</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a dialog having a matching name could not be found.
   */
  public DialogFixture(RobotFixture robot, String dialogName) {
    super(robot, dialogName, Dialog.class);
  }
  
  /**
   * Creates a new <code>{@link DialogFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Dialog</code> to be managed by this fixture.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public DialogFixture(Dialog target) {
    super(target);
  }
  
  /**
   * Creates a new <code>{@link DialogFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Dialog</code>.
   * @param target the <code>Dialog</code> to be managed by this fixture.
   */
  public DialogFixture(RobotFixture robot, Dialog target) {
    super(robot, target);
  }

  /**
   * Shows this fixture's <code>{@link Dialog}</code>.
   * @return this fixture.
   */
  public final DialogFixture show() {
    return (DialogFixture)doShow();
  }
  
  /**
   * Shows this fixture's <code>{@link Dialog}</code>, resized to the given size.
   * @param size the size to resize this fixture's <code>Dialog</code> to.
   * @return this fixture.
   */
  public final DialogFixture show(Dimension size) {
    return (DialogFixture)doShow(size);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Dialog}</code>.
   * @return this fixture.
   */
  public final DialogFixture click() {
    return (DialogFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Dialog}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final DialogFixture click(MouseButton button) {
    return (DialogFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Dialog}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final DialogFixture click(MouseClickInfo mouseClickInfo) {
    return (DialogFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link Dialog}</code>.
   * @return this fixture.
   */
  public final DialogFixture rightClick() {
    return (DialogFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link Dialog}</code>.
   * @return this fixture.
   */
  public final DialogFixture doubleClick() {
    return (DialogFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link Dialog}</code>.
   * @return this fixture.
   */
  public final DialogFixture focus() {
    return (DialogFixture)doFocus();
  }

  /**
   * Simulates a user resizing horizontally this fixture's <code>{@link Dialog}</code>.
   * @param width the width that this fixture's <code>Dialog</code> should have after being resized.
   * @return this fixture.
   */
  public final DialogFixture resizeWidthTo(int width) {
    return (DialogFixture)doResizeWidthTo(width);
  }

  /**
   * Simulates a user resizing vertically this fixture's <code>{@link Dialog}</code>.
   * @param height the height that this fixture's <code>Dialog</code> should have after being resized.
   * @return this fixture.
   */
  public final DialogFixture resizeHeightTo(int height) {
    return (DialogFixture)doResizeHeightTo(height);
  }

  /**
   * Simulates a user resizing this fixture's <code>{@link Dialog}</code>.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  public final DialogFixture resizeTo(Dimension size) {
    return (DialogFixture)doResizeTo(size);
  }

  /**
   * Asserts that the size of this fixture's <code>{@link Dialog}</code> is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's <code>Dialog</code> is not equal to the given size. 
   */
  public final DialogFixture requireSize(Dimension size) {
    return (DialogFixture)assertEqualSize(size);
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link Dialog}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final DialogFixture pressAndReleaseKeys(int... keyCodes) {
    return (DialogFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link Dialog}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final DialogFixture pressKey(int keyCode) {
    return (DialogFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link Dialog}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final DialogFixture releaseKey(int keyCode) {
    return (DialogFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link Dialog}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Dialog</code> is not visible.
   */
  public final DialogFixture requireVisible() {
    return (DialogFixture)assertVisible();
  }
  
  /**
   * Asserts that this fixture's <code>{@link Dialog}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Dialog</code> is visible.
   */
  public final DialogFixture requireNotVisible() {
    return (DialogFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link Dialog}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Dialog</code> is disabled.
   */
  public final DialogFixture requireEnabled() {
    return (DialogFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link Dialog}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>Dialog</code> is never enabled.
   */
  public final DialogFixture requireEnabled(Timeout timeout) {
    return (DialogFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link Dialog}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Dialog</code> is enabled.
   */
  public final DialogFixture requireDisabled() {
    return (DialogFixture)assertDisabled();
  }

  /**
   * Asserts that this fixture's <code>{@link Dialog}</code> is modal.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Dialog</code> is not modal.
   */
  public final DialogFixture requireModal() {
    assertThat(target.isModal()).as(formattedPropertyName("modal")).isTrue();
    return this;
  }
}
