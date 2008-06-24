/*
 * Created on Jun 11, 2008
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
package org.fest.swing.keystroke;

import java.awt.Dimension;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import org.testng.annotations.*;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTextComponentDriver;
import org.fest.swing.testing.KeyRecorder;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Test case for implementations of <code>{@link KeyStrokeMappingProvider}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public abstract class KeyStrokeMappingProviderTestCase {

  static final char BACKSPACE = 8;
  static final char CR = 10;
  static final char DELETE = 127;
  static final char ESCAPE = 27;
  static final char LF = 13;
  
  private Robot robot;
  private JTextComponent textArea;
  private JTextComponentDriver driver;
  
  private Collection<KeyStrokeMapping> keyStrokeMappings;
  
  @BeforeClass public final void setUpOnce() {
    verifyTestCanRun();
    keyStrokeMappings = keyStrokeMappingsToTest();
  }

  abstract void verifyTestCanRun();

  abstract Collection<KeyStrokeMapping> keyStrokeMappingsToTest();
  
  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyFrame frame = new MyFrame();
    textArea = frame.textArea;
    robot.showWindow(frame);
    driver = new JTextComponentDriver(robot); 
  }
  
  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }
  
  @DataProvider(name = "keyStrokeMappings") public final Iterator<Object[]> keyStrokeMappings() {
    return new KeyStrokeMappingIterator(keyStrokeMappings);
  }
  
  final void focusTextArea() {
    driver.focus(textArea);
  }
  
  final void assertCorrectTextEntered(char character, KeyStroke keyStroke) {
    driver.deleteText(textArea);
    pressInTextArea(keyStroke);
    driver.requireText(textArea, String.valueOf(character));
  }
  
  final void assertKeyWasPressedWithoutModifiers(KeyStroke keyStroke, int expectedKey) {
    assertThat(keyStroke.getModifiers()).isEqualTo(0);
    KeyRecorder recorder = KeyRecorder.attachTo(textArea);
    pressInTextArea(keyStroke);
    recorder.keysPressed(expectedKey);
  }

  private void pressInTextArea(KeyStroke keyStroke) {
    driver.pressAndReleaseKey(textArea, keyStroke.getKeyCode(), new int[] { keyStroke.getModifiers() });
  }
  
  static class KeyStrokeMappingIterator implements Iterator<Object[]> {
    private final Iterator<KeyStrokeMapping> realIterator;

    KeyStrokeMappingIterator(Collection<KeyStrokeMapping> keyStrokeMappings) {
      realIterator = keyStrokeMappings.iterator();
    }
    
    public boolean hasNext() {
      return realIterator.hasNext();
    }

    public Object[] next() {
      KeyStrokeMapping next = realIterator.next();
      return new Object[] { next.character(), next.keyStroke() };
    }

    public void remove() {}
  }
  
  static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextArea textArea = new JTextArea(3, 8);
    
    MyFrame() {
      super(KeyStrokeMappingProvider_enTest.class);
      add(textArea);
      setPreferredSize(new Dimension(200, 200));
    }
  }

}
