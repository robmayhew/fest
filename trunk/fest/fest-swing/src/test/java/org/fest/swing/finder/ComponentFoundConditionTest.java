/*
 * Created on Nov 15, 2007
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
package org.fest.swing.finder;

import java.awt.Component;

import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.core.ComponentFinder.finderWithNewAwtHierarchy;
import static org.fest.swing.testing.TestFrame.showInTest;

import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.testing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link org.fest.swing.finder.ComponentFoundCondition}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ComponentFoundConditionTest {

  private TypeMatcher matcher;
  private TestFrame toFind;

  private ComponentFoundCondition condition;

  @BeforeMethod public void setUp() {
    matcher = new TypeMatcher();
    condition = new ComponentFoundCondition("",  finderWithNewAwtHierarchy(), matcher);
    toFind = showInTest(getClass());
  }

  @AfterMethod public void tearDown() {
    toFind.destroy();
  }

  @Test public void shouldReturnTrueInTestAndReferenceFoundComponent() {
    matcher.typeToMatch(TestFrame.class);
    assertThat(condition.test()).isTrue();
    assertThat(condition.found()).isSameAs(toFind);
  }

  @Test public void shouldReturnFalseIfComponentNotFound() {
    matcher.typeToMatch(JTextField.class);
    assertThat(condition.test()).isFalse();
    assertThat(condition.found()).isNull();
  }

  private static class TypeMatcher implements ComponentMatcher {
    private Class<? extends Component> type;

    void typeToMatch(Class<? extends Component> type) { this.type = type; }

    public boolean matches(Component c) {
      return c != null && type.isAssignableFrom(c.getClass());
    }
  }
}
