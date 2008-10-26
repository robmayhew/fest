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

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.testing.TestWindow.createAndShowNewWindow;

/**
 * Tests for <code>{@link WindowMetrics}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMetricsTest {

  private WindowMetrics metrics;
  private TestWindow window;

  @BeforeMethod public void setUp() {
    window = createAndShowNewWindow(getClass());
    metrics = new WindowMetrics(window);
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test public void shouldObtainInsets() {
    assertThat(metrics.insets).isEqualTo(window.getInsets());
  }

  @Test public void shouldCalculateCenter() {
    Insets insets = insetsOf(window);
    Dimension windowSize = sizeOf(window);
    int x = window.getX() + insets.left + ((windowSize.width - (insets.left + insets.right)) / 2);
    int y = window.getY() + insets.top + ((windowSize.height - (insets.top + insets.bottom)) / 2);
    Point center = metrics.center();
    assertThat(center.x).isEqualTo(x);
    assertThat(center.y).isEqualTo(y);
  }

  @Test public void shouldAddVerticalInsets() {
    Insets insets = window.getInsets();
    assertThat(metrics.verticalInsets()).isEqualTo(insets.right + insets.left);
  }

  @Test public void shouldAddHorizontalInsets() {
    Insets insets = window.getInsets();
    assertThat(metrics.horizontalInsets()).isEqualTo(insets.top + insets.bottom);
  }
}
