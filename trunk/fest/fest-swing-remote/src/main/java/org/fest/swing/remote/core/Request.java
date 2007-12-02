/*
 * Created on Dec 1, 2007
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
package org.fest.swing.remote.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.fest.swing.remote.util.Castings.cast;

/**
 * Understands a request for the GUI test server.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Request implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /**
   * Understands different types of request.
   *
   * @author Alex Ruiz
   * @author Yvonne Wang
   */
  public static enum Type {
    PING;
  }
  
  private final Type type;
  private final Map<String, Serializable> values = new HashMap<String, Serializable>();

  public static Request pingRequest() {
    return new Request(Type.PING);
  }
  
  private Request(Type type) {
    this.type = type;
  }

  /**
   * Returns the type of this request.
   * @return the type of this request.
   */
  public Type type() { return type; }
  
  /**
   * Adds a new value to this request.
   * @param name the name of the value to add.
   * @param value the actual value to add.
   */
  public void addValue(String name, Serializable value) {
    values.put(name, value);
  }
  
  /**
   * Returns a value from this request, stored under the given name and casted to the given type.
   * @param <T> the generic type of the class to cast the request value to.
   * @param name the name of the value to return.
   * @param valueType the class to cast the request value to.
   * @return the request value stored under the given name, casted to the given type, or <code>null</code> if no value 
   * is found.
   * @throws ClassCastException if the value cannot be casted to the given type.
   */
  public <T extends Serializable> T value(String name, Class<T> valueType) {
    return cast(values.get(name), valueType);
  }
  
  public void removeValue(String name) {
    values.remove(name);
  }
}
