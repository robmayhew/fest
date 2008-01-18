/*
 * Created on Oct 14, 2005
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
 * Copyright @2005-2006 the original author or authors.
 */
package org.fest.swing.testing;


import org.fest.swing.script.ScriptFailure;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Understands assert methods.
 * 
 * @author Alex Ruiz
 */
public final class Assert {

  public static void assertScriptFailed(ScriptFailure failure, String dirName, String fileName) {
    assertThat(failure.getMessage()).contains(Files.absolutePath(dirName, fileName));
  }
}
