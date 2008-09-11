/*
 * Created on Aug 8, 2008
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

import javax.swing.JComboBox;
import javax.swing.JList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.BasicComponentFinder.finderWithCurrentAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxSetPopupVisibleTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION }) 
public class JComboBoxSetPopupVisibleTaskTest {

  private ComponentFinder finder;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    finder = finderWithCurrentAwtHierarchy();
    window = MyWindow.createNew();
    window.display();
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }

  public void shouldSetPopupVisibleAndInvisible() {
    setPopupVisible(true);
    JComboBoxSetPopupVisibleTask.setPopupVisible(window.comboBox, true);
    assertThat(comboBoxPopup().isShowing()).isTrue();
    setPopupVisible(false);
    try {
      comboBoxPopup();
      fail("Expecting JList not found!");
    } catch (ComponentLookupException expected) {}
  }

  private void setPopupVisible(final boolean visible) {
    JComboBoxSetPopupVisibleTask.setPopupVisible(window.comboBox, visible);
  }
  
  private JList comboBoxPopup() {
    return finder.findByType(window, JList.class);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final JComboBox comboBox = new JComboBox(array("One", "Two"));
    
    private MyWindow() {
      super(JComboBoxSetPopupVisibleTaskTest.class);
      addComponents(comboBox);
    }
  }
}
