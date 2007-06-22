/*
 * Created on Jun 22, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.groovy;

import java.awt.Component;
import java.util.Map;

import org.fest.swing.fixture.ComponentFixture;

/**
 * Understands a context containing necessary information to create <code>{@link SwingFixtureBuilder}</code> nodes.
 * 
 * @author Alex Ruiz
 */
final class Context {

  public static Context context(SwingFixtureBuilder builder, Object name, Object value, Map<Object, Object> attributes) {
    return context(builder, null, name, value, attributes);
  }

  public static Context context(ComponentFixture<? extends Component> currentFixture, Object name, Object value,
                                  Map<Object, Object> attributes) {
    return context(null, currentFixture, name, value, attributes);
  }

  public static Context context(SwingFixtureBuilder builder, ComponentFixture<? extends Component> currentFixture,
                                  Object name, Object value, Map<Object, Object> attributes) {
    return new Context(builder, currentFixture, name, value, attributes);
  }

  final SwingFixtureBuilder builder;
  final ComponentFixture<? extends Component> currentFixture;
  final Object name;
  final Object value;
  final Map<Object, Object> attributes;

  private Context(SwingFixtureBuilder builder, ComponentFixture<? extends Component> currentFixture, Object name,
                   Object value, Map<Object, Object> attributes) {
    this.builder = builder;
    this.currentFixture = currentFixture;
    this.name = name;
    this.value = value;
    this.attributes = attributes;
  }
}
