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
 *   // Equivalent to call 'Jedi.class.setCommonPower("Jump")'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("setCommonPower").{@link StaticMethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                                 .{@link StaticParameterTypes#in(Class) in}(Jedi.class)
 *                                 .{@link Invoker#invoke(Object...) invoke}("Jump");
 *
 *   // Equivalent to call 'Jedi.class.addPadawan()'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("addPadawan").{@link StaticMethodName#in(Class) in}(Jedi.class).{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.class.commonPowerCount()'
 *   String name = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("commonPowerCount").{@link StaticMethodName#withReturnType(Class) withReturnType}(String.class)
 *                                                 .{@link StaticReturnType#in(Class) in}(Jedi.class)
 *                                                 .{@link Invoker#invoke(Object...) invoke}();
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 */
public class StaticMethodName extends NameTemplate {

  /**
   * Creates a new </code>{@link StaticMethodName}</code>: the starting point of the fluent interface for accessing 
   * static methods using Java Reflection.
   * @param name the name of the method to access using Java Reflection.
   * @return the created <code>StaticMethodName</code> instance.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public static StaticMethodName staticMethodName(String name) {
    return new StaticMethodName(name);
  }
  
  private StaticMethodName(String name) {
    super(name);
  }

  /**
   * Specifies the return type of the static method to invoke. This method call is optional if the return type of the
   * method to invoke is <code>void</code>.
   * @param <T> the generic type of the method's return type.
   * @param type the return type of the method to invoke.
   * @return the created return type holder.
   */
  public <T> StaticReturnType<T> withReturnType(Class<T> type) {
    return new StaticReturnType<T>(type, this);
  }

  /**
   * Specifies the parameter types of the static method to invoke. This method call is optional if the method to invoke
   * does not take arguments.
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   */
  public StaticParameterTypes<Void> withParameterTypes(Class<?>... parameterTypes) {
    StaticReturnType<Void> returnType = new StaticReturnType<Void>(Void.class, this);
    return new StaticParameterTypes<Void>(parameterTypes, returnType);
  }

  /**
   * Creates a new invoker for a static method that takes no parameters and return value <code>void</code>.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   */
  public Invoker<Void> in(Class<?> target) {
    return new Invoker<Void>(name, target);
  }
}
