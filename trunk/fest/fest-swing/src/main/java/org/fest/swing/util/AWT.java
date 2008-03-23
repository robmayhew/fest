/*
 * Created on Jan 27, 2008
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
package org.fest.swing.util;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;

import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;

import static javax.swing.SwingUtilities.*;

import static org.fest.reflect.core.Reflection.staticField;
import static org.fest.util.Strings.*;

/**
 * Understands utility methods related to AWT.
 *
 * @author Alex Ruiz
 */
public class AWT {

  private static ComponentHierarchy hierarchy = new ExistingHierarchy();
  
  private static final String APPLET_APPLET_VIEWER_CLASS = "sun.applet.AppletViewer";
  private static final String ROOT_FRAME_CLASSNAME = concat(SwingUtilities.class.getName(), "$");

  /**
   * Returns a point at the center of the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   * @return a point at the center of the given <code>Component</code>.
   */
  public static Point centerOf(Component c) {
    return new Point(c.getWidth() / 2, c.getHeight() / 2);
  }

  /**
   * Returns the insets of the given <code>{@link Container}</code>, or an empty one if no insets can be found.
   * @param c the given <code>Container</code>.
   * @return the insets of the given <code>Container</code>, or an empty one if no insets can be found.
   */
  public static Insets insetsFrom(Container c) {
    try {
      Insets insets = c.getInsets();
      if (insets != null) return insets;
    } catch (Exception e) {}
    return new Insets(0, 0, 0, 0);
  }
  
  /**
   * Returns <code>true</code> if the given component is an Applet viewer.
   * @param c the component to check.
   * @return <code>true</code> if the given component is an Applet viewer, <code>false</code> otherwise.
   */
  public static boolean isAppletViewer(Component c) {
    return c != null && APPLET_APPLET_VIEWER_CLASS.equals(c.getClass().getName());
  }

  /**
   * Returns whether the given component is the default Swing hidden frame.
   * @param c the component to check.
   * @return <code>true</code> if the given component is the default hidden frame, <code>false</code> otherwise.
   */
  public static boolean isSharedInvisibleFrame(Component c) {
    if (c == null) return false;
    // Must perform an additional check, since applets may have their own version in their AppContext
    return c instanceof Frame
        && (c == JOptionPane.getRootFrame() || c.getClass().getName().startsWith(ROOT_FRAME_CLASSNAME));
  }

  /**
   * If the current thread is the AWT event thread, this method will simple execute the <code>{@link Runnable}</code>,
   * otherwise the <code>{@link Runnable}</code> will be executed synchronously, blocking until all pending AWT events
   * have been processed and <code>r.run()</code> returns.
   * @param r the <code>Runnable</code> to execute.
   * @exception InterruptedException if we're interrupted while waiting for the event dispatching thread to finish
   *            executing <code>r.run()</code>.
   * @exception InvocationTargetException if an exception is thrown while running <code>r</code>.
   * @see SwingUtilities#isEventDispatchThread()
   * @see SwingUtilities#invokeAndWait(Runnable)
   */
  public static void runInEventThreadAndWait(Runnable r) throws InterruptedException, InvocationTargetException {
    if (isEventDispatchThread()) {
      r.run();
      return;
    }
    invokeAndWait(r);
  }

  /**
   * Returns the name of the given component. If the component is <code>null</code>, this method will return
   * <code>null</code>.
   * @param c the given component.
   * @return the name of the given component, or <code>null</code> if the component is <code>null</code>.
   */
  public static String quoteNameOf(Component c) {
    if (c == null) return null;
    return quote(c.getName());
  }

  /**
   * Similar to <code>{@link javax.swing.SwingUtilities#getWindowAncestor(Component)}</code>, but returns the
   * <code>{@link Component}</code> itself if it is a <code>{@link Window}</code>, or the invoker's
   * <code>Window</code> if on a pop-up.
   * @param c the <code>Component</code> to get the <code>Window</code> ancestor of.
   * @return the <code>Window</code> ancestor of the given <code>Component</code>, the <code>Component</code> itself
   * if it is a <code>Window</code>, or the invoker's <code>Window</code> if on a pop-up.
   */
  public static Window ancestorOf(Component c) {
    if (c == null) return null;
    if (c instanceof Window) return (Window) c;
    if (c instanceof MenuElement) {
      Component invoker = invokerOf(c);
      if (invoker != null) return ancestorOf(invoker);
    }
    return ancestorOf(hierarchy.parentOf(c));
  }

  /**
   * Returns the invoker, if any, of the given <code>{@link Component}</code>; or <code>null</code>, if the
   * <code>Component</code> is not on a pop-up of any sort.
   * @param c the given <code>Component</code>.
   * @return the invoker, if any, of the given <code>Component</code>; or <code>null</code>, if the
   *         <code>Component</code> is not on a pop-up of any sort.
   */
  public static Component invokerOf(Component c) {
    if (c instanceof JPopupMenu) return ((JPopupMenu) c).getInvoker();
    Container parent = c.getParent();
    return parent != null ? invokerOf(parent) : null;
  }

  /**
   * Safe version of <code>{@link Component#getLocationOnScreen}</code>, which avoids lockup if an AWT pop-up menu is
   * showing. The AWT pop-up holds the AWT tree lock when showing, which lock is required by
   * <code>{@link Component#getLocationOnScreen}.</code>
   * @param c the given <code>Component</code>.
   * @return the a point specifying the <code>Component</code>'s top-left corner in the screen's coordinate space, or
   *         <code>null</code>, if the <code>Component</code> is not showing on the screen.
   */
  public static Point locationOnScreenOf(Component c) {
    if (!isAWTTreeLockHeld()) new Point(c.getLocationOnScreen());
    if (!c.isShowing()) return null;
    Point location = new Point(c.getLocation());
    if (c instanceof Window) return location;
    Container parent = c.getParent();
    if (parent == null) return null;
    Point parentLocation = locationOnScreenOf(parent);
    location.translate(parentLocation.x, parentLocation.y);
    return location;
  }

  /**
   * Returns the focus owner.
   * @return the focus owner.
   */
  @SuppressWarnings("unchecked") 
  public static Component focusOwner() {
    try {
      return staticField("focusOwner").ofType(Component.class).in(KeyboardFocusManager.class).get();
    } catch (Exception e) {
      Component focus = null;
      for (Container c : new ExistingHierarchy().roots()) {
        if (!(c instanceof Window)) continue;
        Window w = (Window)c;
        if (w.isShowing() && (focus = focusOwner(w)) != null) break;
      }
      return focus;
    }
  }

  private static Component focusOwner(Window w) {
    Component focus = w.getFocusOwner();
    if (focus != null) return focus;
    Window[] owned = w.getOwnedWindows();
    for (int i = 0; i < owned.length; i++) 
      if ((focus = owned[i].getFocusOwner()) != null) return focus;
    return focus;
  }
  
  /**
   * Indicates whether the AWT Tree Lock is currently held.
   * @return <code>true</code> if the AWT Tree Lock is currently held, <code>false</code> otherwise.
   */
  public static boolean isAWTTreeLockHeld() {
    Frame[] frames = Frame.getFrames();
    if (frames.length == 0) return false;
    // From Abbot: Hack based on 1.4.2 java.awt.PopupMenu implementation, which blocks the event dispatch thread while
    // the pop-up is visible, while holding the AWT tree lock.
    // Start another thread which attempts to get the tree lock.
    // If it can't get the tree lock, then there is a pop-up active in the current tree.
    // Any component can provide the tree lock.
    ThreadStateChecker checker = new ThreadStateChecker(frames[0].getTreeLock());
    try {
      checker.start();
      // wait a little bit for the checker to finish
      if (checker.isAlive()) checker.join(100);
      return checker.isAlive();
    } catch (InterruptedException e) {
      return false;
    }
  }

  // Try to lock the AWT tree lock; returns immediately if it can
  private static class ThreadStateChecker extends Thread {
    public boolean started;
    private final Object lock;

    public ThreadStateChecker(Object lock) {
      super("Thread state checker");
      setDaemon(true);
      this.lock = lock;
    }

    @Override public synchronized void start() {
      super.start();
      try {
        wait(30000);
      } catch (InterruptedException e) {}
    }

    @Override public void run() {
      synchronized (this) {
        started = true;
        notifyAll();
      }
      synchronized (lock) {
        setName(super.getName()); // dummy operation
      }
    }
  }

  private AWT() {}
}
