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
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands assertion methods for <code>String</code>s.
 *
 * @author Yvonne Wang
 */
public final class StringAssert extends GroupAssert<String> {

  StringAssert(String actual) {
    super(actual);
  }
  
  public void isEmpty() {
    if (!Strings.isEmpty(actual)) fail(concat("the String ", quote(actual), " should be empty"));
  }
  
  public StringAssert isNotEmpty() {
    if (Strings.isEmpty(actual)) fail(concat("the String ", quote(actual), " should not be empty"));   
    return this;
  }
  
  @Override public StringAssert isEqualTo(String expected) {
    super.isEqualTo(expected);
    return this;
  }
  
  @Override public StringAssert isNotEqualTo(String obj) {
    super.isNotEqualTo(obj);
    return this;
  }

  @Override public StringAssert isNotNull() {
    super.isNotNull();
    return this;
  }

  @Override public StringAssert isNotSameAs(String expected) {
    super.isNotSameAs(expected);
    return this;
  }

  @Override public StringAssert isSameAs(String expected) {
    super.isSameAs(expected);
    return this;
  }

  @Override public StringAssert hasSize(int expected) {
    super.hasSize(expected);
    return this;
  }

  int actualGroupSize() {
    return actual.length();
  }

  public StringAssert contains(String expected) {
    if (actual.indexOf(expected) == -1) 
      fail(concat("the String ", quote(actual), " should contain the String ", quote(expected)));
    return this;
  } 
}
