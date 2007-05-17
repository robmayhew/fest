/*
 * Created on Oct 31, 2006
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2006 the original author or authors.
 */
package org.fest.reflect;

import java.util.Arrays;

import static org.fest.util.Strings.concat;

/**
 * Understands the use of reflection to access a constructor from an object.
 * @param <T> the class in which the constructor is declared.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   Reflection.constructor().in(Person.class).newInstance();
 *   Reflection.constructor().withParameterTypes(String.class).in(Person.class).newInstance("Yoda");
 * </pre>
 * </p>
 * @see TargetType
 * @see ParameterTypes
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Constructor<T> {

  private final java.lang.reflect.Constructor<T> constructor;

  Constructor(Class<T> target, Class<?>... parameterTypes) {
    this.constructor = constructor(target, parameterTypes);
  }

  private java.lang.reflect.Constructor<T> constructor(Class<T> target, Class<?>... parameterTypes) {
    try {
      java.lang.reflect.Constructor<T> c = target.getDeclaredConstructor(parameterTypes);
      c.setAccessible(true);
      return c;
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to find constructor in type ", target.getName(),
          " with parameter types ", Arrays.toString(parameterTypes)));
    }
  }

  /**
   * Creates a new instance of the class specified as the generic type of this <code>{@link Constructor}</code>.
   * @return the created instance.
   */
  public T newInstance() {
    return newInstance(new Object[0]);
  }

  /**
   * Creates a new instance of the class specified as the generic type of this <code>{@link Constructor}</code>, using
   * the specified arguments.
   * @param args the arguments to use to create a new instance.
   * @return the created instance.
   */
  public T newInstance(Object... args) {
    try {
      return constructor.newInstance(args);
    } catch (Exception e) {
      throw new ReflectionError("Unable to create a new object from the enclosed constructor", e);
    }
  }

  /**
   * Understands how to create a new <code>{@link Constructor}</code> for a specific data type.
   * @see Reflection#constructor()
   */
  public static class TargetType {

    TargetType() {}

    /**
     * Creates a <code>{@link Constructor}</code> for the given class.
     * @param <T> the generic type of the given class.
     * @param target the class to create a constructor for.
     * @return the created constructor.
     */
    public <T> Constructor<T> in(Class<T> target) {
      return new Constructor<T>(target);
    }

    /**
     * Creates an intermediate object that knows how to specify parameter types for a constructor.
     * @param <T> the generic type of the class to create a constructor for.
     * @param parameterTypes the given parameter types for the constructor.
     * @return the created parameter types.
     */
    public <T> ParameterTypes<T> withParameterTypes(Class<?>... parameterTypes) {
      return new ParameterTypes<T>(parameterTypes);
    }
  }

  /**
   * Understands how to specify parameter types for a constructor.
   * @see TargetType
   */
  public static class ParameterTypes<T> {
    private final Class<?>[] parameterTypes;

    /**
     * Creates a new </code>{@link ParameterTypes}</code>.
     * @param parameterTypes the parameter types for the constructor to create.
     */
    ParameterTypes(Class<?>[] parameterTypes) {
      this.parameterTypes = parameterTypes;
    }

    /**
     * Creates a <code>{@link Constructor}</code> for the given class, using the parameter types specifies during 
     * creation.
     * @param <T> the generic type of the given class.
     * @param target the class to create a constructor for.
     * @return the created constructor.
     */
    public Constructor<T> in(Class<T> target) {
      return new Constructor<T>(target, parameterTypes);
    }
  }
}
