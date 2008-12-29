/*
 * Created on Dec 22, 2008
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
package org.fest.javafx;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.SwingUtilities;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 * @author Michael Huettermann
 */
public class AudioConfigLauncher {

  /**
   * @param args
   * JSR-223 Script Engine
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("fx");
        InputStream stream = AudioConfigLauncher.class.getResourceAsStream("AudioConfigStage.fx");
        System.out.println("stream is null? " + (stream == null));
        InputStreamReader reader = new InputStreamReader(stream);
        try {
          engine.eval(reader);
        } catch (ScriptException e) {
          e.printStackTrace();
        }
      }
    });
  }

}
