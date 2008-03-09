/*
 * Created on Mar 9, 2008
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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTree;

import org.fest.swing.demo.model.Folder;

import static org.fest.swing.demo.view.Icons.*;
import static org.fest.util.Arrays.array;

/**
 * Understands the tree containing all web feeds.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class WebFeedTree extends JXTree {

  private static final long serialVersionUID = 1L;

  private final DefaultMutableTreeNode root;
  private final DefaultTreeModel model;
  
  WebFeedTree() {
    root = new DefaultMutableTreeNode("Root");
    model = new DefaultTreeModel(root);
    setModel(model);
    setRootVisible(false);
    setOpenIcon(FOLDER_OPEN_ICON);
    setClosedIcon(FOLDER_CLOSE_ICON);
  }
  
  void addContent(Object content) {
    if (content instanceof Folder) addFolder((Folder)content);
  }
  
  void addFolder(Folder folder) {
    DefaultMutableTreeNode folderNode = new FolderNode(folder);
    root.add(folderNode);
    model.reload();
    setSelectionPath(new TreePath(array(root, folderNode)));
  }

  private static class FolderNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;
    
    final Folder folder;

    FolderNode(Folder folder) {
      super(folder.name());
      this.folder = folder;
      setAllowsChildren(true);
    }

    /** @see javax.swing.tree.DefaultMutableTreeNode#isLeaf() */
    @Override public boolean isLeaf() {
      return false;
    }
  }
}
