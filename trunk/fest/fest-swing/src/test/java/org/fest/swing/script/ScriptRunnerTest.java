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

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.testing.Assert.assertScriptFailed;
import static org.fest.swing.testing.Files.absolutePath;
import static org.fest.swing.script.Scripts.MUST_BE_SUCCESSFUL;
import static org.fest.swing.script.Scripts.MUST_FAIL;
import static org.fest.swing.script.Scripts.ROOT_FOLDER;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ScriptRunner}</code>.
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
    assertThat(runner.successfulScripts().length).isEqualTo(0);
  }

  private void assertOneSuccess(ScriptRunner runner, String dirName, String fileName) {
    String[] successfulScripts = runner.successfulScripts();
    assertThat(successfulScripts.length).isEqualTo(1);
    assertThat(successfulScripts[0]).isEqualTo(absolutePath(dirName, fileName));
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
