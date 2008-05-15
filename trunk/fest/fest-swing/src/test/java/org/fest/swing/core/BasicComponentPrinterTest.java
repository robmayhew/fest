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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.testing.TestGroups.GUI;

import javax.swing.JButton;
import javax.swing.JComboBox;

import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.testing.PrintStreamStub;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BasicComponentPrinter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicComponentPrinterTest {

  protected static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A button");

    static MainWindow show(Class<?> testClass) {
      MainWindow window = new MainWindow(testClass);
      window.display();
      return window;
    }

    MainWindow(Class<?> testClass) {
      super(testClass);
      add(button);
    }
  }

  private ComponentPrinter printer;

  private MainWindow firstWindow;
  private MainWindow secondWindow;

  @BeforeMethod public void setUp() {
    printer = BasicComponentPrinter.printerWithNewAwtHierarchy();
    firstWindow = MainWindow.show(getClass());
    firstWindow.button.setName("firstButton");
    secondWindow = MainWindow.show(getClass());
    secondWindow.button.setName("secondButton");
  }

  @AfterMethod public void tearDown() {
    firstWindow.destroy();
    secondWindow.destroy();
  }

  @Test public void shouldCreatePrinterWithExistingHierarchy() {
    printer = BasicComponentPrinter.printerWithCurrentAwtHierarchy();
    assertThat(((BasicComponentPrinter)printer).hierarchy()).isInstanceOf(ExistingHierarchy.class);
  }

  @Test public void shouldPrintAllComponents() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out);
    assertThat(out.printed()).contains(format(firstWindow),
                                       format(firstWindow.button),
                                       format(secondWindow),
                                       format(secondWindow.button));
  }

  @Test public void shouldPrintAllComponentsOfGivenType() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JButton.class);
    assertThat(out.printed()).containsOnly(format(firstWindow.button),
                                           format(secondWindow.button));
  }

  @Test public void shouldNotPrintComponentsOfNonMatchingType() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JComboBox.class);
    assertThat(out.printed()).isEmpty();
  }

  @Test public void shouldPrintComponentsUnderGivenRootOnly() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, firstWindow);
    assertThat(out.printed()).contains(format(firstWindow),
                                       format(firstWindow.button))
                             .excludes(format(secondWindow),
                                       format(secondWindow.button));
  }

  @Test public void shouldPrintAllComponentsOfGivenTypeUnderGivenRootOnly() {
    PrintStreamStub out = new PrintStreamStub();
    printer.printComponents(out, JButton.class, firstWindow);
    assertThat(out.printed()).containsOnly(format(firstWindow.button));
  }
}
