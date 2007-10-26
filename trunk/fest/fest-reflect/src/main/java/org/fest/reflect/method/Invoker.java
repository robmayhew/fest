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
package org.fest.reflect.method;

import java.util.Arrays;

import org.fest.reflect.ReflectionError;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands the use of reflection to access a method from an object.
 * <p>
 * <pre>
 *   // Equivalent to call 'person.setName("Luke")'
 *   {@link org.fest.reflect.Reflection#method(String) method}("setName").{@link Name#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                    .{@link ParameterTypes#in(Object) in}(person)
 *                    .{@link Invoker#invoke(Object...) invoke}("Luke");
 * 
 *   // Equivalent to call 'person.concentrate()'
 *   {@link org.fest.reflect.Reflection#method(String) method}("concentrate").{@link Name#in(Object) in}(person).{@link Invoker#invoke(Object...) invoke}();
 *   
 *   // Equivalent to call 'person.getName()'
 *   String name = {@link org.fest.reflect.Reflection#method(String) method}("getName").{@link Name#withReturnType(Class) withReturnType}(String.class)
 *                                  .{@link ReturnType#in(Object) in}(person)
 *                                  .{@link Invoker#invoke(Object...) invoke}();   
 * </pre>
 * </p>
 * 
 * @param <T> the return type of the method invocation.
 * 
 * @author Yvonne Wang
 */
public final class Invoker<T> {

  private final Object target;
  private final java.lang.reflect.Method method;
  private final boolean accessible;

  Invoker(String methodName, Object target, Class<?>... parameterTypes) {
    this.target = target;
    method = method(methodName, target.getClass(), parameterTypes);
    accessible = method.isAccessible();
  }

  private static java.lang.reflect.Method method(String methodName, Class<?> type, Class<?>[] parameterTypes) {
    try {
      return type.getDeclaredMethod(methodName, parameterTypes);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to find method with name ", quote(methodName), " in type ", 
          type.getName(), " with parameter types ", Arrays.toString(parameterTypes)), e);
    }
  }

  /**
   * Invokes the method managed by this class using the given arguments.
   * @param args the arguments to use to call the method managed by this class.
   * @return the result of the method call.
   * @throws ReflectionError if the method cannot be invoked.
   */
  @SuppressWarnings("unchecked") public T invoke(Object... args) {
    try {
      method.setAccessible(true);
      return (T) method.invoke(target, args);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to invoke method ", quote(method.getName()), " with arguments ",
          Arrays.toString(args)), e);
    } finally {
      method.setAccessible(accessible);
    }
  }
  
  /**
   * Returns the "real" method managed by this class.
   * @return the "real" method managed by this class.
   */
  public java.lang.reflect.Method info() {
    return method;
  }
}
