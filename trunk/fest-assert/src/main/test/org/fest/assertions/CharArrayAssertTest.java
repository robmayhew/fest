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
 * Unit tests for <code>{CharArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CharArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new CharArrayAssert((char[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new CharArrayAssert(new char[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new CharArrayAssert(new char[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new CharArrayAssert((char[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new CharArrayAssert(new char[0]).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new CharArrayAssert('a', 'b').isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new CharArrayAssert('a', 'b').isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new CharArrayAssert(new char[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new CharArrayAssert('a', 'b').isEqualTo('a', 'b');
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new CharArrayAssert('a', 'b').isEqualTo('c', 'd');
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new CharArrayAssert('a', 'b').isNotEqualTo('c', 'd');
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new CharArrayAssert('a', 'b').isNotEqualTo('a', 'b');
  }
}
