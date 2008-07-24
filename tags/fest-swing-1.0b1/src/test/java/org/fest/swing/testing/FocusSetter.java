/*
 * Created on Apr 1, 2008
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
package org.fest.swing.testing;

import java.awt.Component;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;

/**
 * Understands setting focus on a <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 */
public final class FocusSetter {

  public static void setFocusOn(Component c) {
    c.requestFocusInWindow();
    pause(200);
    assertThat(c.hasFocus());
  }

  private FocusSetter() {}
}
