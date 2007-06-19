/*
 * Created on Jun 18, 2007
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
package org.fest.swing;

import java.awt.Component;

import abbot.finder.Matcher;

/**
 * Understands an indication whether a <code>{@link java.awt.Component}</code> matches some desired criteria.
 * 
 * @author Alex Ruiz
 */
public interface ComponentMatcher extends Matcher {
  
  /** @return whether the given <code>Component</code> matches some lookup criteria. */
  boolean matches(Component c);
}
