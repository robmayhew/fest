/*
 * Created on Dec 1, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.remote.core;

/**
 * Understands an error that ocurred when perfoming testing on remote GUIs.
 *
 * @author Alex Ruiz
 */
public class RemoteActionFailure extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /** Creates a new </code>{@link RemoteActionFailure}</code>. */
  public RemoteActionFailure() {}

  /**
   * Creates a new </code>{@link RemoteActionFailure}</code>.
   * @param message the detail message.
   */
  public RemoteActionFailure(String message) {
    super(message);
  }

  /**
   * Creates a new </code>{@link RemoteActionFailure}</code>.
   * @param cause the cause of the error.
   */
  public RemoteActionFailure(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new </code>{@link RemoteActionFailure}</code>.
   * @param message the detail message.
   * @param cause the cause of the error.
   */
  public RemoteActionFailure(String message, Throwable cause) {
    super(message, cause);
  }
}
