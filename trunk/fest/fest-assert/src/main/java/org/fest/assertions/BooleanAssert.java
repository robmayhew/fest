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
package org.fest.assertions;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.PrimitiveFail.errorMessageIfNotEqual;

/**
 * Understands assertion methods for <code>boolean</code> values. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(boolean)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class BooleanAssert extends PrimitiveAssert {

  private final boolean actual;

  BooleanAssert(boolean actual) {
    this.actual = actual;
  }
  
  /** {@inheritDoc} */
  public BooleanAssert as(String description) {
    return (BooleanAssert) description(description);
  }
  
  /** {@inheritDoc} */
  public BooleanAssert describedAs(String description) {
    return as(description);
  }
  
  /**
   * Verifies that the actual <code>boolean</code> value is <code>true</code>.
   * @throws AssertionError if the actual <code>boolean</code> value is <code>false</code>.
   */
  public void isTrue() {
    if (!actual) fail(errorMessageIfNotEqual(description(), actual, true));
  }

  /**
   * Verifies that the actual <code>boolean</code> value is <code>false</code>.
   * @throws AssertionError if the actual <code>boolean</code> value is <code>true</code>.
   */
  public void isFalse() {
    if (actual) fail(errorMessageIfNotEqual(description(), actual, false));
  }
}
