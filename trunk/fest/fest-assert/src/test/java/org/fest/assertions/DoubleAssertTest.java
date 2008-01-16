package org.fest.assertions;

import static org.fest.assertions.DoubleAssert.delta;

import org.testng.annotations.Test;

/**
 * Test for <code>{@link DoubleAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class DoubleAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new DoubleAssert(8.68).isEqualTo(8.680);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new DoubleAssert(0.0).isEqualTo(-0.0);
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new DoubleAssert(8.88).isNotEqualTo(8.68);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new DoubleAssert(8.88).isNotEqualTo(8.88);
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new DoubleAssert(0.00).isGreaterThan(-0.00);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotGreaterThanAndExceptedGreaterThan() {
    new DoubleAssert(8.68).isGreaterThan(8.88);
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new DoubleAssert(-0.0).isLessThan(0.0);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotLessThanAndExceptedLessThan() {
    new DoubleAssert(6.68).isLessThan(6.68);
  }

  @Test public void shouldPassIfPositiveAndExpectedPositive() {
    new DoubleAssert(6.68).isPositive();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotPositiveAndExpectedPositive() {
    new DoubleAssert(-6.68).isPositive();
  }
  
  @Test public void shouldPassIfNegativeAndExpectedNegative() {
    new DoubleAssert(-6.68).isNegative();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotNegativeAndExpectedNegative() {
    new DoubleAssert(6.68).isNegative();
  }
  
  @Test public void shouldPassIfNaNAndExpectedNaN() {
    new DoubleAssert(Double.NaN).isNaN();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotNaNAndExpectedNaN() {
    new DoubleAssert(6.68).isNaN();
  }
  
  @Test public void shouldPassIfEqualWithDeltaAndExpectedEqual() {
    new DoubleAssert(8.688).isEqualTo(8.68, delta(0.009));
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotEqualWithDeltaAndExpectedEqual() {
    new DoubleAssert(8.688).isEqualTo(8.888, delta(0.009));
  }  
  
  @Test public void shouldPassIfZeroAndExpectedZero() {
    new DoubleAssert(0).isZero();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotZeroAndExpectedZero() {
    new DoubleAssert(9).isZero();
  }
}
