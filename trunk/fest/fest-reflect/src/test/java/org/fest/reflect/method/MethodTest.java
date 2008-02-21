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
package org.fest.reflect.method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.Person;
import org.fest.test.CodeToTest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.util.ExpectedFailures.*;

/**
 * Tests for the fluent interface for methods.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class MethodTest {

  private Person person;
  
  @BeforeMethod public void setUp() {
    person = new Person("Luke");
  }

  @Test(dataProvider = "emptyAndNullNames") 
  public void shouldThrowErrorIfFieldNameIsNullOrEmpty(final String name) {
    expectIllegalArgumentException("The name of the method to access should not be null or empty").on(new CodeToTest() {
      public void run() {
        new Name(name);
      }
    });
  }
  
  @Test public void shouldCallMethodWithArgs() {
    new Name("setName").withParameterTypes(String.class).in(person).invoke("Leia");
    assertThat(person.getName()).isEqualTo("Leia");
  }
  
  @Test public void shouldCallMethod() {
    String personName = new Name("getName").withReturnType(String.class).in(person).invoke();
    assertThat(personName).isEqualTo("Luke");
  }
  
  @Test public void shouldReturnMethodInfo() {
    java.lang.reflect.Method method = new Name("setName").withParameterTypes(String.class).in(person).info();
    assertThat(method).isNotNull();
    assertThat(method.getName()).isEqualTo("setName");
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertThat(parameterTypes).hasSize(1);
    assertThat(parameterTypes[0]).isEqualTo(String.class);
  }

  @Test public void shouldNotHaveToSpecifyReturnTypeIfVoidAndNoParameters() {
    Jedi jedi = new Jedi("Obi Wan");
    assertThat(jedi.isMaster()).isFalse();
    new Name("makeMaster").in(jedi).invoke();
    assertThat(jedi.isMaster()).isTrue();
  }
  
  @Test public void shouldThrowErrorIfInvalidMethodName() {
    String message = "Unable to find method 'getAge' in org.fest.reflect.Person with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "getAge";
        new Name(invalidName).withReturnType(Integer.class).in(person);
      }
    });
  }
  
  @Test public void shouldThrowErrorIfInvalidArgs() {
    expectReflectionError("Unable to invoke method 'setName' with arguments [8]").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        new Name("setName").withParameterTypes(String.class).in(person).invoke(invalidArg);
      }
    });
  }
  
  @Test public void shouldCallMethodWithReturnTypeAndArgs() {
    Jedi jedi = new Jedi("Yoda");
    new Name("addPower").withReturnType(Boolean.class).withParameterTypes(String.class).in(jedi).invoke("Heal");
    assertThat(jedi.powerCount()).isEqualTo(1);
    assertThat(jedi.powerAt(0)).isEqualTo("Heal");
  }

  @Test(dataProvider = "emptyAndNullNames") 
  public void shouldThrowErrorIfStaticFieldNameIsNullOrEmpty(final String name) {
    expectIllegalArgumentException("The name of the method to access should not be null or empty").on(new CodeToTest() {
      public void run() {
        new StaticName(name);
      }
    });
  }

  @DataProvider(name = "emptyAndNullNames") public Object[][] emptyAndNullNames() {
    return new Object[][] { { "" }, { null } };
  }
}
