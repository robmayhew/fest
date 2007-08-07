/*
 * Created on Jul 30, 2007
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
package org.fest.swing.fixture.util;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.fest.swing.GuiExecutor;

/**
 * Understands a <code>{@link JFrame}</code> that simulates a login window.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class LoginWindow extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final int DEFAULT_SLEEP_TIME = 10000;

  private long loginTime = DEFAULT_SLEEP_TIME;
  private long showSettingsTime = DEFAULT_SLEEP_TIME;
  
  public LoginWindow() {
    setLayout(new FlowLayout());
    add(loginButton());
    add(settingsButton());
  }

  private JButton loginButton() {
    JButton button = new JButton("Login");
    button.setName("login");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        setVisible(false);
        showWindow(new MainWindow(), loginTime);
      }
    });
    return button;
  }

  private JButton settingsButton() {
    JButton button = new JButton("Settings");
    button.setName("showSettings");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        setVisible(false);
        showWindow(new SettingsDialog(), showSettingsTime);
      }
    });
    return button;
  }
  
  private void showWindow(final Window window, final long delay) {
    GuiExecutor.instance().execute(new Runnable() {
      public void run() {
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        window.pack();
        window.setVisible(true);
      }
    });
//    Executor background = Executors.newCachedThreadPool();
//    background.execute(new Runnable() {
//      public void run() {
//      }
//    });
  }

  public void loginTime(long loginTime) {
    this.loginTime = loginTime;
  }

  public void showSettingsTime(long showSettingsTime) {
    this.showSettingsTime = showSettingsTime;
  }
}
