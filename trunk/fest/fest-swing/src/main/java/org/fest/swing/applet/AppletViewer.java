/*
 * Created on Jul 10, 2008
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
package org.fest.swing.applet;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import static java.awt.BorderLayout.*;
import static javax.swing.BorderFactory.*;
import static javax.swing.border.BevelBorder.LOWERED;

import static org.fest.util.Strings.concat;

/**
 * Understands a window that displays an <code>{@link Applet}</code>.
 *
 * @author Alex Ruiz
 */
public class AppletViewer extends JFrame implements StatusDisplay {

  private static final long serialVersionUID = 1L;

  private static final String APPLET_VIEWER_TITLE = "Applet Viewer: ";
  private static final String APPLET_LOADED_MESSAGE = "Applet loaded";
  private static final Dimension DEFAULT_SIZE = new Dimension(100, 100);

  private final JLabel statusLabel = new JLabel();
  
  /**
   * Creates a new </code>{@link AppletViewer}</code>. This constructor creates new instances of 
   * <code>{@link BasicAppletStub}</code> and <code>{@link BasicAppletContext}</code>.
   * @param applet the applet to view.
   */
  public AppletViewer(Applet applet) {
    load(applet, new BasicAppletStub(this, new BasicAppletContext(this)));
  }
  
  /**
   * Creates a new </code>{@link AppletViewer}</code>. This constructor creates new instances of 
   * <code>{@link BasicAppletStub}</code> and <code>{@link BasicAppletContext}</code>.
   * @param applet the applet to view.
   * @param parameters the parameters included in an applet HTML tag.
   */
  public AppletViewer(Applet applet, Map<String, String> parameters) {
    load(applet, new BasicAppletStub(this, new BasicAppletContext(this), parameters));
  }

  /**
   * Creates a new </code>{@link AppletViewer}</code>.
   * @param applet the applet to view.
   * @param stub the applet's stub.
   */
  public AppletViewer(Applet applet, AppletStub stub) {
    load(applet, stub);
  }

  private void load(Applet applet, AppletStub stub) {
    setUpFrame(applet);
    addContent(applet);
    setUpApplet(applet, stub);
  }

  private void setUpFrame(Applet applet) {
    setTitle(concat(APPLET_VIEWER_TITLE, applet.getClass().getName()));
    setSize(DEFAULT_SIZE);
    setLayout(new BorderLayout());
    addWindowListener(new StopAppletWindowListener(applet));
  }
  
  private void addContent(Applet applet) {
    add(applet, CENTER);
    statusLabel.setBorder(createCompoundBorder(createBevelBorder(LOWERED), createEmptyBorder(2, 5, 2, 5)));
    statusLabel.setName("status");
    add(statusLabel, SOUTH);
  }
  
  private void setUpApplet(Applet applet, AppletStub stub) {
    applet.setStub(stub);
    start(applet);
    showStatus(APPLET_LOADED_MESSAGE);
  }

  private void start(Applet applet) {
    applet.init();
    applet.start();
  }
  
  /**
   * Displays the given status message.
   * @param status the status to display.
   */
  public void showStatus(String status) {
    statusLabel.setText(status);
  }
}
