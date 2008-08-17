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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.core;

import javax.swing.JButton;
import javax.swing.JComboBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.testing.PrintStreamStub;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicComponentPrinter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicComponentPrinterTest {

  private BasicComponentPrinter printer;

  private MyWindow window1;
  private MyWindow window2;

  @BeforeMethod public void setUp() {
    printer = (BasicComponentPrinter)BasicComponentPrinter.printerWithNewAwtHierarchy();
    window1 = MyWindow.showNew();
    window1.button.setName("button1");
    window2 = MyWindow.showNew();
    window2.button.setName("button2");
  }

  @AfterMethod public void tearDown() {
    window1.destroy();
    window2.destroy();
  }

  public void shouldCreatePrinterWithExistingHierarchy() {
    printer = (BasicComponentPrinter)BasicComponentPrinter.printerWithCurrentAwtHierarchy();
    assertThat(printer.hierarchy()).isInstanceOf(ExistingHierarchy.class);
  }

  public void shouldPrintAllComponents() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out);
    assertThat(out.printed()).contains(format(window1),
                                       format(window1.button),
                                       format(window2),
                                       format(window2.button));
  }

  public void shouldPrintAllComponentsOfGivenType() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JButton.class);
    assertThat(out.printed()).containsOnly(format(window1.button),
                                           format(window2.button));
  }

  public void shouldNotPrintComponentsOfNonMatchingType() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JComboBox.class);
    assertThat(out.printed()).isEmpty();
  }

  public void shouldPrintComponentsUnderGivenRootOnly() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, window1);
    assertThat(out.printed()).contains(format(window1),
                                       format(window1.button))
                             .excludes(format(window2),
                                       format(window2.button));
  }

  public void shouldPrintAllComponentsOfGivenTypeUnderGivenRootOnly() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JButton.class, window1);
    assertThat(out.printed()).containsOnly(format(window1.button));
  }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A button");

    static MyWindow showNew() {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
      window.display();
      return window;
    }

    MyWindow() {
      super(BasicComponentPrinterTest.class);
      addComponents(button);
    }
  }
}
