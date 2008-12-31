/*
 * Created on Dec 23, 2008
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
package org.fest.javafx.desktop.launcher;

import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.stage.Stage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import static org.fest.javafx.desktop.util.Windows.frameFrom;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands how to launch a JavaFX script.
 *
 * @author Alex Ruiz
 */
public final class ScriptLauncher {

  /**
   * Launches the JavaFX script with the given name and returns the <code>{@link JFrame}</code> hosting the JavaFX UI.
   * <p>
   * <b>Note:</b> this launcher can only start JavaFX scripts that has no dependencies on other scripts or JavaFX 
   * classes.
   * </p>
   * @param scriptName the name of the JavaFX script to launch.
   * @return the <code>JFrame</code> hosting the JavaFX UI once it's started.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  @RunsInEDT
  public static JFrame launch(String scriptName) {
    if (scriptName == null) throw new NullPointerException("The name of the script to launch should not be null");
    if (isEmpty(scriptName)) 
      throw new IllegalArgumentException("The name of the script to launch should not be empty");
    Stage stage = launch(ScriptLauncher.class.getResourceAsStream(scriptName));
    return frameFrom(stage);
  }

  @RunsInEDT
  private static Stage launch(InputStream script) {
    if (script == null) throw new NullPointerException("The stream containing the script to launch should not be null");
    ScriptEngineManager manager = new ScriptEngineManager();
    final ScriptEngine engine = manager.getEngineByExtension("fx");
    final InputStreamReader reader = new InputStreamReader(script);
    return execute(new GuiQuery<Stage>() {
      protected Stage executeInEDT() throws Throwable {
        return (Stage) engine.eval(reader);
      }
    });
  }
  
  private ScriptLauncher() {}
}
