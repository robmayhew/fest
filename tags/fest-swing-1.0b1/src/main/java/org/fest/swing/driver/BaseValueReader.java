/*
 * Created on Apr 12, 2008
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

import javax.swing.JLabel;

import static org.fest.swing.util.Strings.isDefaultToString;

/**
 * Understands the base implementation of all default readers in this package.
 *
 * @author Alex Ruiz
 */
public abstract class BaseValueReader {

  /**
   * Reads the value in the given renderer, or returns <code>null</code> if the renderer belongs to an unknown
   * component type. Internally, this method will call <code>getText()</code> if the given renderer is an instance of
   * <code>{@link JLabel}</code></li>
   * @param renderer the given renderer.
   * @return the value of the given renderer, or <code>null</code> if the renderer belongs to an unknown component
   *         type.
   */
  protected final String valueFrom(Component renderer) {
    if (renderer instanceof JLabel) return ((JLabel)renderer).getText();
    return null;
  }

  /**
   * Returns the <code>toString</code> value from the given object. If the given object does not implement 
   * <code>toString</code>, this method will return <code>null</code>.
   * @param fromModel the given object.
   * @return the <code>toString</code> value from the given object, or <code>null</code> if the given object does not
   * implement <code>toString</code>.
   */
  protected final String valueFrom(Object fromModel) {
    if (fromModel == null) return null;
    String text = fromModel.toString();
    if (!isDefaultToString(text)) return text;    
    return null;
  }
}
