/*
 * Created on Aug 17, 2007
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
package org.fest.reflect.method;

/**
 * Understands the parameter types of the static method to invoke.
 * <p>
 * The following is an example of proper usage of this class:
 * </p>
 *
 * @param <T> the generic type of the static method's return type.
 *
 * @author Alex Ruiz
 */
public final class StaticParameterTypes<T> extends ParameterTypesTemplate<T> {

  StaticParameterTypes(Class<?>[] parameterTypes, StaticReturnType<T> returnType) {
    super(parameterTypes, returnType);
  }

  /**
   * Creates a new method invoker.
   * @param target the class containing the static method to invoke.
   * @return the created method invoker.
   */
  public Invoker<T> in(Class<?> target) {
    return new Invoker<T>(methodName, target, parameterTypes);
  }
}