/*
 * Created on Apr 23, 2008
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

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.fest.swing.demo.view.WebFeedTree.WebFeedNode;

/**
 * Understands a listener that clears the entry table when on a folder node of a <code>{@link WebFeedTree}</code> is
 * selected.
 * 
 * @author Alex Ruiz
 */
class WebFeedTreeFolderSelectionListener {

  static void attachTo(WebFeedTree tree) {
    tree.addTreeSelectionListener(new Listener(tree));
  }

  private WebFeedTreeFolderSelectionListener() {}

  private static class Listener implements TreeSelectionListener {

    private final WebFeedTree tree;

    Listener(WebFeedTree tree) {
      this.tree = tree;
    }

    public void valueChanged(TreeSelectionEvent e) {
      Object selection = tree.getLastSelectedPathComponent();
      if (!(selection instanceof WebFeedNode)) return;
      tree.mainFrame().clearWebFeedItems();
    }
  }
}
