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
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Constructor<T> {

  private final java.lang.reflect.Constructor<T> constructor;
  private final boolean accessible;
  
  Constructor(Class<T> target, Class<?>... parameterTypes) {
    this.constructor = constructor(target, parameterTypes);
    this.accessible = constructor.isAccessible();
  }

  private java.lang.reflect.Constructor<T> constructor(Class<T> target, Class<?>... parameterTypes) {
    try {
      return target.getDeclaredConstructor(parameterTypes);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to find constructor in type ", target.getName(),
          " with parameter types ", Arrays.toString(parameterTypes)), e);
    }
  }

  public T newInstance(Object... args) {
    try {
      constructor.setAccessible(true);
      T newInstance = constructor.newInstance(args);
      constructor.setAccessible(accessible);
      return newInstance;
    } catch (Exception e) {
      throw new ReflectionError("Unable to create a new object from the enclosed constructor", e);
    }
  }

  public java.lang.reflect.Constructor<T> info() {
    return constructor;
  }
  
  public static class TargetType {
    TargetType() {}

    public <T> Constructor<T> in(Class<T> target) {
      return new Constructor<T>(target);
    }

    public ParameterTypes withParameterTypes(Class<?>... parameterTypes) {
      return new ParameterTypes(parameterTypes);
    }
  }

  public static class ParameterTypes {
    private final Class<?>[] parameterTypes;

    ParameterTypes(Class<?>[] parameterTypes) {
      this.parameterTypes = parameterTypes;
    }

    public <T> Constructor<T> in(Class<T> target) {
      return new Constructor<T>(target, parameterTypes);
    }
  }
}
