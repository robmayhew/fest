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
 * Understands assertion methods.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Assertions {

  public static StringAssert assertThat(String actual) {
    return new StringAssert(actual);
  }
  
  public static ObjectAssert assertThat(Object actual) {
    return new ObjectAssert(actual);
  }
  
  public static BooleanAssert assertThat(boolean actual) {
    return new BooleanAssert(actual);
  }
  
  public static BooleanArrayAssert assertThat(boolean[] actual) {
    return new BooleanArrayAssert(actual);
  }

  public static CharAssert assertThat(char actual) {
    return new CharAssert(actual);
  }
  
  public static CharArrayAssert assertThat(char[] actual) {
    return new CharArrayAssert(actual);
  }

  public static ByteAssert assertThat(byte actual) {
    return new ByteAssert(actual);
  }
  public static ByteArrayAssert assertThat(byte[] actual) {
    return new ByteArrayAssert(actual);
  }

  public static ShortAssert assertThat(short actual) {
    return new ShortAssert(actual);
  }
  
  public static ShortArrayAssert assertThat(short[] actual) {
    return new ShortArrayAssert(actual);
  }

  public static IntAssert assertThat(int actual) {
    return new IntAssert(actual);
  }

  public static IntArrayAssert assertThat(int[] actual) {
    return new IntArrayAssert(actual);
  }

  public static LongAssert assertThat(long actual) {
    return new LongAssert(actual);
  }
  public static LongArrayAssert assertThat(long[] actual) {
    return new LongArrayAssert(actual);
  }
  
  public static FloatAssert assertThat(float actual) {
    return new FloatAssert(actual);
  }

  public static FloatArrayAssert assertThat(float[] actual) {
    return new FloatArrayAssert(actual);
  }

  public static DoubleAssert assertThat(double actual) {
    return new DoubleAssert(actual);
  }
  
  public static DoubleArrayAssert assertThat(double[] actual) {
    return new DoubleArrayAssert(actual);
  }

  public static ObjectArrayAssert assertThat(Object[] actual) {
    return new ObjectArrayAssert(actual);
  }

  public static CollectionAssert assertThat(Collection<?> actual) {
    return new CollectionAssert(actual);
  }
  
  public static ImageAssert assertThat(BufferedImage actual) {
    return new ImageAssert(actual);
  }
  
  private Assertions() {}
}
