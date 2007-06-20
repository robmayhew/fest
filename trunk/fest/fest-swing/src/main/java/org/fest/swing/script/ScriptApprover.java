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

/**
 * Understands approval or rejection of execution of a given script file.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public interface ScriptApprover {

  /**
   * Rejects any file which name starts with ".#" or ends with either "~" or ".bak".
   */
  ScriptApprover DEFAULT_APPROVER = new ScriptApprover() {

    /**
     * Rejects any file which name starts with ".#" or ends with either "~" or ".bak".
     * @param toApprove the file to approve.
     * @return <code>false</code> if the name of the given file starts with ".#" or ends with either "~" or ".bak".
     */
    public boolean approve(File toApprove) {
      if (toApprove == null) return false;
      String name = toApprove.getName();
      return !name.startsWith(".#") && !name.endsWith("~") && !name.endsWith(".bak");
    }
  };

  /**
   * Approves or rejects the execution of the given script file.
   * @param toApprove the script file to approve.
   * @return <code>true</code> if the given file can be executed, otherwise <code>false</code>.
   */
  boolean approve(File toApprove);
}