/*
 * Created on May 1, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.util;

/**
 * Understands hash codes.
 *
 * @author Alex Ruiz
 */
public final class HashCodes {
  
  /**
   * @return the integer <code>1231</code> if the given value is <code>true</code>; returns the integer
   *         <code>1237</code> if the given value is <code>false</code>.
   * @see Boolean#hashCode()        
   */
  public static int hashCodeForBoolean(boolean value) {
    return value ? 1231 : 1237;
  }
  
  public static int hashCodeForChar(char value) {
    return (int)value;
  }
  
  public static int hashCodeForByte(byte value) {
    return (int)value;
  }
  
  public static int hashCodeForShort(short value) {
    return (int)value;
  }
  
  public static int hashCodeForInt(int value) {
    return value;
  }
  
  /** @see Long#hashCode() */
  public static int hashCodeForLong(long value) {
    return (int)(value ^ (value >>> 32));  
  }
  
  public static int hashCodeForFloat(float value) {
    return new Float(value).hashCode();
  }
  
  public static int hashCodeForDouble(double value) {
    return new Double(value).hashCode();
  }
  
  public static int hashCodeFrom(Object o) {
    return o != null ? o.hashCode() : 0;
  }

  private HashCodes() {}
}
