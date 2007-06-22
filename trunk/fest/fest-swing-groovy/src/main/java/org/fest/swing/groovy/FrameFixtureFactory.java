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

import java.awt.Frame;
import java.util.Map;

import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.fixture.FrameFixture;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class FrameFixtureFactory implements FixtureFactory<Frame, FrameFixture> {

  public FrameFixture newInstance(GUITestBuilder builder, Object name, Object value, Map<Object, Object> properties) {
    assertThat(value).isInstanceOf(Frame.class);
    FrameFixture fixture = new FrameFixture(builder.robot, (Frame)value);
    return fixture;
  }

  public boolean subnodeHandled(ComponentFixture<?> fixture, GUITestBuilder builder, Object name, Object value, Map properties) {
    // TODO Auto-generated method stub
    return false;
  }

}
