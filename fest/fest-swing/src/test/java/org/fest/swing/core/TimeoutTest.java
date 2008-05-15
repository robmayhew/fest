/*
 * Created on Nov 14, 2007
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
package org.fest.swing.core;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link Timeout}</code>.
 *
 * @author Yvonne Wang
 */
public class TimeoutTest {

  @Test public void shouldReturnDurationPassedWhenCreated() {
    Timeout timeout = Timeout.timeout(2000);
    assertThat(timeout.duration()).isEqualTo(2000);
  }
  
  @Test public void shouldReturnDurationWhenCreatedWithTimeUnit() {
    Timeout timeout = Timeout.timeout(3, TimeUnit.SECONDS);
    assertThat(timeout.duration()).isEqualTo(3000);
  }
}
