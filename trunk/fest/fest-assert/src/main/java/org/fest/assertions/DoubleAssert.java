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

  /** {@inheritDoc} */
  public DoubleAssert as(String description) {
    return (DoubleAssert)description(description);
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
   * Intermediate method that returns assertion methods for <code>double</code> values using a delta.
   * <p>
   * This method is expected to be used like this:
   * <pre>
   * double result = total(price, discount);
   * {@link Assertions#assertThat(double) assertThat}(result).withDelta(0.4d).isEqualTo(expected);
   * </pre>
   * </p>
   * @param delta the delta to use.
   * @return the delta-aware assertion object.
   */
  public WithDelta withDelta(double delta) {
    return new WithDelta(actual, delta, this);
  }
  
  /**
   * Understands assertion methods for <code>double</code> values using a positive delta.
   * <p>
   * This class is expected to be used like this:
   * <pre>
   * double result = total(price, discount);
   * {@link Assertions#assertThat(double) assertThat}(result).{@link DoubleAssert#withDelta(double) withDelta}(0.4d).isEqualTo(expected);
   * </pre>
   * </p>
   */
  public static class WithDelta {
    private final double actual;
    private final double delta;
    private final DoubleAssert doubleAssert;

    WithDelta(double actual, double delta, DoubleAssert doubleAssert) {
      this.actual = actual;
      this.delta = delta;
      this.doubleAssert = doubleAssert;
    }

    /**
     * Verifies that the actual <code>double</code> value is equal to the given one, within a positive delta.
     * @param expected the value to compare the actual one to.
     * @return this assertion object.
     * @throws AssertionError if the actual <code>double</code> value is not equal to the given one.
     */
    public DoubleAssert isEqualTo(double expected) {
      if (Double.compare(expected, actual) == 0) return doubleAssert;
      if (!(abs(expected - actual) <= delta)) fail(errorMessageIfNotEqual(doubleAssert.description(), actual, expected));
      return doubleAssert;
    }
  }
}
