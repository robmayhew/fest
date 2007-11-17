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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Frame;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.util.ReflectionUtils.mapField;

import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestFrame;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowFilter}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowFilterTest {

  private Map<Component, Boolean> filtered;
  private Map<Component, Boolean> implicitFiltered;

  private WindowFilter filter;
  
  @BeforeMethod public void setUp() {
    filter = new WindowFilter(new ParentFinder(), new ChildrenFinder());
    filtered = mapField("filtered", filter);
    filtered.clear();
    implicitFiltered = mapField("implicitFiltered", filter);
    implicitFiltered.clear();
  }
  
  @Test public void shouldFilterComponent() {
    Component c = new JButton();
    implicitFiltered.put(c, true);
    filter.filter(c);
    assertThat(filtered.keySet()).containsOnly(c);
    assertThat(implicitFiltered.size()).isZero();
  }
  
  @Test public void shouldFilterOwnedWindows() {
    TestFrame frame = new TestFrame(getClass());
    TestDialog dialog = new TestDialog(frame);
    implicitFiltered.put(frame, true);
    implicitFiltered.put(dialog, true);
    filter.filter(frame);
    assertThat(filtered.keySet()).containsOnly(frame, dialog);
    assertThat(implicitFiltered.size()).isZero();
  }
  
  @Test public void shouldFilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = new JDialog((Frame)null);
    implicitFiltered.put(dialog, true);
    filter.filter(dialog.getOwner());
    assertThat(filtered.keySet()).containsOnly(dialog);
    assertThat(implicitFiltered.size()).isZero();
  }
  
  @Test public void shouldUnfilter() {
    Component c = new JButton();
    filtered.put(c, true);
    implicitFiltered.put(c, true);
    filter.unfilter(c);
    assertThat(filtered.size()).isZero();
    assertThat(implicitFiltered.size()).isZero();
  }

  @Test(dependsOnMethods = "shouldFilterChildrenOfSharedInvisibleFrame") 
  public void shouldUnfilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = new JDialog((Frame)null);
    filtered.put(dialog, true);
    implicitFiltered.put(dialog, true);
    filter.unfilter(dialog.getOwner());
    assertThat(filtered.size()).isZero();
    assertThat(implicitFiltered.size()).isZero();
  }
  
  @Test public void shouldReturnTrueIfObjectIsFiltered() {
    Component c = new JButton();
    filtered.put(c, true);
    assertThat(filter.isFiltered(c)).isTrue();
  }
  
  @Test public void shouldReturnTrueIfWindowParentIsFiltered() {
    Component c = new JButton();
    TestFrame frame = new TestFrame(getClass());
    frame.add(c);
    filtered.put(frame, true);
    assertThat(filter.isFiltered(c)).isTrue();
  }
  
  @Test public void shouldReturnTrueIfParentOfWindowIsFiltered() {
    TestFrame frame = new TestFrame(getClass());
    TestDialog dialog = new TestDialog(frame);
    filtered.put(frame, true);
    assertThat(filter.isFiltered(dialog)).isTrue();
  }
  
  @Test public void shouldReturnNotFilteredIfGivenComponentIsNull() {
    assertThat(filter.isFiltered(null)).isFalse();
  }
  
  @Test public void shouldImplicitFilter() {
    Component c = new JButton();
    filter.implicitFilter(c);
    assertThat(implicitFiltered.keySet()).containsOnly(c);
  }
  
  @Test public void shouldReturnTrueIfImplicitFiltered() {
    Component c = new JButton();
    implicitFiltered.put(c, true);
    assertThat(filter.isImplicitFiltered(c)).isTrue();
  }

  @Test public void shouldReturnFalseIfNotImplicitFiltered() {
    Component c = new JButton();
    assertThat(filter.isImplicitFiltered(c)).isFalse();
  }
}
