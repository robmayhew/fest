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
package org.fest.reflect.field;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.Person;
import org.fest.reflect.util.NullAndEmptyStringProvider;
import org.fest.test.CodeToTest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.util.ExpectedFailures.*;

/**
 * Tests for the fluent interface for fields and static fields.
 *
 * @author Alex Ruiz
 */
public class FieldTest {
  
  private Person person;
  
  @BeforeTest public void setUp() {
    person = new Person("Luke");
  }
  
  @Test(dataProvider = "nullAndEmptyStrings", dataProviderClass = NullAndEmptyStringProvider.class) 
  public void shouldThrowErrorIfFieldNameIsNullOrEmpty(final String name) {
    expectIllegalArgumentException("The name of the field to access should not be null or empty").on(new CodeToTest() {
      public void run() {
        FieldName.fieldName(name);
      }
    });
  }
  
  @Test public void shouldThrowErrorIfFieldTypeIsNull() {
    expectIllegalArgumentException("The type of the field to access should not be null").on(new CodeToTest() {
      public void run() {
        FieldName.fieldName("name").ofType(null);
      }      
    });
  }
  
  @Test public void shouldGetFieldValue() {
    String personName = FieldName.fieldName("name").ofType(String.class).in(person).get();
    assertThat(personName).isEqualTo("Luke");
  }
  
  @Test public void shouldSetFieldValue() {
    FieldName.fieldName("name").ofType(String.class).in(person).set("Leia");
    assertThat(person.getName()).isEqualTo("Leia");
  }
  
  @Test public void shouldReturnFieldInfo() {
    java.lang.reflect.Field field = FieldName.fieldName("name").ofType(String.class).in(person).info();
    assertThat(field).isNotNull();
    assertThat(field.getName()).isEqualTo("name");
    assertThat(field.getType()).isEqualTo(String.class);
  }
  
  @Test public void shouldThrowErrorIfWrongFieldTypeSpecified() {
    String message = 
      "The type of the field 'name' in org.fest.reflect.Person should be <java.lang.Integer> but was <java.lang.String>";
    expectReflectionError(message).on(new CodeToTest() {
      public void run()  {
        FieldName.fieldName("name").ofType(Integer.class).in(person).get();
      }
    });
  }

  @Test public void shouldThrowErrorIfInvalidFieldName() {
    expectReflectionError("Unable to find field 'age' in org.fest.reflect.Person").on(new CodeToTest() {
      public void run()  {
        FieldName.fieldName("age").ofType(Integer.class).in(person);
      }
    });
  }
  
  @Test public void shouldGetFieldInSuperType() {
    Jedi jedi = new Jedi("Yoda");
    String jediName = FieldName.fieldName("name").ofType(String.class).in(jedi).get();
    assertThat(jediName).isEqualTo("Yoda");
  }
  
  @Test public void shouldGetStaticFieldValue() {
    Person.setCount(6);
    int count = StaticFieldName.staticFieldName("count").ofType(int.class).in(Person.class).get();
    assertThat(count).isEqualTo(6);
  }
  
  @Test public void shouldSetStaticFieldValue() {
    StaticFieldName.staticFieldName("count").ofType(int.class).in(Person.class).set(8);
    assertThat(Person.getCount()).isEqualTo(8);
  }
  
  @Test public void shouldReturnStaticFieldInfo() {
    java.lang.reflect.Field field = StaticFieldName.staticFieldName("count").ofType(int.class).in(Person.class).info();
    assertThat(field).isNotNull();
    assertThat(field.getName()).isEqualTo("count");
    assertThat(field.getType()).isEqualTo(int.class);
  }
  
  @Test(dataProvider = "nullAndEmptyStrings", dataProviderClass = NullAndEmptyStringProvider.class) 
  public void shouldThrowErrorIfStaticFieldNameIsNullOrEmpty(final String name) {
    expectIllegalArgumentException("The name of the field to access should not be null or empty").on(new CodeToTest() {
      public void run() {
        StaticFieldName.staticFieldName(name);
      }
    });
  }

  @Test public void shouldThrowErrorIfStaticFieldTypeIsNull() {
    expectIllegalArgumentException("The type of the field to access should not be null").on(new CodeToTest() {
      public void run() {
        StaticFieldName.staticFieldName("name").ofType(null);
      }      
    });
  }

  @Test public void shouldThrowErrorIfWrongStaticFieldTypeSpecified() {
    String message = "The type of the field 'count' in org.fest.reflect.Person should be <java.lang.Float> but was <int>";
    expectReflectionError(message).on(new CodeToTest() {
      public void run()  {
        StaticFieldName.staticFieldName("count").ofType(Float.class).in(Person.class).get();
      }
    });
  }

  @Test public void shouldThrowErrorIfInvalidStaticFieldName() {
    expectReflectionError("Unable to find field 'age' in org.fest.reflect.Person").on(new CodeToTest() {
      public void run()  {
        StaticFieldName.staticFieldName("age").ofType(int.class).in(Person.class);
      }
    });
  }
  
  @Test public void shouldGetStaticFieldInSuperType() {
    Person.setCount(8);
    int count = StaticFieldName.staticFieldName("count").ofType(int.class).in(Person.class).get();
    assertThat(count).isEqualTo(8);
  }
}
