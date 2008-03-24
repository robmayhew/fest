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
package org.fest.swing.demo.view;

import static javax.swing.SwingUtilities.invokeLater;
import static org.jvnet.substance.SubstanceLookAndFeel.TREE_DECORATIONS_ANIMATION_KIND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.fest.swing.demo.model.Folder;
import org.fest.swing.demo.model.WebFeed;
import org.fest.swing.demo.service.FolderService;
import org.fest.swing.demo.service.Services;
import org.fest.swing.demo.service.WebFeedService;
import org.jvnet.lafwidget.animation.FadeConfigurationManager;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;

/**
 * Launches the application.
 *
 * @author Alex Ruiz
 */
public class Main {

  /**
   * Starts the main application.
   * @param args any command line arguments.
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    FadeConfigurationManager.getInstance().allowFades(TREE_DECORATIONS_ANIMATION_KIND);
    UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel());
    makeWindowDecorationsUseLookAndFeel();
    Services.instance().updateFolderService(new FolderService() {
      private final List<Folder> folders = new ArrayList<Folder>();

      public void saveFolder(Folder folder) {
        folders.add(folder);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      public Folder[] allFolders() {
        Folder[] sortedFolders = folders.toArray(new Folder[folders.size()]);
        Arrays.sort(sortedFolders);
        return sortedFolders;
      }

    });
    invokeLater(new Runnable() {
      public void run() {
        new MainFrame().setVisible(true);
      }
    });
    Services.instance().updateWebFeedService(new WebFeedService() {
      @Override public String obtainFeedName(String address) {
        return null;
      }

      @Override public void saveWebFeed(WebFeed webFeed) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }

  private static void makeWindowDecorationsUseLookAndFeel() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
  }
}
