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

import java.lang.reflect.AnnotatedElement;

import org.fest.swing.GUITest;

import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

/**
 * Understands TestNG-related utility methods.
 *
 * @author Alex Ruiz
 */
public final class TestNG {

  public static boolean isGUITest(ITestResult testResult) {
    if (isGUITest(testResult.getTestClass())) return true;
    if (isGUITest(testResult.getMethod())) return true;
    return false;
  }
  
  private static boolean isGUITest(IClass testClass) {
    return isGUITest(testClass.getRealClass());
  }
  
  private static boolean isGUITest(ITestNGMethod method) {
    return isGUITest(method.getMethod());
  }

  private static boolean isGUITest(AnnotatedElement annotatedElement) {
    GUITest annotation = annotatedElement.getAnnotation(GUITest.class);
    return annotation != null;
  }
  
  private TestNG() {}
}
