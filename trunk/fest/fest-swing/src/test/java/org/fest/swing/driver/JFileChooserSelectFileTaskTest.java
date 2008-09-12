/*
 * Created on Aug 8, 2008
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

import java.io.File;

import javax.swing.JFileChooser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.testing.TestWindow;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Files.*;

/**
 * Tests for <code>{@link JFileChooserSelectFileTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION }) 
public class JFileChooserSelectFileTaskTest {

  private MyWindow window;
  private File fileToSelect;
  
  @BeforeMethod public void setUp() {
    fileToSelect = newTemporaryFile();
    window = MyWindow.createNew();
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    fileToSelect.delete();
  }
  
  public void shouldSelectFile() {
    JFileChooserSelectFileTask.setSelectedFile(window.fileChooser, fileToSelect);
    pause(new Condition("File is selected") {
      public boolean test() {
        return window.fileChooser.getSelectedFile() == fileToSelect;
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final JFileChooser fileChooser = new JFileChooser(temporaryFolder());
    
    private MyWindow() {
      super(JFileChooserSelectFileTask.class);
      add(fileChooser);
    }
  }
}
