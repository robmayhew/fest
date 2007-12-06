/*
 * Created on Dec 1, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.remote.core;

import static org.fest.util.Strings.*;

/**
 * Understands handling client requests.
 * 
 * @author Alex Ruiz
 */
public abstract class RequestHandler {

  /**
   * Returns the type of request this handle supports.
   * @return the type of request this handle supports.
   */
  public abstract Class<? extends Request> supportedType();

  /**
   * Processes the given request.
   * @param request the request to process.
   * @return a response as the result of the request processing.
   * @throws IllegalArgumentException if the this handler does not support the type of the given request.
   */
  public final Response process(Request request) {
    if (!supportedType().equals(request.getClass())) throw unsupported(request);
    return doProcess(request);
  }

  private IllegalArgumentException unsupported(Request request) {
    String message = concat(
        "Handler <", this, "> does not support requests of type ", quote(request.getClass().getName()));
    throw new IllegalArgumentException(message);
  }

  protected abstract Response doProcess(Request request);

  /** @see java.lang.Object#toString() */
  @Override public String toString() {
    return getClass().getName();
  }
}
