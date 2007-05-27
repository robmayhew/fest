/*
 * Created on May 27, 2007
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

import javax.annotation.Generated;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>short</code> arrays. 
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Generated(value = "org.fest.assertions.PrimitiveArrayAssertGenerator", 
           date = "2007-05-27T04:44:16", 
           comments = "Generated using Velocity template 'org/fest/assertions/ArrayAssertTemplate.vm'")
public final class ShortArrayAssert extends GroupAssert<short[]> {

  ShortArrayAssert(short... actual) {
    super(actual);
  }

  @Override public ShortArrayAssert isNotNull() {
    return (ShortArrayAssert)super.isNotNull();
  }
  
  public void isEmpty() {
    if (actual.length > 0) fail(concat("expecting empty array, but was <", Arrays.toString(actual), ">"));
  }

  public ShortArrayAssert isNotEmpty() {
    if (actual.length == 0) fail("expecting non-empty array");
    return this;
  }

  @Override public ShortArrayAssert isEqualTo(short[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(Arrays.toString(expected), Arrays.toString(actual)));
    return this;
  }

  @Override public ShortArrayAssert isNotEqualTo(short[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(Arrays.toString(actual), Arrays.toString(array)));
    return this;
  }

  int actualGroupSize() {
    return actual.length;
  }

  @Override public ShortArrayAssert hasSize(int expected) {
    return (ShortArrayAssert)super.hasSize(expected);
  }
  
  @Override public ShortArrayAssert isSameAs(short[] expected) {
    return (ShortArrayAssert)super.isSameAs(expected);
  }

  @Override public ShortArrayAssert isNotSameAs(short[] expected) {
    return (ShortArrayAssert)super.isNotSameAs(expected);
  }
}
