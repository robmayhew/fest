package org.fest.assertions;

import static java.lang.String.valueOf;
import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.concat;

public class DoubleAssert {
  private final double actual;

  DoubleAssert(double actual) { this.actual = actual; }

  public DoubleAssert isEqualTo(double expected) {
    if (Double.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(valueOf(actual), valueOf(expected)));
    return this;
  }

  public DoubleAssert isNotEqualTo(double expected) {
    if (Double.compare(actual, expected) == 0) fail(errorMessageIfEqual(valueOf(actual), valueOf(expected)));
    return this;
  }

  public DoubleAssert isGreaterThan(double smaller) {
    if (Double.compare(actual, smaller) <= 0) fail("should be greater than", smaller);
    return this;
  }

  public DoubleAssert isLessThan(double bigger) {
    if (Double.compare(actual, bigger) >= 0) fail("should be less than",  bigger);
    return this;
  }
  
  private void fail(String reason, double expected) {
    fail(concat(valueOf(actual), " ", reason, " ", valueOf(expected)));
  }

  public DoubleAssert isPositive() { return isGreaterThan(0); }

  public DoubleAssert isNegative() { return isLessThan(0); }

  public DoubleAssert isZero() { return isEqualTo(0); }

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
      if (!(Math.abs(expected - actual) <= delta)) fail(errorMessageIfNotEqual(valueOf(actual), valueOf(expected)));
      return doubleAssert;
    }
  }
}
