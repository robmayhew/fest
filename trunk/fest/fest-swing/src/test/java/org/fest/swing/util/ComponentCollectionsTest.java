/*
 * Created on Oct 29, 2007
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
package org.fest.swing.util;

import java.awt.Component;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentCollections}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentCollectionsTest {

  @Test public void shouldReturnNewEmptyCollection() {
    Collection<Component> empty = ComponentCollections.empty();
    assertThat(empty).isEmpty();
    assertThat(empty).isNotSameAs(ComponentCollections.empty());
  }
  
  @Test public void shouldAlwaysReturnSameEmptyCollection() {
    Collection<Component> empty = ComponentCollections.EMPTY;
    assertThat(empty).isEmpty();
    assertThat(empty).isSameAs(ComponentCollections.EMPTY);
  }
  
  @Test public void shouldReturnCollectionWithGivenComponents() {
    JTextField textField = new JTextField();
    JButton button = new JButton();
    Collection<Component> components = ComponentCollections.components(textField, button);
    assertThat(components).containsOnly(textField, button);
  }
}
