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
import java.util.UUID;

/**
 * Understands a result of processing a request.
 *
 * @author Alex Ruiz
 */
public final class Response implements Serializable {

  private static final long serialVersionUID = 1L;

  private final boolean success;
  private final RemoteActionFailure cause;
  private final UUID componentFixtureId;

  /**
   * Creates a new response that indicates that request processing was successful.
   * @return the created response.
   */
  public static Response success() {
    return new Response(true, null, null);
  }

  /**
   * Creates a new response that indicates that request processing was successful.
   * @param componentFixtureId the id of the component fixture used to process the request.
   * @return the created response.
   */
  public static Response success(UUID componentFixtureId) {
    return new Response(componentFixtureId);
  }

  /**
   * Creates a new response that indicates that request processing failed.
   * @return the created response.
   */
  public static Response failure() {
    return new Response(false);
  }

  /**
   * Creates a new response that indicates that request processing failed.
   * @param cause the cause of the failure.
   * @return the created response.
   */
  public static Response failure(RemoteActionFailure cause) {
    return new Response(cause);
  }

  private Response(boolean success) {
    this(success, null, null);
  }

  private Response(RemoteActionFailure failure) {
    this(false, null, failure);
  }

  private Response(UUID componentFixtureId) {
    this(true, componentFixtureId, null);
  }

  private Response(boolean success, UUID componentFixtureId, RemoteActionFailure cause) {
    this.success = success;
    this.cause = cause;
    this.componentFixtureId = componentFixtureId;
  }

  /**
   * Returns whether the request processing was successful or not.
   * @return <code>true</code> if the request processing was successful, <code>false</code> otherwise.
   */
  public final boolean successful() { return success; }

  /**
   * Returns the cause of a failure, only if <code>{@link #successful()}</code> is <code>false</code>.
   * @return the cause of a failure, if any.
   */
  public final RemoteActionFailure cause() { return cause; }

  /**
   * The unique id of the <code>{@link org.fest.swing.fixture.ComponentFixture}</code> used when processing the request.
   * @return the id of the component fixture used when processing the request.
   */
  public UUID componentFixtureId() { return componentFixtureId; }
}
