/*
 * Created on Dec 26, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.assertions;

import org.fest.util.Strings;


import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Fail.failIfNotEqual;

import static org.fest.util.Strings.quote;

import static org.fest.util.Strings.*;

/**
 * Understands constraints on Strings.
 *
 * @author Yvonne Wang
 */
public final class StringAssert {

  private final String actual;

  StringAssert(String target) {
    this.actual = target;
  }
  
  public StringAssert isEmpty() {
    if (!Strings.isEmpty(actual)) fail(concat("the String ", quote(actual), " should be empty"));
    return this;
  }
  
  public StringAssert isEqualTo(String expected) {
    failIfNotEqual(actual, expected);
    return this;
  }
  
  public StringAssert constainsString(String expected) {
    if (actual.indexOf(expected) == -1) 
      fail(concat("the String ", quote(actual), " should contain the String ", quote(expected)));
    return this;
  }
}
