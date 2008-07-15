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

import static javax.swing.SwingUtilities.getAncestorOfClass;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

import java.awt.Container;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.MyApplet;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link AppletViewer}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class AppletViewerTests {

  private MyApplet applet;
  private FrameFixture fixture;
  private AppletViewer viewer;

  @BeforeMethod public void setUp() {
    applet = new MyApplet();
    viewer = new AppletViewer(applet);
    fixture = new FrameFixture(viewer);
    fixture.show();
    assertThat(applet.initialized()).isTrue();
    assertThat(applet.started()).isTrue();
  }

  @AfterMethod public void tearDown() {
    fixture.close();
    assertThat(applet.stopped()).isTrue();
    assertThat(applet.destroyed()).isTrue();
    fixture.cleanUp();
  }

  public void shouldLoadApplet() {
    assertThat(applet.isShowing()).isTrue();
    assertThat(viewer.getTitle()).isEqualTo(concat("Applet Viewer: ", MyApplet.class.getName()));
    Container ancestor = getAncestorOfClass(AppletViewer.class, applet);
    assertThat(ancestor).isSameAs(viewer);
    fixture.label("status").requireText("Applet loaded");
    assertThat(viewer.applet()).isSameAs(applet);
    assertThat(viewer.stub()).isInstanceOf(BasicAppletStub.class);
  }
}
