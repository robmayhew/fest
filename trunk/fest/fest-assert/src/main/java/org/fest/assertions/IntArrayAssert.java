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
package org.fest.assertions;

import java.util.Arrays;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>int</code> arrays. 
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class IntArrayAssert extends GroupAssert<int[]> {

  IntArrayAssert(int... actual) {
    super(actual);
  }

  @Override public IntArrayAssert isNotNull() {
    super.isNotNull();
    return this;
  }
  
  public void isEmpty() {
    if (actual.length > 0) fail(concat("expecting empty array, but was <", Arrays.toString(actual), ">"));
  }

  public IntArrayAssert isNotEmpty() {
    if (actual.length == 0) fail("expecting non-empty array");
    return this;
  }

  @Override public IntArrayAssert isEqualTo(int[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(Arrays.toString(expected), Arrays.toString(actual)));
    return this;
  }

  @Override public IntArrayAssert isNotEqualTo(int[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(Arrays.toString(actual), Arrays.toString(array)));
    return this;
  }

  int actualGroupSize() {
    return actual.length;
  }

  @Override public IntArrayAssert hasSize(int expected) {
    super.hasSize(expected);
    return this;
  }
  
  @Override public IntArrayAssert isSameAs(int[] expected) {
    super.isSameAs(expected);
    return this;
  }

  @Override public IntArrayAssert isNotSameAs(int[] expected) {
    super.isNotSameAs(expected);
    return this;
  }
}
