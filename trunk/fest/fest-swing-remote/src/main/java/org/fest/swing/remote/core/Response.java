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

import java.io.Serializable;

import static org.fest.util.Strings.concat;

/**
 * Understands a result of processing a request.
 *
 * @author Alex Ruiz
 */
public final class Response implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private final boolean success;
  private final RemoteActionFailure cause;

  /**
   * Indicates that the processing of a request was successful.
   * @return the created response.
   */
  static Response successful() {
    return new Response(true);
  }
  
  /**
   * Indicates that the processing of a request failed. 
   * @return the created response.
   */
  static Response failed() {
    return new Response(false);
  }

  /**
   * Indicates that the processing of a request failed. 
   * @param cause the cause of the failure.
   * @return the created response.
   */  
  static Response failed(RemoteActionFailure cause) {
    return new Response(false, cause);
  }

  private Response(boolean success) {
    this(success, null);
  }

  private Response(boolean success, RemoteActionFailure cause) {
    this.success = success;
    this.cause = cause;
  }

  /**
   * Returns the status of this response.
   * @return the status of this response.
   */
  public boolean success() { return success; }
  
  /**
   * Returns the cause of a failure, only if the status of this response is not successful.
   * @return the cause of a failure.
   */
  public RemoteActionFailure cause() { return cause; }

  /** @see java.lang.Object#toString() */
  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "success:", String.valueOf(success), ", ",
        "cause:", cause, "]"
    );
  }
}
