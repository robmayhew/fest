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
import org.fest.test.CodeToTest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.util.ExpectedFailures.*;

/**
 * Tests for the fluent interface for methods.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class MethodTest {

  private Jedi jedi;

  @BeforeMethod public void setUp() {
    Jedi.clearCommonPowers();
    jedi = new Jedi("Luke");
  }

  public void shouldThrowErrorIfMethodNameIsNull() {
    expectNullPointerException("The name of the method to access should not be null").on(new CodeToTest() {
      public void run() {
        new MethodName(null);
      }
    });
  }

  public void shouldThrowErrorIfMethodNameIsEmpty() {
    expectIllegalArgumentException("The name of the method to access should not be empty").on(new CodeToTest() {
      public void run() {
        new MethodName("");
      }
    });
  }
  
  public void shouldThrowErrorIfMethodReturnTypeIsNull() {
    expectNullPointerException("The return type of the method to access should not be null").on(new CodeToTest() {
      public void run() {
        new MethodName("setName").withReturnType(null);
      }
    });
  }
  
  public void shouldThrowErrorIfMethodParameterTypeArrayIsNull() {
    expectNullPointerException("The array of parameter types should not be null").on(new CodeToTest() {
      public void run() {
        Class<?>[] parameterTypes = null;
        new MethodName("setName").withParameterTypes(parameterTypes);
      }
    });
  }
  
  public void shouldThrowErrorIfMethodTargetIsNull() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        new MethodName("setName").in(null);
      }
    });
  }

  public void shouldCallMethodWithArgsAndNoReturnValue() {
    new MethodName("setName").withParameterTypes(String.class).in(jedi).invoke("Leia");
    assertThat(jedi.getName()).isEqualTo("Leia");
  }

  public void shouldCallMethodWithNoArgsAndReturnValue() {
    String personName = new MethodName("getName").withReturnType(String.class).in(jedi).invoke();
    assertThat(personName).isEqualTo("Luke");
  }

  public void shouldCallMethodWithArgsAndReturnType() {
    jedi.addPower("Healing");
    String power = new MethodName("powerAt").withReturnType(String.class).withParameterTypes(int.class).in(jedi).invoke(0);
    assertThat(power).isEqualTo("Healing");
  }

  public void shouldCallMethodWithNoArgsAndNoReturnValue() {
    assertThat(jedi.isMaster()).isFalse();
    new MethodName("makeMaster").in(jedi).invoke();
    assertThat(jedi.isMaster()).isTrue();
  }

  public void shouldReturnMethodInfo() {
    java.lang.reflect.Method method = new MethodName("setName").withParameterTypes(String.class).in(jedi).info();
    assertThat(method).isNotNull();
    assertThat(method.getName()).isEqualTo("setName");
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertThat(parameterTypes).hasSize(1);
    assertThat(parameterTypes[0]).isEqualTo(String.class);
  }

  public void shouldThrowErrorIfInvalidMethodName() {
    String message = "Unable to find method 'getAge' in org.fest.reflect.Jedi with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "getAge";
        new MethodName(invalidName).withReturnType(Integer.class).in(jedi);
      }
    });
  }

  public void shouldThrowErrorIfInvalidArgs() {
    expectReflectionError("Unable to invoke method 'setName' with arguments [8]").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        new MethodName("setName").withParameterTypes(String.class).in(jedi).invoke(invalidArg);
      }
    });
  }

  public void shouldThrowErrorIfStaticMethodNameIsNull() {
    expectNullPointerException("The name of the method to access should not be null").on(new CodeToTest() {
      public void run() {
        new StaticMethodName(null);
      }
    });
  }

  public void shouldThrowErrorIfStaticMethodNameIsEmpty() {
    expectIllegalArgumentException("The name of the method to access should not be empty").on(new CodeToTest() {
      public void run() {
        new StaticMethodName("");
      }
    });
  }

  public void shouldThrowErrorIfStaticMethodReturnTypeIsNull() {
    expectNullPointerException("The return type of the method to access should not be null").on(new CodeToTest() {
      public void run() {
        new StaticMethodName("commonPowerCount").withReturnType(null);
      }
    });
  }

  public void shouldThrowErrorIfStaticMethodParameterTypeArrayIsNull() {
    expectNullPointerException("The array of parameter types should not be null").on(new CodeToTest() {
      public void run() {
        Class<?>[] parameterTypes = null;
        new StaticMethodName("commonPowerCount").withParameterTypes(parameterTypes);
      }
    });
  }
  public void shouldThrowErrorIfStaticMethodTargetIsNull() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        new StaticMethodName("commonPowerCount").in(null);
      }
    });
  }

  public void shouldCallStaticMethodWithArgsAndReturnType() {
    Jedi.addCommonPower("Jump");
    String method = "commonPowerAt";
    String power =
      new StaticMethodName(method).withReturnType(String.class).withParameterTypes(int.class).in(Jedi.class).invoke(0);
    assertThat(power).isEqualTo("Jump");
  }

  public void shouldStaticCallMethodWithArgsAndNoReturnValue() {
    new StaticMethodName("addCommonPower").withParameterTypes(String.class).in(Jedi.class).invoke("Jump");
    assertThat(Jedi.commonPowerAt(0)).isEqualTo("Jump");
  }

  public void shouldCallStaticMethodWithNoArgsAndNoReturnValue() {
    Jedi.addCommonPower("Jump");
    assertThat(Jedi.commonPowerCount()).isEqualTo(1);
    assertThat(Jedi.commonPowerAt(0)).isEqualTo("Jump");
    new StaticMethodName("clearCommonPowers").in(Jedi.class).invoke();
    assertThat(Jedi.commonPowerCount()).isEqualTo(0);
  }

  public void shouldCallStaticMethodWithReturnValueAndNoArgs() {
    Jedi.addCommonPower("Jump");
    int count = new StaticMethodName("commonPowerCount").withReturnType(int.class).in(Jedi.class).invoke();
    assertThat(count).isEqualTo(Jedi.commonPowerCount());
  }
  
  public void shouldThrowErrorIfInvalidStaticMethodName() {
    String message = "Unable to find method 'powerSize' in org.fest.reflect.Jedi with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "powerSize";
        new StaticMethodName(invalidName).in(Jedi.class);
      }
    });
  }

  public void shouldThrowErrorIfInvalidArgsForStaticMethod() {
    expectReflectionError("Unable to invoke method 'addCommonPower' with arguments [8]").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        new StaticMethodName("addCommonPower").withParameterTypes(String.class).in(Jedi.class).invoke(invalidArg);
      }
    });
  }
}
