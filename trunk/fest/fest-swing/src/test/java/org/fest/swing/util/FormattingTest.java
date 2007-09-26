/*
 * Created on Sep 16, 2007
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
package org.fest.swing.util;

import java.util.logging.Logger;

import javax.swing.JButton;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FormattingTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  private TestFrame frame;
  private JButton button;
  
  @BeforeMethod public void setUp() {
    frame = new TestFrame(getClass());
    button = new JButton();
    frame.add(button);
    frame.pack();
    frame.setVisible(true);
  }
  
  @AfterMethod public void tearDown() {
    frame.setVisible(false);
    frame.dispose();
  }
  
  @Test public void shouldReturnComponentTypeAndNameIfNameAvailable() {
    button.setName("a button");
    String formatted = Formatting.format(button);
    logger.info(concat("formatted: ", formatted));
    assertThat(formatted).isEqualTo("javax.swing.JButton<'a button'>");
  }
  
  @Test public void shouldReturnToStringOfComponentIfNameNotAvailable() {
    button.setName(null);
    String formatted = Formatting.format(button);
    logger.info(concat("formatted: ", formatted));
    assertThat(formatted).isEqualTo(button.toString());
  }
  
  @Test public void shouldReturnComponentIsNullIfComponentIsNull() {
    assertThat(Formatting.format(null)).isEqualTo("Null Component");
  }
}
