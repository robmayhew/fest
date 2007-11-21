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

import org.fest.swing.monitor.WindowMonitor;
import static org.fest.swing.util.Formatting.format;
import static org.fest.swing.util.Swing.*;
import static org.fest.util.Strings.concat;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.Collection;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

/**
 * Understands access to the current AWT hierarchy.
 * <p>
 * Adapted from <code>abbot.finder.AWTHierarchy</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ExistingHierarchy implements ComponentHierarchy {

  private static WindowMonitor windowMonitor = WindowMonitor.instance();

  final ParentFinder parentFinder = new ParentFinder();
  final ChildrenFinder childrenFinder = new ChildrenFinder();

  final Logger logger = Logger.getLogger(getClass().getName());

  /**
   * Returns the singleton instance of this class.
   * @return the singleton instance of this class.
   */
  public static ExistingHierarchy instance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {
    static final ExistingHierarchy INSTANCE = new ExistingHierarchy();
  }

  /**
   * Creates a new <code>{@link ExistingHierarchy}</code>
   */
  public ExistingHierarchy() {}

  /** ${@inheritDoc} */
  public Collection<? extends Container> roots() {
    return windowMonitor.rootWindows();
  }

  /** ${@inheritDoc} */
  public Container parentOf(Component c) {
    return parentFinder.parentOf(c);
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
   * Returns all descendents of interest of the given component.
   * @param c the given component.
   * @return all descendents of interest of the given component.
   */
  public Collection<Component> childrenOf(Component c) {
    return childrenFinder.childrenOf(c);
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
    } catch (Exception e) {
      logger.log(INFO, concat("Failed to dispose window ", format(w)), e);
    }
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
