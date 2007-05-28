/*
 * Created on May 27, 2007
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
package org.fest.swing.util;

import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.method;

import org.fest.swing.GUITest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Understands <code>{@link GUITests}</code>.
 *
 * @author Alex Ruiz
 */
public class GUITestsTest {

  @GUITest public static class GUITestClass {
    @GUITest public void guiTestMethodWithAnnotation() {}
    public void guiTestMethodWithoutAnnotation() {}
  }

  public static class NonGUITestClass {
    @GUITest public void guiTestMethod() {}
  }

  public static class GUITestSubclass extends GUITestClass {}
  
  public static class NonGUITestSubclass extends NonGUITestClass {
    @Override public void guiTestMethod() {
      super.guiTestMethod();
    }
  }
  
  private GUITestClass guiTest;
  private NonGUITestClass nonGUITest;
  private GUITestSubclass guiTestSubclass;
  private NonGUITestSubclass nonGUITestSubclass;
  
  @BeforeClass public void setUp() throws Exception {
    guiTest = new GUITestClass();
    nonGUITest = new NonGUITestClass();
    guiTestSubclass = new GUITestSubclass();
    nonGUITestSubclass = new NonGUITestSubclass();
  }
  
  @Test public void shouldReturnIsGUITestIfClassHasGUITestAnnotation() {
    Class<? extends GUITestClass> guiTestType = guiTest.getClass();
    Method guiTestMethod = method("guiTestMethodWithoutAnnotation").withReturnType(Void.class).in(guiTest).info();
    boolean isGUITest = GUITests.isGUITest(guiTestType, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsGUITestIfOnlyMethodHasGUITestAnnotation() {
    Class<? extends NonGUITestClass> nonGUITestType = nonGUITest.getClass();
    Method guiTestMethod = method("guiTestMethod").withReturnType(Void.class).in(nonGUITest).info();
    boolean isGUITest = GUITests.isGUITest(nonGUITestType, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsGUITestIfSuperclassIsGUITest() {
    Class<? extends GUITestSubclass> guiTestSubtype = guiTestSubclass.getClass();
    Method guiTestMethod = method("guiTestMethodWithoutAnnotation").withReturnType(Void.class).in(guiTest).info();
    boolean isGUITest = GUITests.isGUITest(guiTestSubtype, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsGUITestIfOverridenMethodIsGUITest() {
    Class<? extends NonGUITestSubclass> nonGUITestSubtype = nonGUITestSubclass.getClass();
    Method guiTestMethod = method("guiTestMethod").withReturnType(Void.class).in(nonGUITestSubclass).info();
    boolean isGUITest = GUITests.isGUITest(nonGUITestSubtype, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
   
}
