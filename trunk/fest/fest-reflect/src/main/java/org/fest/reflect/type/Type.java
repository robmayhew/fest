/*
 * Created on Jan 23, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.type;

import org.fest.reflect.exception.ReflectionError;

import static org.fest.util.Strings.*;

/**
 * Understands loading a class dynamically.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Loads the class 'org.republic.Jedi'
 *   Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#load() load}();
 *
 *   // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
 *   Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#loadAs(Class) loadAs}(Person.class);
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 */
public class Type {

  private final String name;

  /**
   * Creates a new <code>{@link Type}</code>: the starting point of the fluent interface for loading classes 
   * dynamically.
   * @param name the name of the class to load.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public Type(String name) {
    if (name == null) 
      throw new NullPointerException("The name of the class to load should not be null");
    if (isEmpty(name)) 
      throw new IllegalArgumentException("The name of the class to load should not be empty");
    this.name = name;
  }
  
  /**
   * Loads the class with the name specified in this type, using this class' <code>ClassLoader</code>.
   * @return the loaded class.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public Class<?> load() {
    try {
      return getClass().getClassLoader().loadClass(name);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to load class ", quote(name)), e);
    }
  }
  
  /**
   * Loads the class with the name specified in this type, as the given type, using this class' 
   * <code>ClassLoader</code>.
   * <p>
   * The following example shows how to use this method. Let's assume that we have the class <code>Jedi</code> that 
   * extends the class <code>Person</code>:
   * <pre>
   * Class<? extends Person> type = new Type("org.republic.Jedi").loadAs(Person.class);
   * </pre>
   * </p>
   * @param type the given type. 
   * @param <T> the generic type of the type.
   * @return the loaded class.
   * @throws NullPointerException if the given type is <code>null</code>.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public <T> Class<? extends T> loadAs(Class<T> type) {
    if (type == null) throw new NullPointerException("The given type should not be null");
    try {
      return getClass().getClassLoader().loadClass(name).asSubclass(type);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to load class ", quote(name), " as ", type.getName()), e);
    }
  }
}
