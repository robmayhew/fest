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
package org.fest.javafx.launcher;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands how to launch a JavaFX script.
 *
 * @author Alex Ruiz
 */
public final class ScriptLauncher {

  /**
   * Launches the JavaFX script with the given name.
   * @param scriptName the name of the JavaFX script to launch.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  @RunsInEDT
  public static void launch(String scriptName) {
    if (scriptName == null) throw new NullPointerException("The name of the script to launch should not be null");
    if (isEmpty(scriptName)) 
      throw new IllegalArgumentException("The name of the script to launch should not be empty");
    launch(ScriptLauncher.class.getResourceAsStream(scriptName));
  }

  @RunsInEDT
  private static void launch(final InputStream script) {
    if (script == null) throw new NullPointerException("The stream containing the script to launch should not be null");
    execute(new GuiTask() {
      protected void executeInEDT() throws Throwable {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("fx");
        InputStreamReader reader = new InputStreamReader(script);
        engine.eval(reader);
      }
    });
  }
  
  private ScriptLauncher() {}
}
