/*
 * Created on Sep 22, 2006
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.script;


/**
 * Understands assert methods related to execution of <a href="http://abbot.sourceforge.net" target="_blank">Abbot<a/> 
 * test scripts.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ScriptAssert {

  /** Platform-dependent line separator. */
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  /**
   * Asserts that all the scripts in the specified directory are executed successfully. This method does not execute
   * scripts located in subfolders of the given directory. This method reports all the failing scripts as well as the 
   * cause of the failure.
   * @param dirName the specified directory where the scripts to execute are located.
   * @param approvers zero or more script approvers that specify which scripts should be executed.
   */
  public static void assertNoFailures(String dirName, ScriptApprover... approvers) {
    ScriptRunner runner = new ScriptRunner(dirName, approvers);
    runner.runScripts();
  }

  private ScriptAssert() {}

}
