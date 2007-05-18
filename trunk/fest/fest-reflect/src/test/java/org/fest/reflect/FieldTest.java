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

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.*;

/**
 * Tests for <code>{@link Field}</code>.
 *
 * @author Alex Ruiz
 */
public class FieldTest {
  
  private Person person;
  
  @BeforeTest public void setUp() {
    person = new Person("Luke");
  }
  
  @Test public void shouldGetFieldValue() {
    String personName = new Field.FieldName("name").ofType(String.class).in(person).get();
    assertThat(personName).isEqualTo("Luke");
  }
  
  @Test public void shouldSetFieldValue() {
    new Field.FieldName("name").ofType(String.class).in(person).set("Leia");
    assertThat(person.getName()).isEqualTo("Leia");
  }
  
  @Test(expectedExceptions = ReflectionError.class)
  public void shouldThrowErrorIfWrongTypeSpecified() {
    Class<Integer> invalidType = Integer.class;
    new Field.FieldName("name").ofType(invalidType).in(person).get();
  }

  @Test(expectedExceptions = ReflectionError.class)
  public void shouldThrowErrorIfInvalidFieldName() {
    new Field.FieldName("age").ofType(Integer.class).in(person);
  }
  
  @Test public void shouldGetFieldInSuperType() {
    Jedi jedi = new Jedi("Yoda");
    String jediName = new Field.FieldName("name").ofType(String.class).in(jedi).get();
    assertThat(jediName).isEqualTo("Yoda");
  }
}
