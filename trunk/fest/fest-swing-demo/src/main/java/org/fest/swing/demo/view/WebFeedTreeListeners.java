/*
 * Created on Apr 24, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.demo.view;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.tree.TreePath;

import org.fest.swing.demo.view.WebFeedTree.FolderNode;
import org.fest.swing.demo.view.WebFeedTree.WebFeedNode;

/**
 * Understands utility methods related to event listeners in a <code>{@link WebFeedTree}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class WebFeedTreeListeners  {

  static FolderNode folderNodeFrom(MouseEvent e) {
    Object value = selectedTreeValueFrom(e);
    if (!(value instanceof FolderNode)) return null;
    return (FolderNode)value;
  }
  
  static WebFeedNode webFeedNodeFrom(MouseEvent e) {
    Object value = selectedTreeValueFrom(e);
    if (!(value instanceof WebFeedNode)) return null;
    return (WebFeedNode)value;
  }

  static boolean isSelectionRootNode(MouseEvent e) {
    Object value = selectedTreeValueFrom(e);
    if (value == null) return false;
    WebFeedTree tree = webFeedTreeFrom(e);
    return value.equals(tree.getModel().getRoot()); 
  }
  
  private static Object selectedTreeValueFrom(MouseEvent e) {
    WebFeedTree tree = webFeedTreeFrom(e);
    if (tree == null) return null;
    int x = e.getX();
    int y = e.getY();
    int row = tree.getRowForLocation(x, y);
    TreePath path = tree.getPathForRow(row);
    if (path == null) return null;
    return path.getLastPathComponent();
  }
  
  static WebFeedTree webFeedTreeFrom(MouseEvent e) {
    Component c = e.getComponent();
    if (!(c instanceof WebFeedTree)) return null;
    return (WebFeedTree)c;
  }
  
  
  
  private WebFeedTreeListeners() {}
}
