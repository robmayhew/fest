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

import static org.fest.swing.demo.view.Icons.FOLDER_SMALL_ICON;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.*;

import org.fest.swing.demo.model.Folder;
import org.fest.swing.demo.model.WebFeed;
import org.jdesktop.swingx.JXTree;

/**
 * Understands the tree containing all web feeds.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class WebFeedTree extends JXTree {

  private static final long serialVersionUID = 1L;

  private static final String TREE_ROOT_KEY = "tree.root";

  private final DefaultMutableTreeNode root;
  private final DefaultTreeModel model;

  private final NodeModelComparator comparator = new NodeModelComparator();
  private final Map<String, FolderNode> folderNodes = new HashMap<String, FolderNode>();
  private final MainFrame mainFrame;

  WebFeedTree(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    root = new DefaultMutableTreeNode(i18n().message(TREE_ROOT_KEY));
    model = new DefaultTreeModel(root);
    setModel(model);
    setName("feeds");
    setRootVisible(true);
    setOpenIcon(FOLDER_SMALL_ICON);
    setClosedIcon(FOLDER_SMALL_ICON);
    setUpDragAndDrop();
    setUpPopupMenus();
  }

  private void setUpDragAndDrop() {
    setDragEnabled(true);
    setTransferHandler(new WebFeedTreeDnDHandler());
  }

  private void setUpPopupMenus() {
    WebFeedTreeRootPopupMenuMouseListener.attachTo(this);
    WebFeedTreeFolderPopupMenuMouseListener.attachTo(this);
  }

  void addContent(Object content) {
    if (content instanceof WebFeed) addWebFeed((WebFeed)content);
    if (content instanceof Folder) addFolder((Folder)content);
  }

  void addWebFeed(WebFeed webFeed) {
    FolderNode folderNode = folderFor(webFeed);
    WebFeedNode webFeedNode = new WebFeedNode(webFeed);
    MutableTreeNode parent = (folderNode != null) ? folderNode : root;
    int insertIndex = indexForNewNodeInsertion(parent, webFeedNode);
    model.insertNodeInto(webFeedNode, parent, insertIndex);
    selectNode(webFeedNode);
  }

  private FolderNode folderFor(WebFeed webFeed) {
    String folderName = folderNameFor(webFeed);
    if (folderName == null) return null;
    if (folderNodes.containsKey(folderName))
      return folderNodes.get(folderName);
    return addFolder(new Folder(folderName));
  }

  private String folderNameFor(WebFeed webFeed) {
    Folder folder = webFeed.folder();
    if (folder == null) return null;
    return folder.name();
  }

  private FolderNode addFolder(Folder folder) {
    String folderName = folder.name();
    if (folderNodes.containsKey(folderName)) return folderNodes.get(folderName);
    FolderNode folderNode = new FolderNode(folder);
    addFolderNode(folderNode);
    return folderNode;
  }

  private void addFolderNode(FolderNode folderNode) {
    int insertIndex = indexForNewNodeInsertion(root, folderNode);
    model.insertNodeInto(folderNode, root, insertIndex);
    selectNode(folderNode);
    folderNodes.put(folderNode.folder.name(), folderNode);
  }

  private int indexForNewNodeInsertion(TreeNode parent, TreeNode nodeToInsert) {
    int childCount = parent.getChildCount();
    if (childCount == 0) return 0;
    if (childCount == 1) {
      int compareToFirst = comparator.compare(nodeToInsert, parent.getChildAt(0));
      return compareToFirst < 0 ? 0 : 1;
    }
    for (int i = 0; i < childCount; i++) {
      TreeNode currentNode = parent.getChildAt(i);
      int compareToCurrentNode = comparator.compare(nodeToInsert, currentNode);
      if (compareToCurrentNode < 0) return i;
    }
    return childCount++;
  }

  private void selectNode(TreeNode node) {
    TreeNode[] pathToRoot = model.getPathToRoot(node);
    setSelectionPath(new TreePath(pathToRoot));
  }

  static class WebFeedNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;

    final WebFeed webFeed;

    WebFeedNode(WebFeed webFeed) {
      super(webFeed.name());
      this.webFeed = webFeed;
      setAllowsChildren(false);
    }

    /** @see javax.swing.tree.DefaultMutableTreeNode#isLeaf() */
    @Override public boolean isLeaf() {
      return true;
    }
  }

  static class FolderNode extends DefaultMutableTreeNode {
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

  MainFrame mainFrame() { return mainFrame; }

  private static I18n i18n() {
    return I18nSingletonHolder.INSTANCE;
  }

  private static class I18nSingletonHolder {
    static final I18n INSTANCE = new I18n(WebFeedTree.class);
  }

  private static class NodeModelComparator implements Comparator<TreeNode> {
    public int compare(TreeNode node1, TreeNode node2) {
      String text1 = textFrom(node1);
      String text2 = textFrom(node2);
      if (text1 == null) {
        if (text2 == null) return 0;
        return -1;
      }
      if (text2 == null) return 1;
      return text1.compareToIgnoreCase(text2);
    }

    private String textFrom(TreeNode node) {
      if (!(node instanceof DefaultMutableTreeNode)) return null;
      Object userObject = ((DefaultMutableTreeNode)node).getUserObject();
      if (userObject != null) return userObject.toString();
      return null;
    }
  }
}
