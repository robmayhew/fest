/*
 * Created on May 18, 2007
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
package org.fest.reflect;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link Method}</code>.
 *
 * @author Yvonne Wang
 */
public class MethodTest {

  private Person person;
  
  @BeforeMethod public void setUp() {
    person = new Person("Luke");
  }

  @Test public void shouldCallMethodWithArgs() {
    new Method.MethodName("setName").withParameterTypes(String.class).in(person).invoke("Leia");
    assertThat(person.getName()).isEqualTo("Leia");
  }
  
  @Test public void shouldCallMethod() {
    String personName = new Method.MethodName("getName").withReturnType(String.class).in(person).invoke();
    assertThat(personName).isEqualTo("Luke");
  }
  
  @Test public void shouldReturnMethodInfo() {
    java.lang.reflect.Method method = new Method.MethodName("setName").withParameterTypes(String.class).in(person).info();
    assertThat(method).isNotNull();
    assertThat(method.getName()).isEqualTo("setName");
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertThat(parameterTypes).hasSize(1);
    assertThat(parameterTypes[0]).isEqualTo(String.class);
  }
  
  @Test(expectedExceptions = ReflectionError.class) 
  public void shouldThrowErrorIfInvalidMethodName() {
    String invalidName = "getAge";
    new Method.MethodName(invalidName).withReturnType(Integer.class).in(person);
  }
  
  @Test(expectedExceptions = ReflectionError.class)
  public void shouldThrowErrorIfInvalidArgs() {
    int invalidArg = 8;
    new Method.MethodName("setName").withParameterTypes(String.class).in(person).invoke(invalidArg);
  }
  
  @Test public void shouldCallMethodWithReturnTypeAndArgs() {
    Jedi jedi = new Jedi("Yoda");
    new Method.MethodName("addPower").withReturnType(Boolean.class).withParameterTypes(String.class).in(jedi).invoke("Heal");
    assertThat(jedi.powerCount()).isEqualTo(1);
    assertThat(jedi.powerAt(0)).isEqualTo("Heal");
  }
}
