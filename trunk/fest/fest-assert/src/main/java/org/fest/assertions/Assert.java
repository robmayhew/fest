/*
 * Created on May 21, 2007
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

import static org.fest.assertions.Fail.failIfEqual;
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.assertions.Fail.failIfNotNull;
import static org.fest.assertions.Fail.failIfNotSame;
import static org.fest.assertions.Fail.failIfNull;
import static org.fest.assertions.Fail.failIfSame;

/**
 * Understands a template for assertion methods.
 * @param the type of object implementations of this template can verify.
 *
 * @author Yvonne Wang
 */
abstract class Assert<T> {

  final T actual;

  /**
   * Creates a new </code>{@link Assert}</code>.
   * @param actual the actual target to verify.
   */
  Assert(T actual) {
    this.actual = actual;
  }

  public final void isNull() {
    failIfNotNull(actual);
  }

  Assert<T> isNotNull() {
    failIfNull(actual);
    return this;
  }
  
  Assert<T> isSameAs(T expected) {
    failIfNotSame(actual, expected);
    return this;
  }
  
  Assert<T> isNotSameAs(T expected) {
    failIfSame(actual, expected);
    return this;
  }
  
  Assert<T> isEqualTo(T expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  Assert<T> isNotEqualTo(T obj) {
    failIfEqual(actual, obj);
    return this;
  }
}
