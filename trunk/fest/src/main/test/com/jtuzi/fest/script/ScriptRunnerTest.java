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

import java.io.File;

import com.jtuzi.fest.script.FileNameMatchScriptApprover;
import com.jtuzi.fest.script.ScriptApprover;
import com.jtuzi.fest.script.ScriptFailure;
import com.jtuzi.fest.script.ScriptRunner;

import static com.jtuzi.fest.Assert.assertScriptFailed;
import static com.jtuzi.fest.Files.absolutePath;
import static com.jtuzi.fest.script.Scripts.MUST_BE_SUCCESSFUL;
import static com.jtuzi.fest.script.Scripts.MUST_FAIL;
import static com.jtuzi.fest.script.Scripts.ROOT_FOLDER;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link ScriptRunner}</code>.
 * 
 * @author Alex Ruiz
 */
public class ScriptRunnerTest {

  @Test public void shouldExecuteAcceptedScriptOnly() {
    ScriptRunner runner = new ScriptRunner(ROOT_FOLDER, new FileNameMatchScriptApprover(MUST_BE_SUCCESSFUL));
    runner.runScripts();
    assertOneSuccess(runner, ROOT_FOLDER, MUST_BE_SUCCESSFUL);
  }

  @Test public void shouldExecuteAllScriptsIfNoSpecifiedApprovers() {
    ScriptRunner runner = new ScriptRunner(ROOT_FOLDER);
    try {
      runner.runScripts();
      fail("Expecting script failure");
    } catch (ScriptFailure failure) {
      assertScriptFailed(failure, ROOT_FOLDER, MUST_FAIL); 
    }
    assertOneSuccess(runner, ROOT_FOLDER, MUST_BE_SUCCESSFUL);
  }

  @Test public void shouldReportFailedScripts() {
    ScriptRunner runner = new ScriptRunner(ROOT_FOLDER, new FileNameMatchScriptApprover(MUST_FAIL));
    try {
      runner.runScripts();
      fail("Expecting script failure");
    } catch (ScriptFailure failure) {
      assertScriptFailed(failure, ROOT_FOLDER, MUST_FAIL); 
    }
    assertEquals(runner.successfulScripts().length, 0);
  }

  private void assertOneSuccess(ScriptRunner runner, String dirName, String fileName) {
    String[] successfulScripts = runner.successfulScripts();
    assertEquals(successfulScripts.length, 1);
    assertEquals(successfulScripts[0], absolutePath(dirName, fileName));
  }

  @Test(expectedExceptions = { IllegalStateException.class }) 
  public void shouldFailIfNoScriptsFound() {
    new ScriptRunner(ROOT_FOLDER, new ScriptApprover() {
      public boolean approve(File toApprove) {
        return false;
      }
    });
  }

  @Test(expectedExceptions = { IllegalArgumentException.class }) 
  public void shouldFailIfDirectoryDoesNotExist() {
    new ScriptRunner("notExisting");
  }
}
