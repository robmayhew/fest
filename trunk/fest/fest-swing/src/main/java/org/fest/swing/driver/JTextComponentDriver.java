/*
 * Created on Jan 21, 2008
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
package org.fest.swing.driver;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

import static java.lang.Math.*;
import static java.lang.String.valueOf;
import static javax.swing.text.DefaultEditorKit.*;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.Platform.IS_OS_X;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JTextComponent}</code>. Unlike
 * <code>JTextComponentFixture</code>, this driver only focuses on behavior present only in
 * <code>{@link JTextComponent}</code>s. This class is intended for internal use only.
 *
 * @author Alex Ruiz
 */
public class JTextComponentDriver extends JComponentDriver {

  /**
   * Creates a new </code>{@link JTextComponentDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTextComponentDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Types the given text into the <code>{@link JTextComponent}</code>, replacing any existing text already there. If
   * the empty <code>String</code> or <code>null</code> is given, simply removes all existing text.
   * @param textBox the target <code>JTextComponent</code>.
   * @param text the text to enter.
   */
  public final void replaceText(JTextComponent textBox, String text) {
    scrollToVisible(textBox, 0);
    invokeAction(textBox, selectAllAction);
    if (isEmpty(text) && !isEmpty(textBox.getText())) {
        invokeAction(textBox, deletePrevCharAction);
        return;
    }
    robot.focus(textBox);
    robot.enterText(text);
  }

  /**
   * Select the given text range.
   * @param textBox the target <code>JTextComponent</code>.
   * @param start the starting index of the selection.
   * @param end the ending index of the selection.
   * @throws ActionFailedException if the selecting the text in the given range fails.
   */
  public final void selectText(JTextComponent textBox, int start, int end) {
    startSelection(textBox, start);
    endSelection(textBox, end);
    verifySelectionMade(textBox, start, end);
  }

  private void startSelection(JTextComponent textBox, int index) {
    // From Abbot: Equivalent to JTextComponent.setCaretPosition(int), but operates through the UI.
    avoidAutomaticDragAndDrop(textBox);
    robot.mousePress(textBox, scrollToVisible(textBox, index));
  }

  private void avoidAutomaticDragAndDrop(final JTextComponent textBox) {
    if (textBox.getSelectionStart() == textBox.getSelectionEnd()) return;
    robot.invokeAndWait(new SetAndMoveCaretPositionTask(textBox, 0));
  }

  private static class SetAndMoveCaretPositionTask implements Runnable {
    private final JTextComponent target;
    private final int position;

    SetAndMoveCaretPositionTask(JTextComponent target, int position) {
      this.target = target;
      this.position = position;
    }

    public void run() {
      target.setCaretPosition(position);
      target.moveCaretPosition(position);
    }
  }

  private void endSelection(JTextComponent textBox, int index) {
    // From Abbot: Equivalent to JTextComponent.moveCaretPosition(int), but operates through the UI.
    Point where = scrollToVisible(textBox, index);
    robot.mouseMove(textBox, where.x, where.y);
    if (IS_OS_X) pause(75);
    robot.releaseLeftMouseButton();
  }

  /**
   * Move the pointer to the location of the given index. Takes care of auto-scrolling through text.
   * @param textBox the target <code>JTextComponent</code>.
   * @param index the given location.
   * @return the position of the pointer after being moved.
   * @throws ActionFailedException if it was not possible to scroll to the location of the given index.
   */
  private Point scrollToVisible(JTextComponent textBox, int index) {
    Rectangle indexLocation = locationOf(textBox, index);
    if (isRectangleVisible(textBox, indexLocation)) return centerOf(indexLocation);
    scrollToVisible(textBox, indexLocation);
    indexLocation = locationOf(textBox, index);
    if (isRectangleVisible(textBox, indexLocation)) return centerOf(indexLocation);
    throw actionFailure(concat(
        "Unable to make visible the location of the index '", valueOf(index),
        "' by scrolling the point (", formatOriginOf(indexLocation), ") on ", format(textBox)));
  }

  private Rectangle locationOf(JTextComponent textBox, int index) {
    Rectangle r = null;
    try {
      r = textBox.modelToView(index);
    } catch (BadLocationException e) {
      throw actionFailure(concat("Unable to get location for index '", valueOf(index), "' in ", format(textBox)));
    }
    if (r != null) return r;
    throw actionFailure(concat("Text component", format(textBox)," has zero size"));
  }

  private boolean isRectangleVisible(JTextComponent textBox, Rectangle r) {
    return textBox.getVisibleRect().contains(r.x, r.y);
  }

  private String formatOriginOf(Rectangle r) {
    return concat(valueOf(r.x), ",", valueOf(r.y));
  }

  private void scrollToVisible(JTextComponent textBox, Rectangle r) {
    super.scrollToVisible(textBox, r);
    // Taken from JComponent
    if (isVisible(textBox, r)) return;
    scrollToVisibleIfIsTextField(textBox, r);
  }

  private void scrollToVisibleIfIsTextField(JTextComponent textBox, Rectangle r) {
    if (!(textBox instanceof JTextField)) return;
    Point origin = origin(textBox);
    Container parent = textBox.getParent();
    while (parent != null && !(parent instanceof JComponent) && !(parent instanceof CellRendererPane)) {
      addRectangleCoordinatesToPoint(parent.getBounds(), origin);
      parent = parent.getParent();
    }
    if (parent == null || parent instanceof CellRendererPane) return;
    super.scrollToVisible((JComponent)parent, rectangleWithPointAddedToCoordinates(origin, r));
  }

  private Point origin(JTextComponent textBox) {
    return new Point(textBox.getX(), textBox.getY());
  }

  private void addRectangleCoordinatesToPoint(Rectangle r, Point p) {
    p.x += r.x;
    p.y += r.y;
  }

  private Rectangle rectangleWithPointAddedToCoordinates(Point p, Rectangle r) {
    Rectangle destination = new Rectangle(r);
    destination.x += p.x;
    destination.y += p.y;
    return destination;
  }

  private Point centerOf(Rectangle r) {
    return new Point(r.x + r.width / 2, r.y + r.height / 2);
  }

  private void verifySelectionMade(JTextComponent textBox, int start, int end) {
    int actualStart = textBox.getSelectionStart();
    int actualEnd = textBox.getSelectionEnd();
    if (actualStart == min(start, end) && actualEnd == max(start, end)) return;
    throw actionFailure(concat(
        "Unable to select text using indices '", valueOf(start), "' and '", valueOf(end),
        ", current selection starts at '", valueOf(actualStart), "' and ends at '", valueOf(actualEnd), "'"));
  }
}
