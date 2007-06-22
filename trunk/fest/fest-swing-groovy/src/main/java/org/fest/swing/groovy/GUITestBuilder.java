/*
 * Created on Jun 22, 2007
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
package org.fest.swing.groovy;

import groovy.util.BuilderSupport;

import java.util.LinkedHashMap;
import java.util.Map;

import org.fest.swing.RobotFixture;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class GUITestBuilder extends BuilderSupport {

  final RobotFixture robot;

  private static final Map<Object, FixtureFactory> factoryMap = new LinkedHashMap<Object, FixtureFactory>();
  static {
    factoryMap.put("frame", new FrameFixtureFactory());
  }
  
  /** 
   * Creates a new </code>{@link GUITestBuilder}</code>. 
   * @param robot the robot to use with this builder.
   */
  public GUITestBuilder(RobotFixture robot) {
    this.robot = robot;
  }

  protected void setParent(Object parent, Object child) {
    
  }
  
  protected Object createNode(Object name) {
    return createNode(name, null);
  }

  protected Object createNode(Object name, Object value) {
    return createNode(name, null, value);
  }

  protected Object createNode(Object name, Map attributes) {
    return createNode(name, attributes, null);
  }

  @SuppressWarnings("unchecked") 
  protected Object createNode(Object name, Map attributes, Object value) {
    if (factoryMap.containsKey(name)) return factoryMap.get(name).newInstance(this, name, value, attributes);
    throw new IllegalArgumentException(concat(quote(name), " is not a valid node name"));
  }

    
}
