/*
 * Created on Oct 20, 2007
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
import java.awt.Dimension;
import java.awt.Window;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.TestFrame;
import org.fest.swing.monitor.WindowMonitor;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ExistingHierarchy}</code>.
 *
 * @author Alex Ruiz
 */
public class ExistingHierarchyTest {

  private ExistingHierarchy hierarchy;
  
  @BeforeMethod public void setUp() {
    hierarchy = new ExistingHierarchy();
  }
  
  @Test public void shouldReturnAllRootWindows() {
    Collection<Window> rootWindows = WindowMonitor.instance().rootWindows();
    assertThat(hierarchy.rootWindows()).isEqualTo(rootWindows);
  }
  
  @Test public void shouldAlwaysContainGivenComponent() {
    assertThat(hierarchy.contains(new JTextField())).isTrue();
  }
  
  @Test public void shouldReturnParentOfComponent() {
    TestFrame frame = new TestFrame(getClass());
    JTextField textField = new JTextField();
    frame.add(textField);
    assertThat(hierarchy.parentOf(textField)).isSameAs(frame.getContentPane());
    frame.beDisposed();
  }
  
  @Test public void shouldReturnParentOfInternalFrame() {
    CustomFrame frame = new CustomFrame(getClass());
    frame.beVisible();
    JInternalFrame internalFrame = frame.internalFrame;
    assertThat(hierarchy.parentOf(internalFrame)).isSameAs(internalFrame.getDesktopIcon().getDesktopPane());
    frame.beDisposed();
  }
  
  @Test public void shouldReturnSubcomponents() {
    TestFrame frame = new TestFrame(getClass());
    JTextField textField = new JTextField(20);
    JLabel label = new JLabel("label");
    JButton button = new JButton("button");
    frame.addComponents(textField, label, button);
    frame.beVisible();
    Collection<Component> subComponents = hierarchy.childrenOf(frame.getContentPane());
    assertThat(subComponents).hasSize(3).contains(textField).contains(label).contains(button);
    frame.beDisposed();
  }
  
  @Test public void shouldReturnPopupMenuAsSubcomponentOfMenu() {
    JMenu menu = new JMenu();
    menu.add(new JMenuItem());
    Collection<Component> subComponents = hierarchy.childrenOf(menu);
    assertThat(subComponents).hasSize(1).contains(menu.getPopupMenu());
  }

  private static class CustomFrame extends TestFrame {
    private static final long serialVersionUID = 1L;
    
    JInternalFrame internalFrame;

    public CustomFrame(Class testClass) {
      super(testClass);
    }

    @Override protected void beforeShown() {
      JDesktopPane desktop = new JDesktopPane();
      createInternalFrame();
      desktop.add(internalFrame);
      setContentPane(desktop);
    }

    private void createInternalFrame() {
      internalFrame = new JInternalFrame("Internal Frame");
      internalFrame.setPreferredSize(new Dimension(200, 100));
      internalFrame.setVisible(true);
    }

    @Override protected void updateLookAndFeel() {}
  }
}
