/*
 * Created on Aug 27, 2007
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
package org.fest.swing.util;

import org.testng.annotations.Test;

import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.Platform.IS_OS_X;

/**
 * Tests for <code>{@link Platform}</code>
 *
 * @author Alex Ruiz
 */
public class PlatformTest {

  @Test public void shouldReturnControlKeyForNonMacOS() {
    if (IS_OS_X)
      assertThat(Platform.controlOrCommandKey()).isEqualTo(VK_META);
    else  
      assertThat(Platform.controlOrCommandKey()).isEqualTo(VK_CONTROL);
  }
}
