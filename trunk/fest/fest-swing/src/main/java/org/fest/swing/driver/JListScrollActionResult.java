/*
 * Created on Nov 4, 2008
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

import java.awt.Point;

/**
 * Understands the result of an action that involves scrolling up/down to make an item of a
 * <code>{@link javax.swing.JList}</code> visible.
 * 
 * @author Alex Ruiz
 */
final class JListScrollActionResult {

  static final JListScrollActionResult ITEM_NOT_FOUND = new JListScrollActionResult(-1, null);
  
  private final int itemIndex;
  private final Point cellCenter;

  JListScrollActionResult(int itemIndex, Point cellCenter) {
    this.itemIndex = itemIndex;
    this.cellCenter = cellCenter;
  }
  
  int itemIndex() { return itemIndex; }
  Point cellCenter() { return cellCenter != null ? new Point(cellCenter) : null; }
}
