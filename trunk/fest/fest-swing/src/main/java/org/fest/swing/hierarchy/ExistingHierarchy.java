/*
 * Created on Oct 19, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import static java.util.logging.Level.WARNING;

import static org.fest.swing.util.Formatting.format;
import static org.fest.swing.util.Swing.isAppletViewer;
import static org.fest.swing.util.Swing.isSharedInvisibleFrame;
import static org.fest.swing.util.Swing.runInEventThreadAndWait;

import static org.fest.util.Collections.list;
import static org.fest.util.Strings.concat;

import org.fest.swing.monitor.WindowMonitor;

/**
 * Understands access to the current AWT hierarchy.
 * <p>
 * Adapted from <code>abbot.finder.AWTHierarchy</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class ExistingHierarchy implements ComponentHierarchy {

  private static Logger logger = Logger.getLogger(ExistingHierarchy.class.getName());
  
  private static WindowMonitor windowMonitor = WindowMonitor.instance();
  
  /** ${@inheritDoc} */
  public Collection<Window> rootWindows() {
    return windowMonitor.rootWindows();
  }

  /** ${@inheritDoc} */
  public Container parentOf(Component c) {
    Container p = c.getParent();
    if (p == null && c instanceof JInternalFrame) p = parentOf((JInternalFrame)c);
    return p;
  }

  private Container parentOf(JInternalFrame internalFrame) {
    // From Abbot: workaround for bug in JInternalFrame: COMPONENT_HIDDEN is sent before the desktop icon is set, so
    // JInternalFrame.getDesktopPane will throw a NPE if called while dispatching that event. Reported against 1.4.x.
    JInternalFrame.JDesktopIcon icon = internalFrame.getDesktopIcon();
    if (icon != null) return icon.getDesktopPane();
    return null;
  }
  
  /**
   * Returns whether the given component is reachable from any of the root windows. The default is to consider all
   * components to be contained in the hierarchy, whether they are reachable or not.
   * @param c the given component.
   * @return <code>true</code>.
   */
  public boolean contains(Component c) {
    return true;
  }

  /**
   * Returns all descendents of interest of the given component. This includes owned windows for
   * <code>{@link Window}</code>s, children for <code>{@link Container}</code>s.
   * @param c the given component.
   * @return all descendents of interest of the given component.
   */
  public Collection<Component> subComponentsOf(Component c) {
    if (!(c instanceof Container)) return empty();
    Container container = (Container)c;
    List<Component> children = new ArrayList<Component>();
    children.addAll(list(container.getComponents()));
    children.addAll(notExplicitChildrenOf(container));
    return children;
  }

  private Collection<Component> notExplicitChildrenOf(Container c) {
    if (c instanceof JDesktopPane) return internalFramesFromIcons(c);
    if (c instanceof JMenu) return popupMenuIn((JMenu)c);
    if (c instanceof Window) return ownedWindowsIn((Window)c);
    return empty();
  }

  private Collection<Component> popupMenuIn(JMenu m) {
    return components(m.getPopupMenu());
  }
  
  private Collection<Component> ownedWindowsIn(Window w) {
    return components(w.getOwnedWindows());
  }
  
  private Collection<Component> components(Component...components) {
    return list(components);
  }
  
  private Collection<Component> empty() {
    return new ArrayList<Component>();
  }
  
  // From Abbot: add iconified frames, which are otherwise unreachable. For consistency, they are still considerered 
  // children of the desktop pane.
  private Collection<Component> internalFramesFromIcons(Container container) {
    List<Component> frames = new ArrayList<Component>();
    for (Component child : container.getComponents()) {
      if (child instanceof JInternalFrame.JDesktopIcon) {
        JInternalFrame frame = ((JInternalFrame.JDesktopIcon)child).getInternalFrame();
        if (frame != null) frames.add(frame);
        continue;
      }
      // OSX puts icons into a dock; handle icon manager situations here
      if (child instanceof Container) frames.addAll(internalFramesFromIcons((Container)child));
    }
    return frames;
  }

  /**
   * Properly dispose of the given window, making it and its native resources available for garbage collection.
   * @param w the window to dispose.
   */
  public void dispose(Window w) {
    if (isAppletViewer(w)) return;
    logger.info(concat("Disposing ", w));
    for (Window owned : w.getOwnedWindows()) dispose(owned);
    if (isSharedInvisibleFrame(w)) return;
    try {
      runInEventThreadAndWait(disposerFor(w));
    } catch (Exception ignored) {}
  }

  private Runnable disposerFor(final Window w) {
    return new Runnable() {
      public void run() {
        try {
          w.dispose();
        } catch (Throwable e) {
          logger.log(WARNING, concat("Ignoring exception thrown when disposing the window ", format(w)), e);
        }
      }
    };
  }
}
