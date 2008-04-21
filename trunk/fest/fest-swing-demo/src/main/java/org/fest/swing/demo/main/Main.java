/*
 * Created on Mar 3, 2008
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
package org.fest.swing.demo.main;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.lafwidget.animation.FadeConfigurationManager;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;

import org.fest.swing.demo.service.FolderServiceStub;
import org.fest.swing.demo.service.Services;
import org.fest.swing.demo.service.WebFeedServiceStub;
import org.fest.swing.demo.view.MainFrame;

import static javax.swing.SwingUtilities.invokeLater;
import static org.jvnet.substance.SubstanceLookAndFeel.TREE_DECORATIONS_ANIMATION_KIND;

/**
 * Launches the application.
 *
 * @author Alex Ruiz
 */
public class Main {

  /**
   * Starts the main application.
   * @param args any command line arguments.
   * @throws Exception any error that may occur while setting the look and feel of the application.
   */
  public static void main(String[] args) throws Exception {
    updateLookAndFeel();
    Services.instance().updateFolderService(new FolderServiceStub());
    Services.instance().updateWebFeedService(new WebFeedServiceStub());
    invokeLater(new Runnable() {
      public void run() {
        new MainFrame().setVisible(true);
      }
    });
  }

  public static void updateLookAndFeel() throws Exception {
    FadeConfigurationManager.getInstance().allowFades(TREE_DECORATIONS_ANIMATION_KIND);
    UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel());
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
  }
}
