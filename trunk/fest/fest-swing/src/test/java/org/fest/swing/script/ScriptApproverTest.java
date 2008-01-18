/*
 * Created on Sep 23, 2006
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

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link ScriptApprover#DEFAULT_APPROVER}</code>.
 * 
 * @author Alex Ruiz
 */
public class ScriptApproverTest {

  @Test public void shouldRejectFileWithNameStartingWithDotPound() {
    assertThat(ScriptApprover.DEFAULT_APPROVER.approve(new File(".#sm"))).isFalse();
  }

  @Test public void shouldRejectFileWithNameEndingWithTilde() {
    assertThat(ScriptApprover.DEFAULT_APPROVER.approve(new File("myFile~"))).isFalse();
  }

  @Test public void shouldRejectFileWithNameEndingWithDotBak() {
    assertThat(ScriptApprover.DEFAULT_APPROVER.approve(new File("myFile.bak"))).isFalse();
  }

  @Test public void shouldAcceptFileWithNameNotStartingWithDotPoundAndNotEndingWithTildeOrDotBak() {
    assertThat(ScriptApprover.DEFAULT_APPROVER.approve(new File("myScript.xml"))).isTrue();
  }
}
