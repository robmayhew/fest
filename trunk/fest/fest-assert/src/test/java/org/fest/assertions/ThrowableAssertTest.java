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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ThrowableAssert}</code>.
 *
 * @author David DIDIER
 */
public class ThrowableAssertTest {

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasACause() {
    new ThrowableAssert(new Exception(new IOException())).hasNoCause();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedCause1() {
    new ThrowableAssert(new Exception()).hasCause(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedCause2() {
    new ThrowableAssert(new Exception()).hasCause(IOException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedCause3() {
    new ThrowableAssert(new Exception(new IOException())).hasCause(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedCause4() {
    new ThrowableAssert(new Exception(new IOException())).hasCause(FileNotFoundException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedCauseAsAncestor1() {
    new ThrowableAssert(new Exception()).hasCauseAsAncestor(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedCauseAsAncestor2() {
    new ThrowableAssert(new Exception()).hasCauseAsAncestor(IOException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedCauseAsAncestor3() {
    new ThrowableAssert(new Exception(new RuntimeException(new IllegalStateException()))).hasCauseAsAncestor(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedCauseAsAncestor4() {
    Exception e = new Exception(new RuntimeException(new IllegalStateException()));
    new ThrowableAssert(e).hasCauseAsAncestor(FileNotFoundException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedExactCause1() {
    new ThrowableAssert(new Exception()).hasExactCause(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedExactCause2() {
    new ThrowableAssert(new Exception()).hasExactCause(IOException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedExactCause3() {
    new ThrowableAssert(new Exception(new IOException())).hasExactCause(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedExactCause4() {
    new ThrowableAssert(new Exception(new IOException())).hasExactCause(FileNotFoundException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedExactCause5() {
    new ThrowableAssert(new Exception(new FileNotFoundException())).hasExactCause(IOException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedExactCauseAsAncestor1() {
    new ThrowableAssert(new Exception()).hasExactCauseAsAncestor(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedExactCauseAsAncestor2() {
    new ThrowableAssert(new Exception()).hasExactCauseAsAncestor(IOException.class);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldFailIfThrowableHasNotExpectedExactCauseAsAncestor3() {
    new ThrowableAssert(new Exception(new RuntimeException(new IllegalStateException()))).hasExactCauseAsAncestor(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedExactCauseAsAncestor4() {
    Exception e = new Exception(new RuntimeException(new IllegalStateException()));
    new ThrowableAssert(e).hasExactCauseAsAncestor(FileNotFoundException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableHasNotExpectedExactCauseAsAncestor5() {
    Exception e = new Exception(new IllegalStateException(new FileNotFoundException()));
    new ThrowableAssert(e).hasExactCauseAsAncestor(IOException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNull1() {
    new ThrowableAssert(null).hasCause(IOException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNull2() {
    new ThrowableAssert(null).hasExactCauseAsAncestor(IOException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNull3() {
    new ThrowableAssert(null).hasNoCause();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNull4() {
    new ThrowableAssert(null).hasExactCause(IOException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNull5() {
    new ThrowableAssert(null).hasCauseAsAncestor(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedCause1() {
    new ThrowableAssert(new Exception(new IOException())).hasCause(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedCause2() {
    new ThrowableAssert(new Exception(new FileNotFoundException())).hasCause(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedCauseAsAncestor1() {
    new ThrowableAssert(new Exception(new IOException())).hasCauseAsAncestor(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedCauseAsAncestor2() {
    Exception e = new Exception(new RuntimeException(new IllegalStateException()));
    new ThrowableAssert(e).hasCauseAsAncestor(IllegalStateException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedCauseAsAncestor3() {
    Exception e = new Exception(new RuntimeException(new IllegalStateException()));
    new ThrowableAssert(e).hasCauseAsAncestor(RuntimeException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedCauseAsAncestor4() {
    Exception e = new Exception(new IllegalStateException(new FileNotFoundException()));
    new ThrowableAssert(e).hasCauseAsAncestor(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedExactCause() {
    new ThrowableAssert(new Exception(new IOException())).hasExactCause(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedExactCauseAsAncestor1() {
    new ThrowableAssert(new Exception(new IOException())).hasExactCauseAsAncestor(IOException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedExactCauseAsAncestor2() {
    Exception e = new Exception(new RuntimeException(new IllegalStateException()));
    new ThrowableAssert(e).hasExactCauseAsAncestor(IllegalStateException.class);
  }

  @Test public void shouldPassIfThrowableHasExpectedExactCauseAsAncestor3() {
    Exception e = new Exception(new RuntimeException(new IllegalStateException()));
    new ThrowableAssert(e).hasExactCauseAsAncestor(RuntimeException.class);
  }

  @Test public void shouldPassIfThrowableHasNoCause() {
    new ThrowableAssert(new Exception()).hasNoCause();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNotInstanceOfExpectedClass1() {
      new ThrowableAssert(new IllegalStateException()).isInstanceOf(IllegalArgumentException.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfThrowableIsNotInstanceOfExpectedClass2() {
      new ThrowableAssert(new Exception()).isInstanceOf(IllegalStateException.class);
  }

  @Test public void shouldPassIfThrowableIsInstanceOfExpectedClass() {
      new ThrowableAssert(new IllegalStateException()).isInstanceOf(Exception.class);
      new ThrowableAssert(new IllegalStateException()).isInstanceOf(Throwable.class);
  }
}
