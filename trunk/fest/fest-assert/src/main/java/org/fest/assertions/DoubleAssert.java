package org.fest.assertions;

import static java.lang.Math.abs;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.PrimitiveFail.errorMessageIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotGreaterThan;
import static org.fest.assertions.PrimitiveFail.failIfNotLessThan;

/**
 * Understands Assertion methods for <code>Double</code>.
 *
 * @author Yvonne Wang
 */

public class DoubleAssert {
  
  private static final double ZERO = 0.0;
  
  private final double actual;

  DoubleAssert(double actual) { this.actual = actual; }

  public DoubleAssert isEqualTo(double expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  public DoubleAssert isNotEqualTo(double other) {
    failIfEqual(actual, other);
    return this;
  }

  public DoubleAssert isGreaterThan(double smaller) {
    failIfNotGreaterThan(actual, smaller);
    return this;
  }

  public DoubleAssert isLessThan(double bigger) {
    failIfNotLessThan(actual, bigger);
    return this;
  }
  
  public DoubleAssert isPositive() { return isGreaterThan(ZERO); }

  public DoubleAssert isNegative() { return isLessThan(ZERO); }

  public DoubleAssert isZero() { return isEqualTo(ZERO); }

  public DoubleAssert isNaN() { return isEqualTo(Double.NaN); }
  
  public WithDelta withDelta(double delta) {
    return new WithDelta(actual, delta, this);
  }
  
  public static class WithDelta {
    private final double actual;
    private final double delta;
    private final DoubleAssert doubleAssert;

    WithDelta(double actual, double delta, DoubleAssert doubleAssert) {
      this.actual = actual;
      this.delta = delta;
      this.doubleAssert = doubleAssert;
    }
    
    public DoubleAssert isEqualTo(double expected) {
      if (Double.compare(expected, actual) == 0) return doubleAssert;
      if (!(abs(expected - actual) <= delta)) fail(errorMessageIfNotEqual(actual, expected));
      return doubleAssert;
    }
  }
}
