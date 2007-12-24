/*
 * Created on Dec 22, 2007
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
package org.fest.swing.format;

import java.awt.Component;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fest.util.Arrays;

import static org.fest.util.Collections.list;
import static org.fest.util.Strings.*;

/**
 * Understands a formatter that uses
 * <a href="http://java.sun.com/docs/books/tutorial/javabeans/introspection/" target="_blank">introspection</a>
 * to display property values of a <code>{@link Component}</code>. This formatter does not support nested properties.
 *
 * @author Alex Ruiz
 */
public final class IntrospectionComponentFormatter extends ComponentFormatterTemplate {

  private final Class<? extends Component> targetType;
  private final List<String> propertyNames;

  private final Map<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>();

  /**
   * Creates a new </code>{@link IntrospectionComponentFormatter}</code>.
   * @param targetType the type of <code>Component</code> that this formatter supports.
   * @param propertyNames the property names to show as the <code>String</code> representation of a given
   * <code>Component</code>.
   */
  public IntrospectionComponentFormatter(Class<? extends Component> targetType, String...propertyNames) {
    this.targetType = targetType;
    this.propertyNames = list(propertyNames);
    populate();
  }

  private void populate() {
    BeanInfo beanInfo = null;
    try {
      beanInfo = Introspector.getBeanInfo(targetType, Object.class);
    } catch (IntrospectionException e) { return; }
    for (PropertyDescriptor d : beanInfo.getPropertyDescriptors()) register(d);
  }

  private void register(PropertyDescriptor d) {
    String name = d.getName();
    if (!propertyNames.contains(name)) return;
    descriptors.put(name, d);
  }

  /**
   * Returns a <code>String</code> representation of the given <code>{@link Component}</code>, showing only the
   * properties specified in this formatter's
   * <code>{@link #IntrospectionComponentFormatter(Class, String...) constructor}</code>.
   * @param c the given <code>Component</code>.
   * @return a <code>String</code> representation of the given <code>Component</code>.
   * @throws IllegalArgumentException if the given <code>Component</code> is <code>null</code> or its type is not
   * supported by this formatter.
   * @see #targetType()
   */
  protected String doFormat(Component c) {
    StringBuilder b = new StringBuilder();
    b.append(c.getClass().getName()).append("[");
    int max = propertyNames.size() - 1;
    for (int i = 0; i <= max; i++) {
      appendProperty(b, propertyNames.get(i), c);
      if (i < max) b.append(", ");
    }
    b.append("]");
    return b.toString();
  }

  private void appendProperty(StringBuilder b, String name, Component c) {
    b.append(name).append("=");
    try {
      b.append(quote(propertyValue(c, name)));
    } catch (Exception e) {
      b.append(concat("<Unable read property: [", e.getMessage(), "]>"));
    }
  }

  private Object propertyValue(Component c, String property) throws Exception {
    PropertyDescriptor descriptor = descriptors.get(property);
    Object value = descriptor.getReadMethod().invoke(c);
    if (isOneDimensionalArray(value)) return Arrays.format(value);
    return value;
  }

  private boolean isOneDimensionalArray(Object o) {
    return o != null && o.getClass().isArray() && !o.getClass().getComponentType().isArray();
  }

  /**
   * Returns the type of <code>{@link Component}</code> this formatter supports.
   * @return the type of <code>Component</code> this formatter supports.
   */
  public Class<? extends Component> targetType() { return targetType; }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "propertyNames=", propertyNames,
        "]"
    );
  }
}
