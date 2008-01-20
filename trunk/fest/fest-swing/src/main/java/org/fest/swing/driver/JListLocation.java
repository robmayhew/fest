/*
 * Created on Jan 19, 2008
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

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JList;

import org.fest.swing.exception.LocationUnavailableException;

import static java.lang.String.valueOf;

import static org.fest.swing.driver.CellRendererComponents.textFrom;
import static org.fest.swing.util.Objects.*;
import static org.fest.swing.util.Strings.match;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands encapsulation of the location of a row on a <code>{@link JList}</code> (a coordinate, item index or
 * value.)
 * 
 * <p>
 * Adapted from <code>abbot.tester.JListLocation</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
public class JListLocation {

  private final JList list;

  /**
   * Creates a new </code>{@link JListLocation}</code>.
   * @param list the target <code>JList</code>.
   */
  public JListLocation(JList list) {
    this.list = list;
  }

  /**
   * Returns the coordinates of the first item matching the given value.
   * @param value the value to match.
   * @return the coordinates of the item at the given item.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public Point pointAt(Object value) {
    return pointAt(indexOf(value));
  }
  
  /**
   * Returns the index of the first item matching the given value.
   * @param value the value to match.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public int indexOf(Object value) {
    int size = size();
    if (value instanceof String) return indexOf((String)value);
    for (int i = 0; i < size; i++) 
      if (areEqual(value, elementAt(i))) return i;
    throw indexNotFoundFor(value);
  }

  private int indexOf(String value) {
    int size = size();
    for (int i = 0; i < size; i++) if (match(value, text(i))) return i;
    throw indexNotFoundFor(value);
  }
  
  private LocationUnavailableException indexNotFoundFor(Object value) {
    throw new LocationUnavailableException(concat("Unable to find an element matching the value ", quote(value)));
  }
  
  /**
   * Returns the text of the element under the given index.
   * @param index the given index.
   * @return the text of the element under the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public String text(int index) {
    validate(index);
    Object value = elementAt(index);
    String text = textFrom(cellRendererComponent(index, value));
    if (text != null) return text;
    text = toStringOf(value);
    return DEFAULT_TO_STRING.equals(text) ? null : text;
  }

  private Object elementAt(int index) {
    return list.getModel().getElementAt(index);
  }

  private Component cellRendererComponent(int index, Object value) {
    return list.getCellRenderer().getListCellRendererComponent(list, value, index, false, false);
  }

  /**
   * Returns the coordinates of the item at the given index.
   * @param index the given index.
   * @return the coordinates of the item at the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public Point pointAt(int index) {
    validate(index);
    Rectangle rect = list.getCellBounds(index, index);
    return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
  }

  private void validate(int index) {
    int itemCount = size();
    if (index >= 0 && index < itemCount) return;
    throw new LocationUnavailableException(concat(
        "Item index (", valueOf(index), ") should be between [", valueOf(0), "] and [",  valueOf(itemCount - 1), 
        "] (inclusive)"));
  }  

  private int size() {
    return list.getModel().getSize();
  }
}
