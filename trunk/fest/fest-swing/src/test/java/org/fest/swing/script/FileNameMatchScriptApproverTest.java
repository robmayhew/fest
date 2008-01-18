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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link FileNameMatchScriptApprover}</code>.
 * 
 * @author Yvonne Wang
 */
public class FileNameMatchScriptApproverTest {

  private FileNameMatchScriptApprover approver;

  @BeforeClass public void setUp() {
    approver = new FileNameMatchScriptApprover("myFile.xml");
  }

  @Test public void shouldAcceptFileIfInListOfApprovedFiles() {
    assertThat(approver.approve(new File("myFile.xml"))).isTrue();
  }

  @Test public void shouldNotAcceptFileIfNotInListOfApprovedFiles() {
    assertThat(approver.approve(new File("yourFile.xml"))).isFalse();
  }

  @Test public void shouldNotAcceptFileIfFileIsNull() {
    assertThat(approver.approve(null)).isFalse();
  }
}
