/*
 * Created on Dec 22, 2007
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
package org.fest.swing.format;

import java.util.logging.Logger;

import javax.swing.JButton;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link IntrospectorComponentFormatter}</code>.
 *
 * @author Alex Ruiz
 */
public class IntrospectorComponentFormatterTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  private JButton button;
  private IntrospectorComponentFormatter formatter;
  
  @BeforeMethod public void setUp() {
    button = new JButton("Click Me");
    button.setName("button");
    formatter = new IntrospectorComponentFormatter(JButton.class, "name", "text");
    logger.info(formatter.toString());
  }
  
  @Test public void shouldFormatComponent() {
    String formatted = formatter.format(button);
    String expected = 
      concat(button.getClass().getName(), "[name='button', text='Click Me']");
    assertThat(formatted).isEqualTo(expected);
  }
}
