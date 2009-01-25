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
import org.testng.annotations.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.util.NullAndEmptyStringProvider;
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

  private Jedi jedi;

  @BeforeMethod public void setUp() {
    Jedi.clearCommonPowers();
    jedi = new Jedi("Luke");
  }

  @Test(dataProvider = "nullAndEmptyStrings", dataProviderClass = NullAndEmptyStringProvider.class) 
  public void shouldThrowErrorIfFieldNameIsNullOrEmpty(final String name) {
    expectIllegalArgumentException("The name of the method to access should not be null or empty").on(new CodeToTest() {
      public void run() {
        new MethodName(name);
      }
    });
  }

  @Test public void shouldCallMethodWithArgsAndNoReturnValue() {
    new MethodName("setName").withParameterTypes(String.class).in(jedi).invoke("Leia");
    assertThat(jedi.getName()).isEqualTo("Leia");
  }

  @Test public void shouldCallMethodWithNoArgsAndReturnValue() {
    String personName = new MethodName("getName").withReturnType(String.class).in(jedi).invoke();
    assertThat(personName).isEqualTo("Luke");
  }

  @Test public void shouldCallMethodWithArgsAndReturnType() {
    jedi.addPower("Healing");
    String power = new MethodName("powerAt").withReturnType(String.class).withParameterTypes(int.class).in(jedi).invoke(0);
    assertThat(power).isEqualTo("Healing");
  }

  @Test public void shouldCallMethodWithNoArgsAndNoReturnValue() {
    assertThat(jedi.isMaster()).isFalse();
    new MethodName("makeMaster").in(jedi).invoke();
    assertThat(jedi.isMaster()).isTrue();
  }

  @Test public void shouldReturnMethodInfo() {
    java.lang.reflect.Method method = new MethodName("setName").withParameterTypes(String.class).in(jedi).info();
    assertThat(method).isNotNull();
    assertThat(method.getName()).isEqualTo("setName");
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertThat(parameterTypes).hasSize(1);
    assertThat(parameterTypes[0]).isEqualTo(String.class);
  }

  @Test public void shouldThrowErrorIfInvalidMethodName() {
    String message = "Unable to find method 'getAge' in org.fest.reflect.Jedi with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "getAge";
        new MethodName(invalidName).withReturnType(Integer.class).in(jedi);
      }
    });
  }

  @Test public void shouldThrowErrorIfInvalidArgs() {
    expectReflectionError("Unable to invoke method 'setName' with arguments [8]").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        new MethodName("setName").withParameterTypes(String.class).in(jedi).invoke(invalidArg);
      }
    });
  }

  @Test(dataProvider = "nullAndEmptyStrings", dataProviderClass = NullAndEmptyStringProvider.class) 
  public void shouldThrowErrorIfStaticFieldNameIsNullOrEmpty(final String name) {
    expectIllegalArgumentException("The name of the method to access should not be null or empty").on(new CodeToTest() {
      public void run() {
        new StaticMethodName(name);
      }
    });
  }

  @Test public void shouldCallStaticMethodWithArgsAndReturnType() {
    Jedi.addCommonPower("Jump");
    String method = "commonPowerAt";
    String power =
      new StaticMethodName(method).withReturnType(String.class).withParameterTypes(int.class).in(Jedi.class).invoke(0);
    assertThat(power).isEqualTo("Jump");
  }

  @Test public void shouldStaticCallMethodWithArgsAndNoReturnValue() {
    new StaticMethodName("addCommonPower").withParameterTypes(String.class).in(Jedi.class).invoke("Jump");
    assertThat(Jedi.commonPowerAt(0)).isEqualTo("Jump");
  }

  @Test public void shouldCallStaticMethodWithNoArgsAndNoReturnValue() {
    Jedi.addCommonPower("Jump");
    assertThat(Jedi.commonPowerCount()).isEqualTo(1);
    assertThat(Jedi.commonPowerAt(0)).isEqualTo("Jump");
    new StaticMethodName("clearCommonPowers").in(Jedi.class).invoke();
    assertThat(Jedi.commonPowerCount()).isEqualTo(0);
  }

  @Test public void shouldCallStaticMethodWithReturnValueAndNoArgs() {
    Jedi.addCommonPower("Jump");
    int count = new StaticMethodName("commonPowerCount").withReturnType(int.class).in(Jedi.class).invoke();
    assertThat(count).isEqualTo(Jedi.commonPowerCount());
  }
  
  @Test public void shouldThrowErrorIfInvalidStaticMethodName() {
    String message = "Unable to find method 'powerSize' in org.fest.reflect.Jedi with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "powerSize";
        new StaticMethodName(invalidName).in(Jedi.class);
      }
    });
  }

  @Test public void shouldThrowErrorIfInvalidArgsForStaticMethod() {
    expectReflectionError("Unable to invoke method 'addCommonPower' with arguments [8]").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        new StaticMethodName("addCommonPower").withParameterTypes(String.class).in(Jedi.class).invoke(invalidArg);
      }
    });
  }
}
