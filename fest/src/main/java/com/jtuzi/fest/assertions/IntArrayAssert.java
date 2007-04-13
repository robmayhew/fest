/*
 * Created on Mar 19, 2007
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
package com.jtuzi.fest.assertions;

import java.util.Arrays;

import static com.jtuzi.fest.assertions.Fail.errorMessageIfEqual;
import static com.jtuzi.fest.assertions.Fail.errorMessageIfNotEqual;
import static com.jtuzi.fest.assertions.Fail.fail;
import static com.jtuzi.fest.assertions.Fail.failIfNotNull;
import static com.jtuzi.fest.assertions.Fail.failIfNull;

/**
 * Understands assertions for <code>int</code> arrays. 
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class IntArrayAssert {

  private final int[] actual;

  IntArrayAssert(int... actual) {
    this.actual = actual;
  }

  public IntArrayAssert isNull() {
    failIfNotNull(actual);
    return this;
  }

  public IntArrayAssert isNotNull() {
    failIfNull(actual);
    return this;
  }
  
  public IntArrayAssert isEmpty() {
    if (actual.length > 0) fail("expecting empty array, but was <" + Arrays.toString(actual) + ">");
    return this;
  }

  public IntArrayAssert isNotEmpty() {
    if (actual.length == 0) fail("expecting non-empty array");
    return this;
  }

  public IntArrayAssert isEqualTo(int... expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(Arrays.toString(expected), Arrays.toString(actual)));
    return this;
  }

  public IntArrayAssert isNotEqualTo(int... array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(Arrays.toString(actual), Arrays.toString(array)));
    return this;
  }
}
