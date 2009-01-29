/*
 * Created on Jan 25, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.type;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public final class StaticInnerClass {

  private final String name;

  /**
   * Creates a new </code>{@link StaticInnerClass}</code>.
   * @param name
   */
  public StaticInnerClass(String name) {
    this.name = name;
  }

  /**
   * @param declaringClass 
   * @return
   */
  public StaticInnerClassLoader in(Class<?> declaringClass) {
    return new StaticInnerClassLoader(declaringClass, name);
  }
}
