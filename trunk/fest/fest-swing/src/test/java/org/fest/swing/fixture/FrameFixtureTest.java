/*
 * Created on Feb 10, 2007
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
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;

import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.GUITest;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FrameFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest public class FrameFixtureTest extends WindowFixtureTestCase<Frame> {

  private FrameFixture fixture;
  private JFrame target;
  
  @Test public void shouldIconifyAndDeiconifyFrame() {
    fixture.iconify();
    assertThat(fixture.target.getExtendedState()).isEqualTo(ICONIFIED);
    fixture.deiconify();
    assertThat(fixture.target.getExtendedState()).isEqualTo(NORMAL);
  }
  
  @Test public void shouldMaximizeFrame() {
    fixture.maximize();
    int frameState = fixture.target.getExtendedState() & MAXIMIZED_BOTH;
    assertThat(frameState).isEqualTo(MAXIMIZED_BOTH);
  }

  @Test(dependsOnMethods = "shouldMaximizeFrame") 
  public void shouldNormalizeFrame() {
    fixture.maximize();
    fixture.normalize();
    assertThat(fixture.target.getExtendedState()).isEqualTo(NORMAL);
  }

  protected void afterSetUp() {
    fixture.show();
    target.setSize(new Dimension(600, 400));
  }

  protected ComponentFixture<Frame> createFixture() {
    fixture = new FrameFixture(robot(), target);
    return fixture;
  }

  protected Frame createTarget() {
    target = new JFrame();
    return target;
  }
}
