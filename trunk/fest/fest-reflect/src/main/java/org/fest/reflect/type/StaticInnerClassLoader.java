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

import static org.fest.util.Strings.concat;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class StaticInnerClassLoader {

  private final Class<?> declaringClass;
  private final String innerClassName;

  /**
   * Creates a new </code>{@link StaticInnerClassLoader}</code>.
   * @param declaringClass
   * @param innerClassName
   */
  public StaticInnerClassLoader(Class<?> declaringClass, String innerClassName) {
    this.declaringClass = declaringClass;
    this.innerClassName = innerClassName;
  }

  /**
   * @return
   */
  public Class<?> load() {
    String declaringClassName = declaringClass.getName();
    for (Class<?> innerClass : declaringClass.getDeclaredClasses())
      if (innerClass.getName().equals(concat(declaringClassName, "$", innerClassName))) return innerClass;
    return null;
  }

}
