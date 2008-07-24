/*
 * Created on Oct 18, 2007
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
package org.fest.swing.monitor;

import java.awt.Insets;
import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestWindow.showInTest;

/**
 * Tests for <code>{@link WindowMetrics}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMetricsTest {

  private WindowMetrics metrics;
  private TestWindow frame;
  
  @BeforeMethod public void setUp() {
    frame = showInTest(getClass());
    metrics = new WindowMetrics(frame);
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
  }
  
  @Test public void shouldObtainInsets() {
    assertThat(metrics.insets).isEqualTo(frame.getInsets());
  }
  
  @Test public void shouldCalculateCenter() {
    Insets insets = frame.getInsets();
    int x = frame.getX() + insets.left + ((frame.getWidth() - (insets.left + insets.right)) / 2);
    int y = frame.getY() + insets.top + ((frame.getHeight() - (insets.top + insets.bottom)) / 2);
    Point center = metrics.center();
    assertThat(center.x).isEqualTo(x);
    assertThat(center.y).isEqualTo(y);
  }

  @Test public void shouldAddVerticalInsets() {
    Insets insets = frame.getInsets();
    assertThat(metrics.verticalInsets()).isEqualTo(insets.right + insets.left);
  }

  @Test public void shouldAddHorizontalInsets() {
    Insets insets = frame.getInsets();
    assertThat(metrics.horizontalInsets()).isEqualTo(insets.top + insets.bottom);
  }
}
