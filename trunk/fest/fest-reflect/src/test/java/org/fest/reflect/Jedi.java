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

  private List<String> powers = new ArrayList<String>();
  private boolean master;
  
  public Jedi(String name) {
    super(name);
  }
  
  public boolean addPower(String power) {
    return powers.add(power);
  }
  
  public int powerCount() {
    return powers.size();
  }
  
  public String powerAt(int index) {
    return powers.get(index);
  }
  
  public void makeMaster() {
    master = true;
  }
  
  public boolean isMaster() { return master; }
}