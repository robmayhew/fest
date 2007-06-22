/*
 * Created on Jun 22, 2007
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
package org.fest.swing.groovy;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Frame;

import org.fest.swing.fixture.FrameFixture;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class FrameFixtureFactory implements FixtureFactory<Frame, FrameFixture> {

  public FrameFixture newInstance(Context context) {
    assertThat(context.value).isInstanceOf(Frame.class);
    FrameFixture fixture = new FrameFixture(context.builder.robot, (Frame)context.value);
    return fixture;
  }

  public boolean fixtureSettingCreated(Context context) {
    if (!(context.currentFixture instanceof FrameFixture)) return false;
    FrameFixture fixture = (FrameFixture)context.currentFixture;
    if (shown(fixture, context)) return true;
    return false;
  }

  private boolean shown(FrameFixture fixture, Context context) {
    if (!"show".equals(context.name)) return false;
    fixture.show();
    return true;
  }
}
