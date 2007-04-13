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
package com.jtuzi.fest.script;

import java.io.File;

import com.jtuzi.fest.script.FileNameMatchScriptApprover;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link FileNameMatchScriptApprover}</code>.
 * 
 * @author Yvonne Wang
 */
public class FileNameMatchScriptApproverTest {

  private FileNameMatchScriptApprover approver;

  @BeforeClass public void setUp() {
    approver = new FileNameMatchScriptApprover("myFile.xml");
  }

  @Test public void shouldAcceptFileIfInListOfApprovedFiles() {
    assertTrue(approver.approve(new File("myFile.xml")));
  }

  @Test public void shouldNotAcceptFileIfNotInListOfApprovedFiles() {
    assertFalse(approver.approve(new File("yourFile.xml")));
  }

  @Test public void shouldNotAcceptFileIfFileIsNull() {
    assertFalse(approver.approve(null));
  }
}
