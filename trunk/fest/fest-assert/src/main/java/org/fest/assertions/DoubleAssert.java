package org.fest.assertions;

import static java.lang.Math.abs;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.PrimitiveFail.errorMessageIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotGreaterThan;
import static org.fest.assertions.PrimitiveFail.failIfNotLessThan;

/**
 * Understands Assertion methods for <code>Double</code>. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(double)}</code>.
 *
 * @author Yvonne Wang
 */
public final class DoubleAssert extends PrimitiveAssert {
  
  private static final double ZERO = 0.0;
  
  private final double actual;

  DoubleAssert(double actual) { this.actual = actual; }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleAssert as(String description) {
    return (DoubleAssert)description(description);
  }
  
  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in 
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>double</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is not equal to the given one.
   */
  public DoubleAssert isEqualTo(double expected) {
    failIfNotEqual(description(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> value is not equal to the given one.
   * @param other the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is equal to the given one.
   */
  public DoubleAssert isNotEqualTo(double other) {
    failIfEqual(description(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> value is greater than the given one.
   * @param smaller the value expected to be smaller than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is less than or equal to the given one.
   */
  public DoubleAssert isGreaterThan(double smaller) {
    failIfNotGreaterThan(description(), actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> value is less than the given one.
   * @param bigger the value expected to be bigger than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is greater than or equal to the given one.
   */
  public DoubleAssert isLessThan(double bigger) {
    failIfNotLessThan(description(), actual, bigger);
    return this;
  }
  
  /**
   * Verifies that the actual <code>double</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is not positive.
   */
  public DoubleAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>double</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is not negative.
   */
  public DoubleAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>double</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is not equal to zero.
   */
  public DoubleAssert isZero() { return isEqualTo(ZERO); }

  /**
   * Verifies that the actual <code>double</code> value is equal to <code>{@link Double#NaN}</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is not equal to <code>NAN</code>.
   */
  public DoubleAssert isNaN() { return isEqualTo(Double.NaN); }

  /**
   * Verifies that the actual <code>double</code> value is equal to the given one, within a positive delta.
   * @param expected the value to compare the actual one to.
   * @param delta the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> value is not equal to the given one.
   */
  public DoubleAssert isEqualTo(double expected, Delta delta) {
    if (Double.compare(expected, actual) == 0) return this;
    if (!(abs(expected - actual) <= delta.value)) fail(errorMessageIfNotEqual(description(), actual, expected));    
    return this;
  }
  
  /**
   * Creates a new holder for a delta value to be used in 
   * <code>{@link DoubleAssert#isEqualTo(double, org.fest.assertions.DoubleAssert.Delta)}</code>.
   * @param d the delta value.
   * @return a new delta value holder.
   */
  public static Delta delta(double d) {
    return new Delta(d);
  }
  
  /**
   * Holds a delta value to be used in
   * <code>{@link DoubleAssert#isEqualTo(double, org.fest.assertions.DoubleAssert.Delta)}</code>.
   */
  public static class Delta {
    final double value;

    private Delta(double value) {
      this.value = value;
    }
  }
}
