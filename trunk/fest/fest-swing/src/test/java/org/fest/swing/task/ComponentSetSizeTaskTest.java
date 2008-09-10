/*
 * Created on Feb 23, 2008
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
package org.fest.swing.task;

import java.awt.Dimension;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Test for <code>{@link ComponentSetSizeTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentSetSizeTaskTest {

  private TestWindow window;
  private Dimension size;

  @BeforeMethod public void setUp() {
    window = TestWindow.showNewInTest(getClass());
    size = new Dimension(200, 100);
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test public void shouldSetSize() {
    assertThat(window.getSize()).isNotEqualTo(size);
    ComponentSetSizeTask.setComponentSize(window, size);
    assertThat(window.getSize()).isEqualTo(size);
  }
}
