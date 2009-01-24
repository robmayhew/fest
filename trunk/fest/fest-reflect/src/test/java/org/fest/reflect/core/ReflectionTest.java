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
package org.fest.reflect.core;

import org.testng.annotations.Test;

import org.fest.reflect.constructor.TargetType;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link Reflection}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ReflectionTest {

  @Test public void shouldReturnConstructorFactory() {
    assertThat(Reflection.constructor()).isInstanceOf(TargetType.class);
  }
  
  @Test public void shouldReturnFieldFactory() {
    assertThat(Reflection.field("field")).isInstanceOf(org.fest.reflect.field.FieldName.class);
  }

  @Test public void shouldReturnStaticFieldFactory() {
    assertThat(Reflection.staticField("field")).isInstanceOf(org.fest.reflect.field.StaticFieldName.class);
  }

  @Test public void shouldReturnMethodFactory() {
    assertThat(Reflection.method("method")).isInstanceOf(org.fest.reflect.method.MethodName.class);
  }

  @Test public void shouldReturnStaticMethodFactory() {
    assertThat(Reflection.staticMethod("method")).isInstanceOf(org.fest.reflect.method.StaticMethodName.class);
  }
}
