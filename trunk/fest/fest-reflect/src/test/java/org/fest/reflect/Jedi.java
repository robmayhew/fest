/*
 * Created on May 18, 2007
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
package org.fest.reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Understands a Jedi.
 *
 * @author Alex Ruiz
 */
public class Jedi extends Person {

  private static final List<String> COMMON_POWERS = new ArrayList<String>();

  private final List<String> powers = new ArrayList<String>();
  private boolean master;

  public Jedi(String name) {
    super(name);
  }

  public void addPower(String power) {
    powers.add(power);
  }

  public String powerAt(int index) {
    return powers.get(index);
  }

  public int powerCount() {
    return powers.size();
  }

  public void clearPowers() {
    powers.clear();
  }

  public void makeMaster() {
    master = true;
  }

  public boolean isMaster() { return master; }

  public static void addCommonPower(String power) {
    COMMON_POWERS.add(power);
  }

  public static String commonPowerAt(int index) {
    return COMMON_POWERS.get(index);
  }

  public static int commonPowerCount() {
    return COMMON_POWERS.size();
  }

  public static void clearCommonPowers() {
    COMMON_POWERS.clear();
  }
}