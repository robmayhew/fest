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
import java.awt.Frame;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;

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
    Component c = new JButton();
    implictlyIgnored.put(c, true);
    filter.ignore(c);
    assertThat(ignored.keySet()).containsOnly(c);
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldFilterOwnedWindows() {
    TestFrame frame = new TestFrame(getClass());
    TestDialog dialog = new TestDialog(frame);
    implictlyIgnored.put(frame, true);
    implictlyIgnored.put(dialog, true);
    filter.ignore(frame);
    assertThat(ignored.keySet()).containsOnly(frame, dialog);
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldFilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = new JDialog((Frame)null);
    implictlyIgnored.put(dialog, true);
    filter.ignore(dialog.getOwner());
    assertThat(ignored.keySet()).containsOnly(dialog);
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldUnfilter() {
    Component c = new JButton();
    ignored.put(c, true);
    implictlyIgnored.put(c, true);
    filter.recognize(c);
    assertThat(ignored.size()).isZero();
    assertThat(implictlyIgnored.size()).isZero();
  }

  @Test(dependsOnMethods = "shouldFilterChildrenOfSharedInvisibleFrame") 
  public void shouldUnfilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = new JDialog((Frame)null);
    ignored.put(dialog, true);
    implictlyIgnored.put(dialog, true);
    filter.recognize(dialog.getOwner());
    assertThat(ignored.size()).isZero();
    assertThat(implictlyIgnored.size()).isZero();
  }
  
  @Test public void shouldReturnTrueIfObjectIsFiltered() {
    Component c = new JButton();
    ignored.put(c, true);
    assertThat(filter.isIgnored(c)).isTrue();
  }
  
  @Test public void shouldReturnTrueIfWindowParentIsFiltered() {
    Component c = new JButton();
    TestFrame frame = new TestFrame(getClass());
    frame.add(c);
    ignored.put(frame, true);
    assertThat(filter.isIgnored(c)).isTrue();
  }
  
  @Test public void shouldReturnTrueIfParentOfWindowIsFiltered() {
    TestFrame frame = new TestFrame(getClass());
    TestDialog dialog = new TestDialog(frame);
    ignored.put(frame, true);
    assertThat(filter.isIgnored(dialog)).isTrue();
  }
  
  @Test public void shouldReturnNotFilteredIfGivenComponentIsNull() {
    assertThat(filter.isIgnored(null)).isFalse();
  }
  
  @Test public void shouldImplicitFilter() {
    Component c = new JButton();
    filter.implicitlyIgnore(c);
    assertThat(implictlyIgnored.keySet()).containsOnly(c);
  }
  
  @Test public void shouldReturnTrueIfImplicitFiltered() {
    Component c = new JButton();
    implictlyIgnored.put(c, true);
    assertThat(filter.isImplicitlyIgnored(c)).isTrue();
  }

  @Test public void shouldReturnFalseIfNotImplicitFiltered() {
    Component c = new JButton();
    assertThat(filter.isImplicitlyIgnored(c)).isFalse();
  }
}
