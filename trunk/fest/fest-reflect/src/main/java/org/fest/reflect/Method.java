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
import static org.fest.util.Strings.quote;

/**
 * Understands the use of reflection to access a method from an object.
 * @param <T> the return type of the method invocation.
 * 
 * @author Yvonne Wang
 */
public final class Method<T> {

  private final Object target;
  private final java.lang.reflect.Method method;
  private final boolean accessible;

  Method(String methodName, Object target, Class<?>... parameterTypes) {
    this.target = target;
    method = method(methodName, target.getClass(), parameterTypes);
    accessible = method.isAccessible();
  }

  private static java.lang.reflect.Method method(String methodName, Class<?> type, Class<?>[] parameterTypes) {
    try {
      return type.getDeclaredMethod(methodName, parameterTypes);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to find method with name ", quote(methodName), " in type ", 
          type.getName(), " with parameter types ", Arrays.toString(parameterTypes)));
    }
  }

  @SuppressWarnings("unchecked") public T invoke(Object... args) {
    try {
      method.setAccessible(true);
      return (T) method.invoke(target, args);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to invoke method ", quote(method.getName()), " with arguments ",
          Arrays.toString(args)));
    } finally {
      method.setAccessible(accessible);
    }
  }
  
  public java.lang.reflect.Method info() {
    return method;
  }

  public static class MethodName {
    private final String name;

    MethodName(String name) {
      this.name = name;
    }

    public <T> ReturnType<T> withReturnType(Class<T> type) {
      return new ReturnType<T>(type, this);
    }

    public ParameterTypes<Void> withParameterTypes(Class<?>... parameterTypes) {
      ReturnType<Void> returnType = new ReturnType<Void>(Void.class, this);
      return new ParameterTypes<Void>(parameterTypes, returnType);
    }

    public Method in(Object target) {
      return new Method<Void>(name, target);
    }
  }

  public static class ReturnType<T> {
    private final MethodName fieldName;

    ReturnType(Class<T> type, MethodName fieldName) {
      this.fieldName = fieldName;
    }

    public Method<T> in(Object target) {
      return new Method<T>(fieldName.name, target);
    }

    public ParameterTypes<T> withParameterTypes(Class<?>... parameterTypes) {
      return new ParameterTypes<T>(parameterTypes, this);
    }
  }

  public static class ParameterTypes<T> {
    private final Class<?>[] values;
    private final ReturnType<T> returnType;

    ParameterTypes(Class<?>[] parameterTypes, ReturnType<T> returnType) {
      this.values = parameterTypes;
      this.returnType = returnType;
    }

    public Method<T> in(Object target) {
      return new Method<T>(returnType.fieldName.name, target, values);
    }
  }
}
