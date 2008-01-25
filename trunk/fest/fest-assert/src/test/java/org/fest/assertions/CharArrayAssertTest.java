/*
 * Created on Jan 25, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link CharArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CharArrayAssertTest {

  @Test public void shouldPassIfGivenValuesAreInArray() {
    new CharArrayAssert('a', 'b').contains('a', 'b');
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsNotInArray() {
    new CharArrayAssert(emptyArray()).contains('a', 'b');
  }

  @Test public void shouldPassIfGivenValuesAreNotInArray() {
    new CharArrayAssert('a', 'b').excludes('c', 'd');
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsInArray() {
    new CharArrayAssert('a', 'b').excludes('a', 'b');
  }

  @Test public void shouldPassIfArrayIsNull() {
    new CharArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new CharArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new CharArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new CharArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new CharArrayAssert(emptyArray()).isEmpty();
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
    new CharArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new CharArrayAssert('a', 'b').isEqualTo(array('a', 'b'));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new CharArrayAssert('a', 'b').isEqualTo(array('c', 'd'));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new CharArrayAssert('a', 'b').isNotEqualTo(array('c', 'd'));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new CharArrayAssert('a', 'b').isNotEqualTo(array('a', 'b'));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new CharArrayAssert(emptyArray()).containsOnly('a', 'b');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new CharArrayAssert('a', 'b', 'c').containsOnly(array('a', 'b'));
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new CharArrayAssert('a', 'b').containsOnly(array('c', 'd'));
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new CharArrayAssert('a', 'b').containsOnly(array('a', 'b'));    
  }
  
  private char[] nullArray() { return null; }

  private char[] emptyArray() { return new char[0]; }
  
  private char[] array(char... args) { return args; }
}
