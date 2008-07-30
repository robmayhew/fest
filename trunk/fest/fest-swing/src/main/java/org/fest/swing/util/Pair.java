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
 * @param <F> the generic type of the first value in this pair.
 * @param <S> the generic type of the second value in this pair.
 *
 * @author Yvonne Wang
 *
 */
public class Pair<F, S> {

  /** The first value in this pair. */
  public final F first;

  /** The second value in this pair. */
  public final S second;

  /**
   * Creates a new </code>{@link Pair}</code>.
   * @param first the first value in this pair.
   * @param second the second value in this pair.
   */
  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

}
