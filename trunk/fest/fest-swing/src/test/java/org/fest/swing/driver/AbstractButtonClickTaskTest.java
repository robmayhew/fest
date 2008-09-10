/*
 * Created on Jun 22, 2008
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

import javax.swing.JButton;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link AbstractButtonClickTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test 
public class AbstractButtonClickTaskTest {

  private MyButton button;

  @BeforeClass public void setUp() {
    button = MyButton.createNew("Hello");
  }

  public void shouldClickButton() {
    AbstractButtonClickTask.doClick(button);
    assertThat(button.clicked()).isTrue();
  }

  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean clicked;
    
    static MyButton createNew(String text) {
      return new MyButton(text);
    }
    
    private MyButton(String text) {
      super(text);
    }

    @Override public void doClick() {
      clicked = true;
      super.doClick();
    }
    
    boolean clicked() { return clicked; }
  }
}
