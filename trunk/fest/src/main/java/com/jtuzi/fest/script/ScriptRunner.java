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
package com.jtuzi.fest.script;

import static com.jtuzi.fest.util.Files.*;
import static com.jtuzi.fest.util.Objects.*;
import static com.jtuzi.fest.util.Strings.quote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.script.Script;
import abbot.script.StepRunner;
import abbot.util.AWTFixtureHelper;


/**
 * Understands how to run one or more Abbot scripts.
 * 
 * @author Alex Ruiz
 */
public final class ScriptRunner {

  private static final Hierarchy DUMMY_HIERARCHY = new AWTHierarchy();

  private static Logger logger = Logger.getLogger(ScriptRunner.class.getName());

  private final List<String> scriptNames = new ArrayList<String>();

  private final List<String> successfulScripts = new ArrayList<String>();

  /**
   * Creates a new </code>{@link ScriptRunner}</code>.
   * @param dirName the directory containing the script files to execute. Recursion is not enabled.
   * @param approvers approve or rejects the files to be executed. If none is specified, all the script files in the
   *          given directory will be approved.
   * @throws IllegalArgumentException if the given directory is not a directory or if it does not exist.
   * @throws IllegalStateException if the runner could not find scripts to execute.
   */
  public ScriptRunner(String dirName, ScriptApprover... approvers) {
    this(dirName, false, approvers);
  }

  /**
   * Creates a new </code>{@link ScriptRunner}</code>.
   * @param dirName the directory containing the script files to execute.
   * @param recurse flag that indicates if subdirectories should be searched for script files.
   * @param approvers approve or rejects the files to be executed. If none is specified, all the script files in the
   *          given directory will be approved.
   * @throws IllegalArgumentException if the given directory is not a directory or if it does not exist.
   * @throws IllegalStateException if the runner could not find scripts to execute.
   */
  public ScriptRunner(String dirName, boolean recurse, ScriptApprover... approvers) {
    this(fileNamesIn(dirName, recurse), approvers);
  }

  /**
   * Creates a new </code>{@link ScriptRunner}</code>.
   * @param fileNames the names of the script files to execute.
   * @param approvers approve or rejects the files to be executed. If none is specified, all the script files in the
   *          given directory will be approved.
   * @throws IllegalStateException if the runner could not find scripts to execute.
   */
  public ScriptRunner(List<String> fileNames, ScriptApprover... approvers) {
    this(fileNames.toArray(new String[fileNames.size()]), approvers);
  }

  /**
   * Creates a new </code>{@link ScriptRunner}</code>.
   * @param fileNames the names of the script files to execute.
   * @param approvers approve or rejects the files to be executed. If none is specified, all the script files in the
   *          given directory will be approved.
   * @throws IllegalStateException if the runner could not find scripts to execute.
   */
  public ScriptRunner(String[] fileNames, ScriptApprover... approvers) {
    for (String fileName : fileNames) {
      File file = new File(fileName);
      if (!approved(file, approvers)) continue;
      scriptNames.add(file.getAbsolutePath());
    }
    if (scriptNames.isEmpty()) throw new IllegalStateException("No scripts found");
  }

  private boolean approved(File file, ScriptApprover[] approvers) {
    if (isEmpty(approvers)) return true;
    for (ScriptApprover approver : approvers)
      if (!approver.approve(file)) return false;
    return true;
  }

  /**
   * Executes the script files specified during construction.
   */
  public void runScripts() {
    successfulScripts.clear();
    for (String scriptName : scriptNames) runScript(scriptName);
  }

  private void runScript(String scriptName) {
    StepRunner runner = new StepRunner(new AWTFixtureHelper());
    AWTHierarchy.setDefault(runner.getHierarchy());
    Script script = new Script(scriptName, DUMMY_HIERARCHY);
    logger.info("Running " + quote(script));
    try {
      runner.run(script);
      successfulScripts.add(scriptName);
    } catch (Throwable t) {
      throw new ScriptFailure(scriptName, t);
    } finally {
      logger.info(quote(scriptName) + " finished");
    }
  }

  public String[] successfulScripts() {
    return successfulScripts.toArray(new String[successfulScripts.size()]);
  }
}
