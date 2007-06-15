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

import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;

/**
 * Understands a template for assertion methods related to arrays or collections.
 * @param the type of object implementations of this template can verify.
 *
 * @author Yvonne Wang
 */
abstract class GroupAssert<T> extends Assert<T> {

  /**
   * Creates a new </code>{@link GroupAssert}</code>.
   * @param actual the object to verify.
   */
  GroupAssert(T actual) {
    super(actual);
  }
  
  abstract void isEmpty();
  
  abstract GroupAssert<T> isNotEmpty();
  
  GroupAssert<T> hasSize(int expected) {
    if (actual == null) fail("the object to verify is null");
    if (actualGroupSize() != expected) fail(errorMessageIfNotEqual(String.valueOf(actual), String.valueOf(expected)));
    return this;
  }

  abstract int actualGroupSize();
}
