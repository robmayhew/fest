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

/**
 * Understands a result of processing a request.
 *
 * @author Alex Ruiz
 */
public final class Response implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private final Status status;
  private final Exception cause;

  /**
   * Understands the status of processing a request.
   *
   * @author Alex Ruiz
   */
  public static enum Status {
    SUCCESS, FAILED;
  }
  
  /**
   * Indicates that the processing of a request was successful.
   * @return the created response.
   */
  public static Response successful() {
    return new Response(Status.SUCCESS);
  }
  
  /**
   * Indicates that the processing of a request failed. 
   * @return the created response.
   */
  public static Response failed() {
    return new Response(Status.FAILED);
  }

  /**
   * Indicates that the processing of a request failed. 
   * @param cause the cause of the failure.
   * @return the created response.
   */  
  public static Response failed(Exception cause) {
    return new Response(Status.FAILED, cause);
  }
  
  /**
   * Creates a new </code>{@link Response}</code>.
   * @param status the status of this response.
   */
  private Response(Status status) {
    this(status, null);
  }

  /**
   * Creates a new </code>{@link Response}</code>.
   * @param status the status of this response.
   * @param cause the cause of the failure.
   */
  private Response(Status status, Exception cause) {
    this.status = status;
    this.cause = cause;
  }

  /**
   * Returns the status of this response.
   * @return the status of this response.
   */
  public Status status() { return status; }
  
  /**
   * Returns the cause of a failure, only if the status of this response is <code>{@link Status#FAILED}</code>.
   * @return the cause of a failure.
   */
  public Exception cause() { return cause; }
}
