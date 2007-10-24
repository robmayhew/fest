/*
 * Created on Oct 24, 2007
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * Understands how to find children components in a <code>{@link JDesktopPane}</code>.
 *
 * @author Yvonne Wang
 */
final class JDesktopPaneChildrenFinder implements ChildrenFinderStrategy<JDesktopPane> {

  /**
   * Returns the non-explicit children of the given {@link JDesktopPane}. In the case of 
   * <code>{@link JDesktopPane}</code>s, internal frames are considered non-explicit children.
   * @param p the desktop pane whose children we are looking for.
   * @return a collection containing the non-explicit children found.
   */
  public Collection<Component> nonExplicitChildrenOf(JDesktopPane p) {
    return internalFramesFromIcons(p);
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
}
