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

import java.awt.Container;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.MyApplet;

import static javax.swing.SwingUtilities.getAncestorOfClass;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.task.IsComponentShowingTask.isShowing;
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
    applet = new GuiTask<MyApplet>() {
      protected MyApplet executeInEDT() {
        return new MyApplet();
      }
    }.run();
    viewer = new GuiTask<AppletViewer>() {
      protected AppletViewer executeInEDT() {
        return new AppletViewer(applet);
      }
    }.run();
    fixture = new FrameFixture(viewer);
    fixture.show();
    assertThatAppletIsInitializedAndStarted();
  }
  
  private void assertThatAppletIsInitializedAndStarted() {
    boolean initialized = new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.initialized();
      }
    }.run();
    assertThat(initialized).isTrue();
    boolean started = new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.started();
      }
    }.run();
    assertThat(started).isTrue();
  }
  
  @AfterMethod public void tearDown() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        viewer.unloadApplet();
        return null;
      }
    }.run();
    assertThatApplietIsStoppedAndDestroyed();
    fixture.cleanUp();
  }

  private void assertThatApplietIsStoppedAndDestroyed() {
    boolean stopped = new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.stopped();
      }
    }.run();
    assertThat(stopped).isTrue();
    boolean destroyed = new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return applet.destroyed();
      }
    }.run();
    assertThat(destroyed).isTrue();
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
    String title = new GuiTask<String>() {
      protected String executeInEDT() {
        return viewer.getTitle();
      }
    }.run();
    assertThat(title).isEqualTo(concat("Applet Viewer: ", MyApplet.class.getName()));
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
    boolean loaded = new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return viewer.appletLoaded();
      }
    }.run();
    assertThat(loaded).isTrue();
  }
}
