/*
 * Created on Sep 1, 2008
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JButtons.button;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link ComponentAddFocusListenerTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class ComponentAddFocusListenerTaskTest {

  private Component component;
  private FocusListener listener;
  
  @BeforeMethod public void setUp() {
    component = button().createNew();
    listener = new FocusAdapter() {};
  }
  
  public void shouldAddFocusListenerToComponent() {
    ComponentAddFocusListenerTask.addFocusListener(component, listener);
    assertThat(component.getFocusListeners()).contains(listener);
  }
}
