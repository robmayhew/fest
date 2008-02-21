/*
 * Created on Feb 20, 2008
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
package org.fest.reflect.method;

/**
 * Understands the name of a static method to invoke using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 */
public class StaticName extends NameTemplate {

  /**
   * Creates a new </code>{@link StaticName}</code>.
   * @param name the name of the method to access using Java Reflection.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public StaticName(String name) {
    super(name);
  }

}
