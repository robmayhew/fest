/*
 * Created on May 22, 2007
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
package org.fest.swing.testng;

import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.GUITest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TestNG}</code>.
 *
 * @author Alex Ruiz
 */
public class TestNGTest {

  @GUITest public static class SomeGUITestClass {
    @GUITest public void someGUITestMethod() {}
    public void someNonGUITestMethod() {}
  }

  public static class SomeNonGUITestClass {
    @GUITest public void someGUITestMethod() {}
  }
  
  private TestResultStub testResult;
  
  @BeforeMethod public void setUp() {
    testResult = new TestResultStub();
  }
  
  @Test public void shouldReturnIsGUITestIfClassHasGUITestAnnotation() {
    testResult.getTestClass().setRealClass(SomeGUITestClass.class);
    assertThat(TestNG.isGUITest(testResult)).isTrue();
  }

  @Test public void shouldReturnIsGUITestIfMethodHasGUITestAnnotation() throws Exception {
    setUpStubs(SomeNonGUITestClass.class, "someGUITestMethod");
    assertThat(TestNG.isGUITest(testResult)).isTrue();
  }

  @Test public void shouldReturnIsGUITestIfBothClassAndMethodHaveGUITestAnnotation() throws Exception {
    setUpStubs(SomeGUITestClass.class, "someGUITestMethod");
    assertThat(TestNG.isGUITest(testResult)).isTrue();
  }
  
  @Test public void shouldReturnIsNotGUITestIfClassAndMethodDoNotHaveGUITestAnnotation() throws Exception {
    setUpStubs(String.class, "concat", String.class);
    assertThat(TestNG.isGUITest(testResult)).isFalse();
  }

  private void setUpStubs(Class<?> realClass, String methodName, Class<?>... parameterTypes) throws Exception {
    testResult.getTestClass().setRealClass(realClass);
    Method realMethod = realClass.getDeclaredMethod(methodName, parameterTypes);
    testResult.getMethod().setMethod(realMethod);
  }
}
