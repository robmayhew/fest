/*
 * Created on May 17, 2007
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

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link Constructor}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ConstructorTest {

  @Test public void shouldCreateNewInstanceWithDefaultConstructor() {
    Person person = new Constructor.TargetType().in(Person.class).newInstance();
    assertThat(person).isNotNull();
    assertThat(person.getName()).isEmpty();
  }
  
  @Test public void shouldCreateNewInstanceUsingGivenConstructorParameters() {
    Person person = new Constructor.TargetType().withParameterTypes(String.class).in(Person.class).newInstance("Yoda");
    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("Yoda");
  }
  
  @Test public void shouldReturnConstructorInfo() {
    java.lang.reflect.Constructor<Person> constructor = new Constructor.TargetType().withParameterTypes(String.class).in(Person.class).info();
    assertThat(constructor).isNotNull();
    Class<?>[] parameterTypes = constructor.getParameterTypes();
    assertThat(parameterTypes).hasSize(1);
    assertThat(parameterTypes[0]).isEqualTo(String.class);
  }
  
  @Test(expectedExceptions = ReflectionError.class)
  public void shouldThrowErrorIfConstructorNotFound() {
    Class<Integer> illegalType = Integer.class;
    new Constructor.TargetType().withParameterTypes(illegalType).in(Person.class);
  }
  
  @Test(expectedExceptions = ReflectionError.class)
  public void shouldThrowErrorIfInstanceNotCreated() {
    int illegalArg = 8;
    new Constructor.TargetType().withParameterTypes(String.class).in(Person.class).newInstance(illegalArg);
  }
}
