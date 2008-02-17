/*
 * Created on Dec 27, 2006
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

import java.math.BigDecimal;

/**
 * Understands assertion methods for <code>{@link BigDecimal}</code>. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(BigDecimal)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class BigDecimalAssert extends GenericAssert<BigDecimal> {

  BigDecimalAssert(BigDecimal actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(&quot;Result&quot;).isNotNull();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BigDecimalAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(&quot;Result&quot;).isNotNull();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BigDecimalAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <<code>{@link BigDecimal}</code> satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public BigDecimalAssert satisfies(Condition<BigDecimal> condition) {
    verify(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   */
  public BigDecimalAssert isNotNull() {
    assertNotNull();
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is the same as the given one.
   * @param expected the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not the same as the given one.
   */
  public BigDecimalAssert isSameAs(BigDecimal expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is not the same as the given one.
   * @param other the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is the same as the given one.
   */
  public BigDecimalAssert isNotSameAs(BigDecimal other) {
    assertNotSameAs(other);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is equal to the given one.
   * @param expected the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not equal to the given one.
   */
  public BigDecimalAssert isEqualTo(BigDecimal expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is not equal to the given one.
   * @param other the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is equal to the given one.
   */
  public BigDecimalAssert isNotEqualTo(BigDecimal other) {
    assertNotEqualTo(other);
    return this;
  }
}
