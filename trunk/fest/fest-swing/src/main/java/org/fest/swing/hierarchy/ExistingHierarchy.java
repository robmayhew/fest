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

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import static org.fest.util.Collections.list;

import org.fest.swing.monitor.WindowMonitor;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz 
 */
public final class ExistingHierarchy implements ComponentHierarchy {

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
  
  /** ${@inheritDoc} */
  public boolean contains(Component c) {
    return false;
  }

  /** ${@inheritDoc} */
  public Collection<Component> subComponentsOf(Component c) {
    if (!(c instanceof Container)) return empty();
    Container container = (Container)c;
    List<Component> children = new ArrayList<Component>();
    children.addAll(list(container.getComponents()));
    children.addAll(notExplicitChildrenOf(container));
    return children;
  }

  private Collection<Component> notExplicitChildrenOf(Container c) {
    if (c instanceof JMenu) return popupMenuIn((JMenu)c);
    if (c instanceof Window) return ownedWindowsIn((Window)c);
    if (c instanceof JDesktopPane) 
      // Add iconified frames, which are otherwise unreachable. For consistency, they are still considerered children of 
      // the desktop pane.
      return internalFramesFromIcons(c);
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

  /** ${@inheritDoc} */
  public void dispose(final Window w) {
  }
}
