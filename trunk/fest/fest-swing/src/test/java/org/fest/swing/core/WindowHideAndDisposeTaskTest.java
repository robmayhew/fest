/*
 * Created on Sep 2, 2008
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
package org.fest.swing.core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;
import org.fest.swing.timing.Condition;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.swing.timing.Pause.pause;

/**
 * Tests for <code>{@link WindowHideAndDisposeTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class WindowHideAndDisposeTaskTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  public void shouldHideAndDisposeWindow() {
    assertThat(window.isShowing()).isTrue();
    WindowHideAndDisposeTask.hideAndDispose(window);
    pause(new Condition("Window is hidden and disposed") {
      public boolean test() {
        return !window.isShowing() && window.wasDisposed();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private boolean disposed;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(WindowHideAndDisposeTaskTest.class);
    }

    @Override
    public void dispose() {
      synchronized (this) { disposed = true; }
      super.dispose();
    }

    synchronized boolean wasDisposed() { return disposed; }
  }
}
