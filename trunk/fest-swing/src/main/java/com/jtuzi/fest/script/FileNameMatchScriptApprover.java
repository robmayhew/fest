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
import java.util.Arrays;
import java.util.List;

import static com.jtuzi.fest.util.Objects.isEmpty;

/**
 * Understands a <code>{@link ScriptApprover}</code> that approves a file only if its name is in a list of approved
 * files.
 * 
 * @author Yvonne Wang
 */
public final class FileNameMatchScriptApprover implements ScriptApprover {

  /** Names of the script files approved for execution. */
  private final List<String> fileNamesToMatch;

  /**
   * Creates a new </code>{@link FileNameMatchScriptApprover}</code>.
   * @param fileNamesToMatch the names of the script files that are approved for execution.
   */
  public FileNameMatchScriptApprover(String... fileNamesToMatch) {
    if (isEmpty(fileNamesToMatch))
      throw new IllegalArgumentException("There should be at least one file name to match");
    this.fileNamesToMatch = Arrays.asList(fileNamesToMatch);
  }

  /**
   * @return <code>true</code> if the name of the given file matches any of the file names specified at construction
   *         time.
   */
  public boolean approve(File toApprove) {
    if (toApprove == null) return false;
    return fileNamesToMatch.contains(toApprove.getName());
  }

}
