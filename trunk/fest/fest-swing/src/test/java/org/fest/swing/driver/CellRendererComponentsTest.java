/*
 * Created on Feb 24, 2008
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

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link CellRendererComponents}</code>.
 *
 * @author Alex Ruiz
 */
public class CellRendererComponentsTest {

  @Test public void shouldReturnNullIfRendererIsNotJLabel() {
    String text = CellRendererComponents.textFrom(new JTextField());
    assertThat(text).isNull();
  }
  
  @Test public void shouldReturnTextFromJLabel() {
    String text = CellRendererComponents.textFrom(new JLabel("Hello"));
    assertThat(text).isEqualTo("Hello");
  }

  @Test public void shouldReturnTrimmedTextFromJLabel() {
    String text = CellRendererComponents.textFrom(new JLabel("   Hello   "));
    assertThat(text).isEqualTo("Hello");
  }
}
