package org.fest.swing.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Robot {

  /**
   * Returns the <code>{@link BasicComponentPrinter}</code> used by this fixture.
   * @return the <code>ComponentPrinter</code> used by this fixture.
   */
  BasicComponentPrinter printer();

  /**
   * Returns the <code>{@link ComponentFinder}</code> used by this fixture.
   * @return the object responsible for GUI component lookup and user input simulation.
   */
  ComponentFinder finder();

  /**
   * Safely display a window with proper EDT synchronization. This method blocks until the <code>{@link Window}</code>
   * is showing and ready for input.
   * @param w the window to display.
   */
  void showWindow(Window w);

  /**
   * Safely display a window with proper EDT synchronization. This method blocks until the <code>{@link Window}</code>
   * is showing and ready for input.
   * @param w the window to display.
   * @param size the size of the window to display.
   */
  void showWindow(Window w, Dimension size);

  /**
   * <p>
   * Safely display a window with proper EDT synchronization. This method blocks until the window is showing. This
   * method will return even when the window is a modal dialog, since the show method is called on the event dispatch
   * thread. The window will be packed if the pack flag is set, and set to the given size if it is non-<code>null</code>.
   * </p>
   * Modal dialogs may be shown with this method without blocking.
   * @param w the window to display.
   * @param size the size of the window to display.
   * @param pack flag that indicates if the window should be packed or not. By packed we mean calling
   * <code>w.pack()</code>.
   */
  void showWindow(final Window w, final Dimension size, final boolean pack);

  /**
   * Posts a <code>{@link Runnable}</code> on the given component's event queue. Useful to ensure an operation happens
   * on the event dispatch thread.
   * @param c the component which event queue will be used.
   * @param action the <code>Runnable</code> to post in the event queue.
   */
  void invokeLater(Component c, Runnable action);

  /**
   * Runs the given <code>{@link Runnable}</code> on the event dispatch thread, but don't return until it's been run.
   * @param action the <code>Runnable</code> to run.
   */
  void invokeAndWait(Runnable action);

  /**
   * Posts a <code>{@link Runnable}</code> on the given component's event queue and wait for it to finish.
   * @param c the component which event queue will be used.
   * @param action the <code>Runnable</code> to post in the event queue.
   */
  void invokeAndWait(Component c, Runnable action);

  /**
   * Cleans up any used resources (keyboard, mouse, open windows and <code>{@link ScreenLock}</code>) used by this
   * robot.
   */
  void cleanUp();

  /**
   * Simulates a user clicking the given mouse button, the given times at the given position on the given
   * <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to click.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  void click(Component target, Point where, MouseButton button, int times);

  /** 
   * Simulates a user pressing a mouse button. 
   * @param button the button to press.
   */
  void mousePress(MouseButton button);

  /**
   * Simulates a user pressing the left mouse button on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to press the left mouse button.
   */
  void mousePress(Component target, Point where);

  /**
   * Simulates a user pressing the given mouse button on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to press the given mouse button.
   * @param button the mouse button to press.
   */
  void mousePress(Component target, Point where, MouseButton button);

  /**
   * Makes the mouse pointer show small quick jumpy movements on the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   */
  void jitter(Component c);

  /**
   * Simulates a user moving the mouse pointer to the center of the given <code>{@link Component}</code>.
   * @param target the given <code>Component</code>.
   */
  void mouseMove(Component target);

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given
   * <code>{@link Component}</code>.
   * @param target the given <code>Component</code>.
   * @param x horizontal coordinate relative to the given <code>Component</code>.
   * @param y vertical coordinate relative to the given <code>Component</code>.
   */
  void mouseMove(Component target, int x, int y);

  /**
   * Simulates a user entering the given text. Note that this method the key strokes to the component that has input
   * focus.
   * @param text the text to enter.
   */
  void enterText(String text);

  /**
   * Types the given character. Note that this method sends the key strokes to the component that has input focus.
   * @param character the character to type.
   */
  void type(char character);

  /**
   * Type the given keycode with the given modifiers. Modifiers is a mask from the available
   * <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   */
  void pressAndReleaseKey(int keyCode, int modifiers);

  /**
   * Simulates a user pressing and releasing the given keys. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   */
  void pressAndReleaseKeys(int... keyCodes);

  /**
   * Simulates a user pressing given key. This method does not affect the current focus.
   * @param keyCode the code of the key to press.
   * @see java.awt.event.KeyEvent
   */
  void pressKey(int keyCode);

  /**
   * Simulates a user releasing the given key. This method does not affect the current focus.
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   */
  void releaseKey(int keyCode);

  /**
   * Releases the left mouse button.
   */
  void releaseLeftMouseButton();

  /**
   * Releases any mouse button(s) used by the robot.
   */
  void releaseMouseButtons();

  /**
   * Wait for an idle AWT event queue. Note that this is different from the implementation of
   * <code>java.awt.Robot.waitForIdle()</code>, which may have events on the queue when it returns. Do <strong>NOT</strong>
   * use this method if there are animations or other continual refreshes happening, since in that case it may never
   * return.
   */
  void waitForIdle();

  /**
   * Indicates whether the robot is currently in a dragging operation.
   * @return <code>true</code> if the robot is currently in a dragging operation, <code>false</code> otherwise.
   */
  boolean isDragging();

  /** 
   * Indicates whether the given <code>{@link Component}</code> is ready for input. 
   * @param c the given <code>Component</code>.
   * @return <code>true</code> if the given <code>Component</code> is ready for input, <code>false</code> otherwise.
   */
  boolean isReadyForInput(Component c);

  /**
   * Simulates a user closing the given window.
   * @param w the window to close.
   */
  void close(Window w);

}