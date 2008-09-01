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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=76">Bug 76</a>.
 * 
 * @author Wim Deblauwe
 */
@Test(groups = { GUI, BUG })
public class JOptionPaneTest {

  private DialogFixture m_window;

  public void shouldFindOptionPane() throws InterruptedException {
    JOptionPaneStarter optionPaneStarter = JOptionPaneStarter.createNew(null, "Message 1");
    m_window = new DialogFixture(optionPaneStarter);
    m_window.show();
    m_window.requireVisible();
    m_window.button().click();

    Thread.sleep(1000);

    JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(m_window.robot);
    fixture.requireMessage("Message 1");
    fixture.button().click();
  }

  public void shouldFindOptionPaneAgain() throws InterruptedException {
    JOptionPaneStarter optionPaneStarter = JOptionPaneStarter.createNew(null, "Message 2");
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
  
  private static class JOptionPaneStarter extends JDialog {

    private static final long serialVersionUID = 1L;

    static JOptionPaneStarter createNew(final Frame owner, final String message) {
      return execute(new GuiQuery<JOptionPaneStarter>() {
        protected JOptionPaneStarter executeInEDT() {
          return new JOptionPaneStarter(owner, message);
        }
      });
    }
    
    JOptionPaneStarter(Frame owner, String message) {
      super(owner, "JOptionPane Starter");
      setContentPane(createContentPane(message));
    }

    private Container createContentPane(String message) {
      JPanel panel = new JPanel();
      panel.add(new JButton(new OpenJOptionPaneAction(message)));
      return panel;
    }

    private class OpenJOptionPaneAction extends AbstractAction {
      private static final long serialVersionUID = 1L;
      
      private final String m_message;

      OpenJOptionPaneAction(String message) {
        super("Start!");
        m_message = message;
      }

      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(JOptionPaneStarter.this, m_message);
      }
    }
  }
}