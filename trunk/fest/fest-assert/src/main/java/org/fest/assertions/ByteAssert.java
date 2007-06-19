/*
 * Created on Jun 18, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static java.lang.String.valueOf;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Fail.failIfEqual;
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>byte</code>s.
 * 
 * @author Yvonne Wang
 */
public final class ByteAssert {

  private final byte actual;

  private static final byte ZERO = (byte)0;

  ByteAssert(byte actual) {
    this.actual = actual;
  }

  public ByteAssert isEqualTo(byte expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  public ByteAssert isNotEqualTo(byte expected) {
    failIfEqual(actual, expected);
    return this;
  }

  public ByteAssert isGreaterThan(byte smaller) {
    if (actual <= smaller) failed("should be greater than", smaller);
    return this;
  }

  public ByteAssert isLessThan(byte bigger) {
    if (actual >= bigger) failed("should be less than", bigger);
    return this;
  }

  private void failed(String reason, int expected) {
    fail(concat(valueOf(actual), " ", reason, " ", valueOf(expected)));
  }

  public ByteAssert isPositive() { return isGreaterThan(ZERO); }

  public ByteAssert isNegative() { return isLessThan(ZERO); }

  public ByteAssert isZero() { return isEqualTo(ZERO); }
}
