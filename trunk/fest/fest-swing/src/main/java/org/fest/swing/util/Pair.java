/*
 * Created on Jul 29, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.util;

/**
 * Understands a pair of values.
 * @param <O> the generic type of the 1st. value in this pair.
 * @param <T> the generic type of the 2nd. value in this pair.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Pair<O, T> {

  /** The first value in this pair. */
  public final O one;

  /** The second value in this pair. */
  public final T two;

  /**
   * Creates a new </code>{@link Pair}</code>.
   * @param one the 1st. value in this pair.
   * @param two the 2nd. value in this pair.
   */
  public Pair(O one, T two) {
    this.one = one;
    this.two = two;
  }
}
