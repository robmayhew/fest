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

import org.testng.annotations.Test;

import static org.fest.swing.script.Scripts.*;
import static org.fest.swing.script.Assert.assertScriptFailed;

/**
 * Tests for <code>{@link ScriptAssert}</code>.
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
