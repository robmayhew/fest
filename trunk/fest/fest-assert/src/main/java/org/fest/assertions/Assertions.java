/*
 * Created on Dec 27, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.assertions;

import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * Understands an entry point for assertion methods for different data types. Each method in this class is a static 
 * factory for the type-specific assertion objects. The purpose of this class is to make test code more readable. 
 * <p>
 * For example:
 * <pre>
 * int removed = employees.removeFired();
 * {@link org.fest.assertions.Assertions#assertThat(int) assertThat}(removed).{@link org.fest.assertions.IntAssert#isZero isZero}();
 * 
 * List&lt;Employee&gt; newEmployees = employees.hired(TODAY);
 * {@link org.fest.assertions.Assertions#assertThat(java.util.Collection) assertThat}(newEmployees).{@link org.fest.assertions.CollectionAssert#hasSize(int) hasSize}(6);
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Assertions {

  /**
   * Creates a new instance of <code>{@link StringAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static StringAssert assertThat(String actual) {
    return new StringAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link ObjectAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ObjectAssert assertThat(Object actual) {
    return new ObjectAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link BooleanAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static BooleanAssert assertThat(boolean actual) {
    return new BooleanAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link BooleanArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static BooleanArrayAssert assertThat(boolean[] actual) {
    return new BooleanArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link CharAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CharAssert assertThat(char actual) {
    return new CharAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link CharArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CharArrayAssert assertThat(char[] actual) {
    return new CharArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link ByteAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ByteAssert assertThat(byte actual) {
    return new ByteAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link ByteArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ByteArrayAssert assertThat(byte[] actual) {
    return new ByteArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link ShortAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ShortAssert assertThat(short actual) {
    return new ShortAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link ShortArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ShortArrayAssert assertThat(short[] actual) {
    return new ShortArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link IntAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static IntAssert assertThat(int actual) {
    return new IntAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link IntArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static IntArrayAssert assertThat(int[] actual) {
    return new IntArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link LongAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static LongAssert assertThat(long actual) {
    return new LongAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link LongArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static LongArrayAssert assertThat(long[] actual) {
    return new LongArrayAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link FloatAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static FloatAssert assertThat(float actual) {
    return new FloatAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link FloatArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static FloatArrayAssert assertThat(float[] actual) {
    return new FloatArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link DoubleAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static DoubleAssert assertThat(double actual) {
    return new DoubleAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link DoubleArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static DoubleArrayAssert assertThat(double[] actual) {
    return new DoubleArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link ObjectArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ObjectArrayAssert assertThat(Object[] actual) {
    return new ObjectArrayAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link CollectionAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CollectionAssert assertThat(Collection<?> actual) {
    return new CollectionAssert(actual);
  }
  
  /**
   * Creates a new instance of <code>{@link ImageAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ImageAssert assertThat(BufferedImage actual) {
    return new ImageAssert(actual);
  }
  
  private Assertions() {}
}
