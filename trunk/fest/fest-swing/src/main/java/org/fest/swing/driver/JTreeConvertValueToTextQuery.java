/*
 * Created on Aug 22, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that converts a value to text for a particular cell in
 * a <code>{@link JTree}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTreeConvertValueToTextQuery extends GuiQuery<String> {
  
  private final JTree tree;
  private final Object modelValue;

  static String convertValueToText(JTree tree, Object modelValue) {
    return execute(new JTreeConvertValueToTextQuery(tree, modelValue));
  }
  
  JTreeConvertValueToTextQuery(JTree tree, Object modelValue) {
    this.tree = tree;
    this.modelValue = modelValue;
  }

  protected String executeInEDT() {
    return tree.convertValueToText(modelValue, false, false, false, 0, false);
  }
}