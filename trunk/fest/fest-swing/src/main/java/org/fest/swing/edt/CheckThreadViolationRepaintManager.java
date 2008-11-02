/*
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.fest.swing.edt;

import java.lang.ref.WeakReference;

import javax.swing.JComponent;
import javax.swing.RepaintManager;

import static javax.swing.SwingUtilities.isEventDispatchThread;

import static org.fest.assertions.Fail.fail;

/**
 * <p>
 * This class is used to detect Event Dispatch Thread rule violations.<br/>
 * See <a href="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How to Use Threads</a> for more info
 * </p>
 * <p>
 * This is a modification of original idea of Scott Delap.<br/>
 * Initial version of <code>ThreadCheckingRepaintManager</code> can be found here<br>
 * <a href="http://www.clientjava.com/blog/2004/08/20/1093059428000.html">Easily Find Swing Threading Mistakes</a>
 * </p>
 * <p>
 * Alex Ruiz modified to make a test fail if an EDT violation is detected.
 * </p>
 *
 * @author Scott Delap
 * @author Alexander Potochkin
 *
 * https://swinghelper.dev.java.net/
 */
public class CheckThreadViolationRepaintManager extends RepaintManager {

  /**
   * Creates a new <code>{@link CheckThreadViolationRepaintManager}</code> and sets it as the current repaint manager.
   * @return the created (and installed) repaint manager.
   * @see RepaintManager#setCurrentManager(RepaintManager)
   */
  public static CheckThreadViolationRepaintManager install() {
    CheckThreadViolationRepaintManager repaintManager = new CheckThreadViolationRepaintManager();
    setCurrentManager(repaintManager);
    return repaintManager;
  }

  // it is recommended to pass the complete check
  private boolean completeCheck = true;
  private WeakReference<JComponent> lastComponent;

  public CheckThreadViolationRepaintManager(boolean completeCheck) {
    this.completeCheck = completeCheck;
  }

  public CheckThreadViolationRepaintManager() {
    this(true);
  }

  public boolean isCompleteCheck() {
    return completeCheck;
  }

  public void setCompleteCheck(boolean completeCheck) {
    this.completeCheck = completeCheck;
  }

  @Override public synchronized void addInvalidComponent(JComponent component) {
    checkThreadViolations(component);
    super.addInvalidComponent(component);
  }

  @Override public void addDirtyRegion(JComponent component, int x, int y, int w, int h) {
    checkThreadViolations(component);
    super.addDirtyRegion(component, x, y, w, h);
  }

  private void checkThreadViolations(JComponent c) {
    if (!isEventDispatchThread() && (completeCheck || c.isShowing())) {
      boolean isRepaint = false;
      boolean fromSwing = false;
      boolean imageUpdate = false;
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      for (StackTraceElement st : stackTrace) {
        if (isRepaint && st.getClassName().startsWith("javax.swing.")) fromSwing = true;
        if (isRepaint && "imageUpdate".equals(st.getMethodName())) imageUpdate = true;
        if ("repaint".equals(st.getMethodName())) {
          isRepaint = true;
          fromSwing = false;
        }
      }
      // assuming it is java.awt.image.ImageObserver.imageUpdate(...)
      // image was asynchronously updated, that's ok
      if (imageUpdate) return;
      // no problems here, since repaint() is thread safe
      if (isRepaint && !fromSwing) return;
      // ignore the last processed component
      if (lastComponent != null && c == lastComponent.get()) { return; }
      lastComponent = new WeakReference<JComponent>(c);
      fail("EDT violation detected");
    }
  }
}