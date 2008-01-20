/*
 * Created on Jan 17, 2008
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
package org.fest.swing.driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import static java.lang.String.valueOf;

import static org.fest.util.Strings.concat;

/**
 * Understands an iterator of the children of a <code>{@link JTree}</code> node.
 *
 * @author Alex Ruiz
 */
final class TreeNodeChildrenIterator implements Iterator<Object> {

  private final TreeModel model;
  private final Object parent;
  
  private int currentChildIndex;
  
  /**
   * Creates a new </code>{@link TreeNodeChildrenIterator}</code>.
   * @param model the <code>JTree</code> model.
   * @param parent the parent of the children to iterate.
   */
  TreeNodeChildrenIterator(TreeModel model, Object parent) {
    this.model = model;
    this.parent = parent;
  }

  /**
   * Returns <code>true</code> if the parent node has more children.
   * @return <code>true</code> if the parent node has more children.
   */
  public boolean hasNext() {
    return currentChildIndex < childCount();
  }

  /**
   * Returns the next child in the iteration.
   *
   * @return the next child in the iteration.
   * @exception NoSuchElementException iteration has no more elements.
   */
  public Object next() {
    int childCount = childCount();
    if (currentChildIndex >= childCount)
      throw new NoSuchElementException(
          concat("Unable to get child at index [", valueOf(currentChildIndex), "]. ",
          		   "Parent node only contains ", valueOf(childCount), " element(s)."));
    return model.getChild(parent, currentChildIndex++);
  }

  private int childCount() {
    return model.getChildCount(parent);
  }

  /**
   * Removal of children is not supported by this iterator.
   * @throws UnsupportedOperationException <code>remove</code> operation is not supported by this iterator.
   */
  public void remove() {
    throw new UnsupportedOperationException("'remove' is not supported");
  }

}
