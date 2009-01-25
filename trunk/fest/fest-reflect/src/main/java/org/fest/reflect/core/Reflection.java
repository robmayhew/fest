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
package org.fest.reflect.core;

import org.fest.reflect.constructor.TargetType;
import org.fest.reflect.field.FieldName;
import org.fest.reflect.field.Invoker;
import org.fest.reflect.field.StaticFieldName;
import org.fest.reflect.field.StaticFieldType;
import org.fest.reflect.method.MethodName;
import org.fest.reflect.method.StaticMethodName;
import org.fest.reflect.type.Type;

import static org.fest.reflect.field.FieldName.fieldName;
import static org.fest.reflect.field.StaticFieldName.staticFieldName;
import static org.fest.reflect.method.MethodName.methodName;
import static org.fest.reflect.method.StaticMethodName.staticMethodName;

/**
 * Understands the entry point for the classes in this package.
 * The following is an example of proper usage of the classes in this package:
 * <pre>
 *   // Equivalent to call 'new Person()'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#in in}(Person.class).{@link org.fest.reflect.constructor.Invoker#newInstance(Object...) newInstance}();
 *   
 *   // Equivalent to call 'new Person("Yoda")'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.ParameterTypes#in(Class) in}(Person.class).{@link org.fest.reflect.constructor.Invoker#newInstance(Object...) newInstance}("Yoda");
 * 
 *   // Retrieves the value of the field "name"
 *   String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(person).{@link org.fest.reflect.field.Invoker#get() get}();
 *   
 *   // Sets the value of the field "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(person).{@link org.fest.reflect.field.Invoker#set(Object) set}("Yoda");
 *   
 *   // Equivalent to call 'person.setName("Luke")'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("setName").{@link org.fest.reflect.method.MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                    .{@link org.fest.reflect.method.ParameterTypes#in(Object) in}(person)
 *                    .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}("Luke");
 * 
 *   // Retrieves the value of the static field "count" in Person.class
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the static field "count" to 3 in Person.class
 *   {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#set(Object) set}(3);
 *   
 *   // Equivalent to call 'person.concentrate()'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("concentrate").{@link org.fest.reflect.method.MethodName#in(Object) in}(person).{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *   
 *   // Equivalent to call 'person.getName()'
 *   String name = {@link org.fest.reflect.core.Reflection#method(String) method}("getName").{@link org.fest.reflect.method.MethodName#withReturnType(Class) withReturnType}(String.class)
 *                                  .{@link org.fest.reflect.method.ReturnType#in(Object) in}(person)
 *                                  .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.class.setCommonPower("Jump")'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("setCommonPower").{@link org.fest.reflect.method.StaticMethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                                 .{@link org.fest.reflect.method.StaticParameterTypes#in(Class) in}(Jedi.class)
 *                                 .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}("Jump");
 *
 *   // Equivalent to call 'Jedi.class.addPadawan()'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("addPadawan").{@link org.fest.reflect.method.StaticMethodName#in(Class) in}(Jedi.class).{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.class.commonPowerCount()'
 *   String name = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("commonPowerCount").{@link org.fest.reflect.method.StaticMethodName#withReturnType(Class) withReturnType}(String.class)
 *                                                 .{@link org.fest.reflect.method.StaticReturnType#in(Class) in}(Jedi.class)
 *                                                 .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *                                     
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Reflection {

  /**
   * Starting point for the fluent interface for loading a class dynamically.
   * @param name the name of the class to load.
   * @return the starting point of the method chain.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public static Type type(String name) { return new Type(name); }
  
  /**
   * Starting point for the fluent interface for accessing fields via reflection.
   * @param name the name of the field to access.
   * @return the starting point of the method chain.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public static FieldName field(String name) {
    return fieldName(name);
  }

  /**
   * Starting point for the fluent interface for accessing static fields via reflection.
   * @param name the name of the static field to access.
   * @return the starting point of the method chain.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public static StaticFieldName staticField(String name) {
    return staticFieldName(name);
  }

  /**
   * Starting point for the fluent interface for invoking methods via reflection.
   * @param name the name of the method to invoke.
   * @return the starting point of the method chain.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public static MethodName method(String name) {
    return methodName(name);
  }

  /**
   * Starting point for the fluent interface for invoking static methods via reflection.
   * @param name the name of the static method to invoke.
   * @return the starting point of the static method chain.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public static StaticMethodName staticMethod(String name) {
    return staticMethodName(name);
  }

  /**
   * Starting point for the fluent interface for invoking constructors via reflection.
   * @return the starting point of the method chain.
   */
  public static TargetType constructor() {
    return TargetType.type();
  }

  private Reflection() {}

}
