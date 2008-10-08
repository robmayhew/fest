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

  private MyWindow windowOne;
  private MyWindow windowTwo;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    printer = (BasicComponentPrinter)BasicComponentPrinter.printerWithNewAwtHierarchy();
    windowOne = MyWindow.createAndShow();
    windowOne.buttonName("button1");
    windowTwo = MyWindow.createAndShow();
    windowTwo.buttonName("button2");
  }

  @AfterMethod public void tearDown() {
    windowOne.destroy();
    windowTwo.destroy();
    ScreenLock.instance().release(this);
  }

  public void shouldCreatePrinterWithExistingHierarchy() {
    printer = (BasicComponentPrinter)BasicComponentPrinter.printerWithCurrentAwtHierarchy();
    assertThat(printer.hierarchy()).isInstanceOf(ExistingHierarchy.class);
  }

  public void shouldPrintAllComponents() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out);
    assertThat(out.printed()).contains(format(windowOne),
                                       format(windowOne.button),
                                       format(windowTwo),
                                       format(windowTwo.button));
  }

  public void shouldPrintAllComponentsOfGivenType() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JButton.class);
    assertThat(out.printed()).containsOnly(format(windowOne.button),
                                           format(windowTwo.button));
  }

  public void shouldNotPrintComponentsOfNonMatchingType() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JComboBox.class);
    assertThat(out.printed()).isEmpty();
  }

  public void shouldPrintComponentsUnderGivenRootOnly() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, windowOne);
    assertThat(out.printed()).contains(format(windowOne),
                                       format(windowOne.button))
                             .excludes(format(windowTwo),
                                       format(windowTwo.button));
  }

  public void shouldPrintAllComponentsOfGivenTypeUnderGivenRootOnly() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JButton.class, windowOne);
    assertThat(out.printed()).containsOnly(format(windowOne.button));
  }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A button");

    static MyWindow createAndShow() {
      MyWindow window = new MyWindow();
      window.display();
      return window;
    }

    private MyWindow() {
      super(BasicComponentPrinterTest.class);
      addComponents(button);
    }

    void buttonName(final String buttonName) {
      execute(new GuiTask() {
        protected void executeInEDT() {
          button.setName(buttonName);
        }
      });
    }
  }
}
