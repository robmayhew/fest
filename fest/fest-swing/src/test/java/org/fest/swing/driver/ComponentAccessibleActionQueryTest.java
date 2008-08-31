/*
 * Created on Feb 23, 2008
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

import java.util.Locale;

import javax.accessibility.*;
import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ComponentAccessibleActionQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentAccessibleActionQueryTest {

  private MyWindow window;
  private AccessibleAction action;

  @BeforeMethod public void setUp() {
    action = createMock(AccessibleAction.class);
    window = MyWindow.createAndShowInEDT(action);
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  public void shouldFindAccessibleAction() {
    AccessibleAction actualAction = ComponentAccessibleActionQuery.accessibleActionFrom(window.button);
    assertThat(actualAction).isSameAs(action);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createAndShowInEDT(final AccessibleAction action) {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(action); }
      });
      window.display();
      return window;
    }
    
    final MyButton button = new MyButton("Hello");

    MyWindow(AccessibleAction action) {
      super(ComponentAccessibleActionQueryTest.class);
      AccessibleContext context = new MyAccessibleContext(action);
      button.accessibleContext(context);
      add(button);
    }
  }

  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private AccessibleContext accessibleContext;

    public MyButton(String text) {
      super(text);
    }

    void accessibleContext(AccessibleContext newAccessibleContext) {
      accessibleContext = newAccessibleContext;
    }

    @Override public AccessibleContext getAccessibleContext() {
      return accessibleContext;
    }
  }

  private static class MyAccessibleContext extends AccessibleContext {
    private final AccessibleAction accessibleAction;

    MyAccessibleContext(AccessibleAction accessibleAction) {
      this.accessibleAction = accessibleAction;
    }

    @Override public AccessibleAction getAccessibleAction() {
      return accessibleAction;
    }

    public Accessible getAccessibleChild(int i) { return null; }
    public int getAccessibleChildrenCount() { return 0; }
    public int getAccessibleIndexInParent() { return 0; }
    public AccessibleRole getAccessibleRole() { return null; }
    public AccessibleStateSet getAccessibleStateSet() { return null; }
    public Locale getLocale() { return null; }
  }
}
