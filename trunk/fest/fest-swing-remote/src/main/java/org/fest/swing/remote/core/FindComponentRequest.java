/*
 * Created on Dec 6, 2007
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
package org.fest.swing.remote.core;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class FindComponentRequest implements Request {

  private static final long serialVersionUID = 1L;
  
  private final String name;

  /**
   * Creates a request to find a GUI component by name.
   * @param name the name of the component we are looking for.
   * @return the created request.
   */
  public static FindComponentRequest findByName(String name) {
    return new FindComponentRequest(name);
  }
  
  private FindComponentRequest(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the component to look for.
   * @return the name of the component to look for.
   */
  public String name() { return name; }
}
