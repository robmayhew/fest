/*
 * Created on Nov 1, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.util.Map;

import javax.swing.JDialog;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JButtons.button;
import static org.fest.swing.factory.JDialogs.dialog;

/**
 * Tests for <code>{@link WindowFilter}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowFilterTest {

  private Map<Component, Boolean> ignored;
  private Map<Component, Boolean> implictlyIgnored;

  private WindowFilter filter;
  
  @BeforeMethod public void setUp() {
    filter = new WindowFilter(new ParentFinder(), new ChildrenFinder());
    ignored = filter.ignored;
    ignored.clear();
    implictlyIgnored = filter.implicitlyIgnored;
    implictlyIgnored.clear();
  }
  
  @Test public void shouldFilterComponent() {
    Component c = button().createNew();
    implictlyIgnored.put(c, true);
    filter.ignore(c);
    assertThat(ignored.keySet()).containsOnly(c);
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldFilterOwnedWindows() {
    TestWindow window = TestWindow.createInEDT(getClass());
    TestDialog dialog = TestDialog.createInEDT(window);
    implictlyIgnored.put(window, true);
    implictlyIgnored.put(dialog, true);
    filter.ignore(window);
    assertThat(ignored.keySet()).containsOnly(window, dialog);
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldFilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = dialog().createNew();
    implictlyIgnored.put(dialog, true);
    filter.ignore(dialog.getOwner());
    assertThat(ignored.keySet()).containsOnly(dialog);
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldUnfilter() {
    Component c = button().createNew();
    ignored.put(c, true);
    implictlyIgnored.put(c, true);
    filter.recognize(c);
    assertThat(ignored.size()).isZero();
    assertThat(implictlyIgnored.size()).isZero();
  }

  @Test(dependsOnMethods = "shouldFilterChildrenOfSharedInvisibleFrame") 
  public void shouldUnfilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = dialog().createNew();
    ignored.put(dialog, true);
    implictlyIgnored.put(dialog, true);
    filter.recognize(dialog.getOwner());
    assertThat(ignored.size()).isZero();
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldReturnTrueIfObjectIsFiltered() {
    Component c = button().createNew();
    ignored.put(c, true);
    assertThat(filter.isIgnored(c)).isTrue();
  }
  
  @Test public void shouldReturnTrueIfWindowParentIsFiltered() {
    Component c = button().createNew();
    TestWindow window = TestWindow.createInEDT(getClass());
    // TODO call in EDT
    window.add(c);
    ignored.put(window, true);
    assertThat(filter.isIgnored(c)).isTrue();
  }
  
  @Test public void shouldReturnTrueIfParentOfWindowIsFiltered() {
    TestWindow window = TestWindow.createInEDT(getClass());
    TestDialog dialog = TestDialog.createInEDT(window);
    ignored.put(window, true);
    assertThat(filter.isIgnored(dialog)).isTrue();
  }
  
  @Test public void shouldReturnNotFilteredIfGivenComponentIsNull() {
    assertThat(filter.isIgnored(null)).isFalse();
  }
  
  @Test public void shouldImplicitFilter() {
    Component c = button().createNew();
    filter.implicitlyIgnore(c);
    assertThat(implictlyIgnored.keySet()).containsOnly(c);
  }
  
  @Test public void shouldReturnTrueIfImplicitFiltered() {
    Component c = button().createNew();
    implictlyIgnored.put(c, true);
    assertThat(filter.isImplicitlyIgnored(c)).isTrue();
  }

  @Test public void shouldReturnFalseIfNotImplicitFiltered() {
    Component c = button().createNew();
    assertThat(filter.isImplicitlyIgnored(c)).isFalse();
  }
}
