package org.fest.assertions;

import org.testng.annotations.Test;

public class DoubleAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new DoubleAssert(8.68).isEqualTo(8.680);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new DoubleAssert(8.88).isEqualTo(8.68);
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new DoubleAssert(8.88).isNotEqualTo(8.68);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new DoubleAssert(8.88).isNotEqualTo(8.88);
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new DoubleAssert(8.88).isGreaterThan(8.68);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotGreaterThanAndExceptedGreaterThan() {
    new DoubleAssert(8.68).isGreaterThan(8.88);
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new DoubleAssert(8.68).isLessThan(8.88);
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
    new DoubleAssert(8.688).withDelta(0.009).isEqualTo(8.68);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotEqualWithDeltaAndExpectedEqual() {
    new DoubleAssert(8.688).withDelta(0.009).isEqualTo(8.888);
  }  
  
  @Test public void shouldPassIfZeroAndExpectedZero() {
    new DoubleAssert(0).isZero();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotZeroAndExpectedZero() {
    new DoubleAssert(9).isZero();
  }
}
