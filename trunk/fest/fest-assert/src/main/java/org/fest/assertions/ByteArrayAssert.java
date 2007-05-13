/*
 * Created on May 13, 2007
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
import static org.fest.assertions.Fail.failIfNotNull;
import static org.fest.assertions.Fail.failIfNull;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>byte</code> arrays. 
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ByteArrayAssert {

  private final byte[] actual;

  ByteArrayAssert(byte... actual) {
    this.actual = actual;
  }

  public ByteArrayAssert isNull() {
    failIfNotNull(actual);
    return this;
  }

  public ByteArrayAssert isNotNull() {
    failIfNull(actual);
    return this;
  }
  
  public ByteArrayAssert isEmpty() {
    if (actual.length > 0) fail(concat("expecting empty array, but was <", Arrays.toString(actual), ">"));
    return this;
  }

  public ByteArrayAssert isNotEmpty() {
    if (actual.length == 0) fail("expecting non-empty array");
    return this;
  }

  public ByteArrayAssert isEqualTo(byte... expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(Arrays.toString(expected), Arrays.toString(actual)));
    return this;
  }

  public ByteArrayAssert isNotEqualTo(byte... array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(Arrays.toString(actual), Arrays.toString(array)));
    return this;
  }
}
