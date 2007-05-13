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

import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;

/**
 * Understands assertion methods for <code>boolean</code> values.
 *
 * @author Alex Ruiz
 */
public final class BooleanAssert {

  private final boolean actual;

  public BooleanAssert(boolean actual) {
    this.actual = actual;
  }
  
  public BooleanAssert isTrue() {
    if (!actual) fail(errorMessageIfNotEqual(Boolean.FALSE, Boolean.TRUE));
    return this;
  }

  public BooleanAssert isFalse() {
    if (actual) fail(errorMessageIfNotEqual(Boolean.TRUE, Boolean.FALSE));
    return this;
  }
}
