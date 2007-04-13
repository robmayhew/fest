/*
 * Created on Feb 9, 2007
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
package com.jtuzi.fest.fixture;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jtuzi.fest.AbbotFixture;
import com.jtuzi.fest.fixture.JLabelFixture;

import static com.jtuzi.fest.assertions.Assertions.assertThat;


import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link JLabelFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JLabelFixtureTest {

  private static class CustomLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    private boolean wasClicked;
    
    CustomLabel(String text) {
      super(text);
      addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { wasClicked = true; }
      });      
    }
    
    boolean wasClicked() { return wasClicked; }
  }
  
  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JLabel firstLabel = new JLabel("First Label");
    final CustomLabel secondLabel = new CustomLabel("Second Label");
    
    MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }
    
    private void setUpComponents() {
      firstLabel.setName("firstLabel");
      secondLabel.setName("secondLabel");
    }
    
    private void addComponents() {
      add(firstLabel);
      add(secondLabel);
    }
  }
  
  private MainWindow window;
  private AbbotFixture abbot;
  private JLabelFixture fixtureForSecondLabel;
  
  @BeforeClass public void setUp() {
    abbot = new AbbotFixture();
    window = new MainWindow();
    abbot.showWindow(window);
    fixtureForSecondLabel = new JLabelFixture(abbot, "secondLabel");
  }
  
  @Test public void shouldHaveFoundLabel() {
    assertThat(fixtureForSecondLabel.target).isSameAs(window.secondLabel);
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldClickLabel() {
    fixtureForSecondLabel.click();
    assertTrue(window.secondLabel.wasClicked());
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldGiveFocusToLabel() {
    fixtureForSecondLabel.focus();
    assertTrue(window.secondLabel.hasFocus());
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldPassIfLabelHasMatchingText() {
    fixtureForSecondLabel.shouldHaveThisText("Second Label");
  }
  
  @Test(dependsOnMethods = {"shouldHaveFoundLabel", "shouldPassIfLabelHasMatchingText"},
        expectedExceptions = AssertionError.class) 
  public void shouldFailIfLabelHasNotMatchingText() {
    fixtureForSecondLabel.shouldHaveThisText("A Label");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldReturnLabelText() {
    assertThat(fixtureForSecondLabel.text()).isEqualTo("Second Label");
  }
  
  @AfterClass public void tearDown() {
    abbot.cleanUp();
  }

}
