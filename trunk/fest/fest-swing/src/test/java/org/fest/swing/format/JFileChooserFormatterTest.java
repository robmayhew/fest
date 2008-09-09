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

import java.io.File;

import javax.swing.JFileChooser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JFileChooser.OPEN_DIALOG;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JFileChooserFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JFileChooserFormatterTest {

  private MyWindow window;
  private JFileChooser fileChooser;
  private JFileChooserFormatter formatter;
  
  @BeforeClass public void setUp() {
    window = MyWindow.createAndShow();
    fileChooser = window.fileChooser;
    formatter = new JFileChooserFormatter();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJFileChooser() {
    formatter.format(textField().createNew());
  }

  public void shouldFormatJFileChooser() {
    String formatted = formatter.format(fileChooser);
    final JFileChooser fileChooser2 = fileChooser;
    assertThat(formatted).contains(fileChooser.getClass().getName())
                         .contains("name='fileChooser'")
                         .contains("dialogTitle='A file chooser'")
                         .contains("dialogType=OPEN_DIALOG")
                         .contains(concat("currentDirectory=", currentDirectoryOf(fileChooser2)))
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  private static File currentDirectoryOf(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getCurrentDirectory();
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    
    static MyWindow createAndShow() {
      MyWindow window = new MyWindow();
      window.display();
      return window;
    }
    
    final JFileChooser fileChooser = new JFileChooser();

    private MyWindow() {
      super(JFileChooserFormatterTest.class);
      fileChooser.setDialogTitle("A file chooser");
      fileChooser.setName("fileChooser");
      fileChooser.setDialogType(OPEN_DIALOG);
      addComponents(fileChooser);
    }
  }
}
