/*
 * Created on Dec 21, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture.bug76;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JOptionPaneFixture;

import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=76">Bug 76</a>.
 * 
 * @author Wim Deblauwe
 */
@Test(groups = GUI)
public class JOptionPaneTest {

  private DialogFixture m_window;

  @Test public void shouldFindOptionPane() throws InterruptedException {
    JOptionPaneStarter optionPaneStarter = new JOptionPaneStarter(null, "Message 1");
    m_window = new DialogFixture(optionPaneStarter);
    m_window.show();
    m_window.requireVisible();
    m_window.button().click();

    Thread.sleep(1000);

    JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(m_window.robot);
    fixture.requireMessage("Message 1");
    fixture.button().click();
  }

  @Test public void shouldFindOptionPaneAgain() throws InterruptedException {
    JOptionPaneStarter optionPaneStarter = new JOptionPaneStarter(null, "Message 2");
    m_window = new DialogFixture(optionPaneStarter);
    m_window.show();
    m_window.requireVisible();
    m_window.button().click();

    Thread.sleep(1000);

    JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(m_window.robot);
    fixture.requireMessage("Message 2");
    fixture.button().click();
  }

  @AfterMethod public void stopGui() {
    m_window.cleanUp();
  }
}
