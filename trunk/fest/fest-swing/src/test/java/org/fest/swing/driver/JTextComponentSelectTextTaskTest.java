/*
 * Created on Aug 11, 2008
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

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTextComponentSelectTextTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI) 
public class JTextComponentSelectTextTaskTest {

  static final String TEXTBOX_TEXT = "Hello World";

  private MyWindow window;
  private JTextComponent textBox;

  @BeforeMethod public void setUp() {
    window = MyWindow.newWindow();
    textBox = window.textBox;
    window.display();
  }
  
  @Test(dataProvider = "selectionIndices", groups = GUI)
  public void shouldSelectText(int start, int end) {
    JTextComponentSelectTextTask.selectTextIn(textBox, start, end);
    String selection = selectedTextOf(textBox);
    assertThat(selection).isEqualTo(TEXTBOX_TEXT.substring(start, end));
  }
  
  @DataProvider(name = "selectionIndices") public Object[][] selectionIndices() {
    return new Object[][] {
        { 0, 5 },
        { 1, 9 },
        { 6, 8 }
    };
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textBox = new JTextField(20);
    
    static MyWindow newWindow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
    }
    
    MyWindow() {
      super(JTextComponentSelectTextTaskTest.class);
      setPreferredSize(new Dimension(80, 60));
      add(textBox);
      textBox.setText(TEXTBOX_TEXT);
    }
  }
}
