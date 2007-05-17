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

import static org.fest.util.Strings.quote;

/**
 * Understands the use of reflection to access a method from an object.
 * @param <T> the return type of the method invocation.
 * 
 * @author Alex Ruiz
 */
public final class Method<T> {

  private final Object target;
  private final java.lang.reflect.Method method;

  Method(String methodName, Object target, Class<?>... parameterTypes) {
    this.target = target;
    this.method = method(methodName, target.getClass(), parameterTypes);
  }

  private static java.lang.reflect.Method method(String methodName, Class<?> type, Class<?>[] parameterTypes) {
    try {
      java.lang.reflect.Method method = type.getDeclaredMethod(methodName, parameterTypes);
      method.setAccessible(true);
      return method;
    } catch (Exception e) {
      throw new ReflectionError("Unable to find method with name " + quote(methodName) + " in type " + type.getName()
          + " with parameter types " + Arrays.toString(parameterTypes));
    }
  }

  @SuppressWarnings("unchecked") public T invoke() {
    return invokeWithArgs();
  }

  @SuppressWarnings("unchecked") public T invokeWithArgs(Object... args) {
    try {
      return (T) method.invoke(target, args);
    } catch (Exception e) {
      throw new ReflectionError("Unable to invoke method " + quote(method.getName()) + " with arguments "
          + Arrays.toString(args));
    }
  }

  public static class MethodName {
    private final String value;

    MethodName(String name) {
      this.value = name;
    }

    public <T> ReturnType<T> withReturnType(Class<T> type) {
      return new ReturnType<T>(type, this);
    }
  }

  public static class ReturnType<T> {
    private final MethodName fieldName;

    ReturnType(Class<T> type, MethodName fieldName) {
      this.fieldName = fieldName;
    }

    public Method<T> in(Object target) {
      return new Method<T>(fieldName.value, target);
    }

    public ParameterTypes<T> andParameterTypes(Class<?>... parameterTypes) {
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
      return new Method<T>(returnType.fieldName.value, target, values);
    }
  }
}
