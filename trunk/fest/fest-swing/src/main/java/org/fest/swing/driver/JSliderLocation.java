/*
 * Created on Jan 27, 2008
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
package org.fest.swing.driver;

import static javax.swing.SwingConstants.VERTICAL;

import java.awt.Insets;
import java.awt.Point;

import javax.swing.JSlider;

/**
 * Understands encapsulation of a location in a <code>{@link JSlider}</code>.
 *
 * <p>
 * Adapted from <code>abbot.tester.JSliderTester</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JSliderLocation {

  public Point pointAt(JSlider slider, int value) {
    if (slider.getOrientation() == VERTICAL) return locationForVerticalOrientation(slider, value);
    return locationForHorizontalOrientation(slider, value);
  }

  private Point locationForVerticalOrientation(JSlider slider, int value) {
    Point p = centerOf(slider);
    Insets insets = slider.getInsets();
    int max = slider.getHeight() - insets.top - insets.bottom - 1;
    p.y = (int)(percent(slider, value) * max);
    if (!slider.getInverted()) p.y = max - p.y;
    return p;
  }

  private Point locationForHorizontalOrientation(JSlider slider, int value) {
    Point p = centerOf(slider);
    Insets insets = slider.getInsets();
    int max = slider.getWidth() - insets.left - insets.right - 1;
    p.x = (int)(percent(slider, value) * max);
    if (slider.getInverted()) p.x = max - p.x;
    return p;
  }

  private Point centerOf(JSlider slider) {
    return new Point((slider.getWidth()/2), (slider.getHeight()/2));
  }

  private float percent(JSlider slider, int value) {
    return (float)(value - slider.getMinimum()) / range(slider);
  }

  private int range(JSlider slider) {
    return slider.getMaximum() - slider.getMinimum();
  }
}
