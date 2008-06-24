/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JFileChooserFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserFormatterTest {

  private JFileChooser fileChooser;
  private JFileChooserFormatter formatter;
  
  @BeforeClass public void setUp() {
    fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("A file chooser");
    fileChooser.setName("fileChooser");
    fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    formatter = new JFileChooserFormatter();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJFileChooser() {
    formatter.format(new JTextField());
  }

  @Test public void shouldFormatJFileChooser() {
    String formatted = formatter.format(fileChooser);
    assertThat(formatted).contains(fileChooser.getClass().getName())
                         .contains("name='fileChooser'")
                         .contains("dialogTitle='A file chooser'")
                         .contains("dialogType=OPEN_DIALOG")
                         .contains(concat("currentDirectory=", fileChooser.getCurrentDirectory()))
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
  
}
