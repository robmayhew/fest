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

import static com.jtuzi.fest.Assert.assertScriptFailed;
import static com.jtuzi.fest.script.Scripts.MUST_FAIL;
import static com.jtuzi.fest.script.Scripts.ROOT_FOLDER;

import com.jtuzi.fest.script.FileNameMatchScriptApprover;
import com.jtuzi.fest.script.ScriptAssert;
import com.jtuzi.fest.script.ScriptFailure;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link ScriptAssert}</code>.
 * 
 * @author Alex Ruiz
 */
public class ScriptAssertTest {

  @Test public void shouldReportFailedScripts() {
    try {
      ScriptAssert.assertNoFailures(ROOT_FOLDER, new FileNameMatchScriptApprover(MUST_FAIL));
    } catch (ScriptFailure failure) {
      assertScriptFailed(failure, ROOT_FOLDER, MUST_FAIL);  
    }
  }

}
