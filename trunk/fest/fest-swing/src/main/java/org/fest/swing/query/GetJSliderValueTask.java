/*
 * Created on Jul 28, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.query;

import javax.swing.JSlider;

import org.fest.swing.core.GuiQuery;

/**
 * Understands a task that returns the value of a <code>{@link JSlider}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class GetJSliderValueTask extends GuiQuery<Integer> {

  private final JSlider slider;

  /**
   * Returns the value of the given <code>{@link JSlider}</code>. This action is executed in the event dispatch thread.
   * @param slider the given <code>JSlider</code>.
   * @return the value of the given <code>JSlider</code>.
   */
  public static int valueOf(JSlider slider) {
    return new GetJSliderValueTask(slider).run();
  }

  private GetJSliderValueTask(JSlider slider) {
    this.slider = slider;
  }

  /**
   * Returns the value in this task's <code>{@link JSlider}</code>. This action is executed in the event dispatch
   * thread.
   * @return the value in this task's <code>JSlider</code>.
   */
  protected Integer executeInEDT() {
    return slider.getValue();
  }
}