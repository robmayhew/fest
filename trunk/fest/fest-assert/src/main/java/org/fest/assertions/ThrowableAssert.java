/*
 * Created on Dec 23, 2007
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

import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Strings.concat;
import static org.fest.assertions.Fail.*;

/**
 * Understands assertion methods for <code>{@link Throwable}</code>. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Throwable)}</code>.
 *
 * @author David DIDIER
 * @author Alex Ruiz
 */
public final class ThrowableAssert extends GenericAssert<Throwable> {

  private final ObjectAssert objectAssert;

  /**
   * Creates a new <code>ThrowableAssert</code>.
   * @param actual the actual <code>Throwable</code> to test.
   */
  ThrowableAssert(Throwable actual) {
    super(actual);
    objectAssert = new ObjectAssert(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>.
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(error).<strong>as</strong>(&quot;Number Formatting&quot;).isInstanceOf(NumberFormatException.class);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert as(String description) {
    objectAssert.as(description);
    return (ThrowableAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(error).<strong>describedAs</strong>(&quot;Number Formatting&quot;).isInstanceOf(NumberFormatException.class);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is an instance of the given type.
   * @param type the type to check the actual <code>Throwable</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is not an instance of the given type.
   * @throws IllegalArgumentException if the given type is <code>null</code>.
   */
  public ThrowableAssert isInstanceOf(Class<? extends Throwable> type) {
    objectAssert.isInstanceOf(type);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is an instance of the given type. In order for the assertion to
   * pass, the type of the actual <code>Throwable</code> has to be exactly the same as the given type.
   * @param type the type to check the actual <code>Throwable</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is not an instance of the given type.
   * @throws IllegalArgumentException if the given type is <code>null</code>.
   */
  public ThrowableAssert isExactlyInstanceOf(Class<?> type) {
    isNotNull();
    objectAssert.validateTypeToCheckAgainst(type);
    Class<?> current = actual.getClass();
    if (!type.equals(current))
      fail(concat("expected exactly the same type:", inBrackets(type), " but was:", inBrackets(current)));
    return this;
  }

  /**
   * Verifies that the message of the actual <code>Throwable</code> is equal to the given one.
   * @param message the expected message.
   * @return this assertion error.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the message of the actual <code>Throwable</code> is not equal to the given one.
   */
  public ThrowableAssert hasMessage(String message) {
    isNotNull();
    failIfNotEqual(description(), actual.getMessage(), message);
    return this;
  }
  
  /**
   * Returns a <code>{@link StringAssert}</code> wrapping the message of the actual <code>Throwable</code>.
   * @return a <code>StringAssert</code> wrapping the message of the actual <code>Throwable</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   */
  public StringAssert message() {
    isNotNull();
    return new StringAssert(actual.getMessage());
  }
  
  /**
   * Returns the cause of the actual <code>Throwable</code>, wrapped in a <code>{@link ThrowableAssert}</code>.
   * @return a <code>ThrowableAssert</code> containing the cause of the actual <code>Throwable</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   */
  public ThrowableAssert cause() {
    isNotNull();
    return new ThrowableAssert(actual.getCause());
  }

  /**
   * Returns the hierarchy of causes of the the actual <code>Throwable</code>, wrapped in a
   * <code>{@link CauseHierarchyAssert}</code>.
   * @return a <code>CauseHierarchyAssert</code> containing the hierarchy of causes of the actual
   *          <code>Throwable</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   */
  public CauseHierarchyAssert causeHierarchy() {
    isNotNull();
    return new CauseHierarchyAssert(this);
  }

  /**
   * Verifies that the actual <code>Throwable</code> does not have a cause.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Throwable</code> has a cause.
   */
  public ThrowableAssert hasNoCause() {
    isNotNull();
    Throwable actualCause = actual.getCause();
    if (actualCause != null)
      fail(concat("expected exception without cause, but cause was:", inBrackets(actualCause.getClass())));
    return this;
  }

  /**
   * Understands assertion methods for the hierarchy of causes in a <code>{@link Throwable}</code>.
   *
   * @author Alex Ruiz
   * @author David DIDIER
   */
  public static class CauseHierarchyAssert {
    private final ThrowableAssert throwableAssert;

    CauseHierarchyAssert(ThrowableAssert throwableAssert) {
      this.throwableAssert = throwableAssert;
    }

    /**
     * Verifies that the actual <code>Throwable</code> has a cause of the given type anywhere in its cause hierarchy.
     * The test is <code>true</code> if <code>type</code> is a subclass of one of the causes of the actual
     * <code>Throwable</code>.
     * @param type the type to check the hierarchy of causes in the actual <code>Throwable</code> against.
     * @return this assertion object.
     * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
     * @throws AssertionError if the actual <code>Throwable</code> does not have a cause of the given type somewhere in
     *          its cause hierarchy.
     * @throws IllegalArgumentException if the type to check against is <code>null</code>.
     */
    public CauseHierarchyAssert hasCauseOfType(Class<? extends Throwable> type) {
      throwableAssert.isNotNull();
      throwableAssert.objectAssert.validateTypeToCheckAgainst(type);
      if (!causeOfTypeFound(type))
        throwableAssert.fail(concat("expected a cause of type:", inBrackets(type), ", but found none"));
      return this;
    }

    private boolean causeOfTypeFound(Class<? extends Throwable> type) {
      Throwable cause = throwableAssert.actual.getCause();
      while (cause != null) {
        if (type.isAssignableFrom(cause.getClass())) return true;
        cause = cause.getCause();
      }
      return false;
    }

    /**
     * Verifies that the actual <code>Throwable</code> has a cause of the given type anywhere in its cause hierarchy.
     * The test is <code>true</code> if <code>type</code> is strictly equal of one of the causes of the actual
     * <code>Throwable</code>.
     * @param type the type to check the hierarchy of causes in the actual <code>Throwable</code> against.
     * @return this assertion object.
     * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
     * @throws AssertionError if the actual <code>Throwable</code> does not have a cause of the given type somewhere in
     *          its cause hierarchy.
     * @throws IllegalArgumentException if the type to check against is <code>null</code>.
     */
    public CauseHierarchyAssert hasCauseOfExactType(Class<? extends Throwable> type) {
      throwableAssert.isNotNull();
      throwableAssert.objectAssert.validateTypeToCheckAgainst(type);
      if (!causeOfExactTypeFound(type))
        throwableAssert.fail(concat("expected a cause of exact type:", inBrackets(type), ", but found none"));
      return this;
    }

    private boolean causeOfExactTypeFound(Class<? extends Throwable> type) {
      Throwable cause = throwableAssert.actual.getCause();
      while (cause != null) {
        if (cause.getClass().equals(type)) return true;
        cause = cause.getCause();
      }
      return false;
    }
  }

  /**
   * Verifies that the actual <code>Throwable</code> is equal to the given one.
   * @param expected the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is not equal to the given one.
   */
  public ThrowableAssert isEqualTo(Throwable expected) {
    return (ThrowableAssert)assertEqualTo(expected);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not equal to the given one.
   * @param other the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is equal to the given one.
   */
  public ThrowableAssert isNotEqualTo(Throwable other) {
    return (ThrowableAssert)assertNotEqualTo(other);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not <code>null</code>.
   *
   * @return this assertion object.
   *
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   */
  public ThrowableAssert isNotNull() {
    return (ThrowableAssert)assertNotNull();
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not the same as the given one.
   * @param other the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is the same as the given one.
   */
  public ThrowableAssert isNotSameAs(Throwable other) {
    return (ThrowableAssert)assertNotSameAs(other);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is the same as the given one.
   * @param expected the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is not the same as the given one.
   */
  public ThrowableAssert isSameAs(Throwable expected) {
    return (ThrowableAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>Throwable</code> satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public ThrowableAssert satisfies(Condition<Throwable> condition) {
    return (ThrowableAssert)verify(condition);
  }
}
