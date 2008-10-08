/*
 * Created on Aug 12, 2008
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

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JSpinnerValueQuery.valueOf;

/**
 * Tests for <code>{@link JSpinnerIncrementValueTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JSpinnerIncrementValueTaskTest extends JSpinnerSetValueTaskTestCase {

  public void shouldIncrementValue() {
    JSpinnerIncrementValueTask.incrementValue(spinner());
    assertThat(valueOf(spinner())).isEqualTo("Three");
  }

  public void shouldNotIncrementValueIfPreviousValueIsNull() {
    JSpinnerIncrementValueTask.incrementValue(spinner());
    JSpinnerIncrementValueTask.incrementValue(spinner());
    robot().waitForIdle();
    assertThat(valueOf(spinner())).isEqualTo("Three");
  }
}
