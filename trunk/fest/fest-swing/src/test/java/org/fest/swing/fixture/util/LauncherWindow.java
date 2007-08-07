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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Understands a <code>{@link JFrame}</code> that simulates a login window.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class LauncherWindow extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final int DEFAULT_DELAY = 10000;

  private int frameLaunchDelay = DEFAULT_DELAY;
  private int dialogLaunchDelay = DEFAULT_DELAY;
  
  public LauncherWindow() {
    setLayout(new FlowLayout());
    add(frameLaunchButton());
    add(dialogLaunchButton());
  }

  private JButton frameLaunchButton() {
    JButton button = new JButton("Launch Frame");
    button.setName("launchFrame");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        setVisible(false);
        launchFrame();
      }
    });
    return button;
  }
  
  private void launchFrame() {
    new Timer(frameLaunchDelay, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showWindow(new FrameToLaunch());
      }
    }).start();
  }

  private JButton dialogLaunchButton() {
    JButton button = new JButton("Launch Dialog");
    button.setName("launchDialog");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        setVisible(false);
        launchDialog();
      }
    });
    return button;
  }
  
  private void launchDialog() {
    new Timer(dialogLaunchDelay, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showWindow(new DialogToLaunch());
      }
    }).start();
  }

  private void showWindow(Window window) {
    window.pack();
    window.setVisible(true);
  }
  
  public void frameLaunchDelay(int frameLaunchDelay) {
    this.frameLaunchDelay = frameLaunchDelay;
  }

  public void dialogLaunchDelay(int dialogLaunchDelay) {
    this.dialogLaunchDelay = dialogLaunchDelay;
  }
  
  public static class FrameToLaunch extends JFrame {
    private static final long serialVersionUID = 1L;

    public FrameToLaunch() {
      setName("frame");
    }
  }
  
  public static class DialogToLaunch extends JDialog {
    private static final long serialVersionUID = 1L;

    public DialogToLaunch() {
      setName("dialog");
    }
  }
}
