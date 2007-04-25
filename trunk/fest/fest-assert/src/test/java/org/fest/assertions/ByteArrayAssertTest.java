/*
 * Created on Apr 22, 2007
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
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{ByteArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ByteArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new ByteArrayAssert((byte[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new ByteArrayAssert(new byte[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new ByteArrayAssert(new byte[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new ByteArrayAssert((byte[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new ByteArrayAssert(new byte[0]).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new ByteArrayAssert((byte)8, (byte)6).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new ByteArrayAssert((byte)8, (byte)6).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new ByteArrayAssert(new byte[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new ByteArrayAssert((byte)8, (byte)6).isEqualTo((byte)8, (byte)6);
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new ByteArrayAssert((byte)8, (byte)6).isEqualTo((byte)8);
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new ByteArrayAssert((byte)8, (byte)6).isNotEqualTo((byte)8);
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new ByteArrayAssert((byte)8, (byte)6).isNotEqualTo((byte)8, (byte)6);
  }
}
