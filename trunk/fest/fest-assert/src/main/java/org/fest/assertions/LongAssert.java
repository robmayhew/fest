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

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>long</code>s.
 * 
 * @author Yvonne Wang
 */
public class LongAssert {

  private static final long ZERO = 0L;

  private final long actual;

  LongAssert(long actual) {
    this.actual = actual;
  }

  public LongAssert isEqualTo(long expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  public LongAssert isNotEqualTo(long expected) {
    failIfEqual(actual, expected);
    return this;
  }

  public LongAssert isGreaterThan(long smaller) {
    if (actual <= smaller) failed("should be greater than", smaller);
    return this;
  }

  public LongAssert isLessThan(long bigger) {
    if (actual >= bigger) failed("should be less than", bigger);
    return this;
  }

  private void failed(String reason, long expected) { 
    fail(concat(actual, " ", reason, " ", expected));
  }

  public LongAssert isPositive() { return isGreaterThan(ZERO); }

  public LongAssert isNegative() { return isLessThan(ZERO); }

  public LongAssert isZero() { return isEqualTo(ZERO); }

}
