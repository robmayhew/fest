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
import java.awt.Container;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.MyApplet;

import static javax.swing.SwingUtilities.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.query.FrameTitleQuery.titleOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link AppletViewer}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class AppletViewerGuiTest {

  private MyApplet applet;
  private FrameFixture fixture;
  private AppletViewer viewer;

  @BeforeMethod public void setUp() {
    applet = newMyApplet();
    viewer = newAppletViewer(applet);
    fixture = new FrameFixture(viewer);
    fixture.show();
    assertThatAppletIsInitializedAndStarted();
  }

  private static MyApplet newMyApplet() {
    return new MyApplet();
  }

  private static AppletViewer newAppletViewer(final Applet applet) {
    return new AppletViewer(applet);
  }

  private void assertThatAppletIsInitializedAndStarted() {
    assertThat(isInitialized(applet)).isTrue();
    assertThat(isStarted(applet)).isTrue();
  }

  private static boolean isInitialized(final MyApplet applet) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.initialized();
      }
    });
  }

  private static boolean isStarted(final MyApplet applet) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.started();
      }
    });
  }

  @AfterMethod public void tearDown() {
    unloadAppletIn(viewer);
    assertThatApplietIsStoppedAndDestroyed();
    fixture.cleanUp();
  }

  private static void unloadAppletIn(final AppletViewer viewer) {
    invokeLater(new Runnable() {
      public void run() {
        viewer.unloadApplet();
      }
    });
    pause(new Condition("applet is unloaded") {
      public boolean test() {
        return !viewer.appletLoaded();
      }
    });
  }

  private void assertThatApplietIsStoppedAndDestroyed() {
    assertThat(isStopped(applet)).isTrue();
    assertThat(isDestroyed(applet)).isTrue();
  }

  private static boolean isStopped(final MyApplet applet) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.stopped();
      }
    });
  }

  private static boolean isDestroyed(final MyApplet applet) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.destroyed();
      }
    });
  }

  public void shouldLoadApplet() {
    assertThatAppletIsShowing();
    assertThatAppletIsLoaded();
    assertThatAppletViewerHasCorrectTitle();
    Container ancestor = getAncestorOfClass(AppletViewer.class, applet);
    assertThat(ancestor).isSameAs(viewer);
    fixture.label("status").requireText("Applet loaded");
    assertThat(viewer.applet()).isSameAs(applet);
    assertThat(viewer.stub()).isInstanceOf(BasicAppletStub.class);
  }

  private void assertThatAppletViewerHasCorrectTitle() {
    assertThat(titleOf(viewer)).isEqualTo(concat("Applet Viewer: ", MyApplet.class.getName()));
  }

  public void shouldReloadApplet() {
    viewer.reloadApplet();
    assertThatAppletIsShowing();
    assertThatAppletIsLoaded();
  }

  private void assertThatAppletIsShowing() {
    assertThat(isShowing(applet)).isTrue();
  }

  private void assertThatAppletIsLoaded() {
    assertThat(isLoaded(viewer)).isTrue();
  }

  private static boolean isLoaded(final AppletViewer viewer) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return viewer.appletLoaded();
      }
    });
  }
}
