/*
 * Created on Dec 30, 2008
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
package org.fest.javafx.launcher;

import javafx.stage.Stage;

import javax.swing.JFrame;

import com.sun.javafx.runtime.TypeInfo;
import com.sun.javafx.runtime.sequence.Sequence;
import com.sun.javafx.stage.FrameStageDelegate;

import static org.fest.reflect.core.Reflection.staticMethod;

/**
 * Understands how to start a compile JavaFX UI.
 *
 * @author Alex Ruiz
 */
public class JavaFxClassLauncher {

  /**
   * Launches the given compiled JavaFX UI.
   * @param javaFxClass the class containing the JavaFX UI to launch.
   * @return the <code>JFrame</code> hosting the JavaFX UI once it's started.
   */
  public static JFrame launch(Class<?> javaFxClass) {
    Stage stage = (Stage) staticMethod("javafx$run$").withReturnType(Object.class)
                                                     .withParameterTypes(Sequence.class)
                                                     .in(javaFxClass)
                                                     .invoke(TypeInfo.String.emptySequence);
    FrameStageDelegate frameDelegate = (FrameStageDelegate) stage.get$impl_stageDelegate().get();
    return (JFrame) frameDelegate.get$window().get();
  }
}
