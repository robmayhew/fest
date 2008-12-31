/*
 * Created on Dec 31, 2008
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
package org.fest.javafx.desktop.util;

import javafx.stage.Stage;

import javax.swing.JFrame;

import com.sun.javafx.stage.FrameStageDelegate;

/**
 * Understands utilities related to JavaFX's desktop profile.
 *
 * @author Alex Ruiz
 */
public final class Windows {

  /**
   * Returns the <code>{@link JFrame}</code> stored in the given JavaFX stage.
   * @param stage the given JavaFX stage.
   * @return the <code>JFrame</code> from given JavaFX stage.
   */
  public static JFrame frameFrom(Stage stage) {
    FrameStageDelegate frameDelegate = (FrameStageDelegate) stage.get$impl_stageDelegate().get();
    return (JFrame) frameDelegate.get$window().get();
  }
  
  private Windows() {}
}
