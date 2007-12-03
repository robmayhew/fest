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

import java.util.HashMap;
import java.util.Map;

import org.fest.swing.remote.core.Request.Type;

import static org.fest.swing.remote.core.Request.Type.PING;

/**
 * Understands a registry of <code>{@link RequestHandler}</code>s. Each <code>{@link RequestHandler}</code> is 
 * associated to a specific request <code>{@link Request.Type type}</code>.
 *
 * @author Alex Ruiz
 */
public class RequestDispatcher {

  private final Map<Type, RequestHandler> handlers = new HashMap<Type, RequestHandler>();
  private final TestServer server;
  
  /**
   * Creates a new </code>{@link RequestDispatcher}</code>.
   * @param server the GUI testing server in use. 
   */
  public RequestDispatcher(TestServer server) {
    this.server = server;
    populate();
  }

  private void populate() {
    handlers.put(PING, new PingRequestHandler(server));
  }
  
  /**
   * Delegates to a <code>{@link RequestHandler}</code> the processing of the given request.
   * @param request the request to process.
   * @return the result of the process.
   * @throws IllegalArgumentException if the request is <code>null</code>.
   */
  public Response dispatch(Request request) {
    return handlerFor(request).process(request);
  }

  RequestHandler handlerFor(Request request) {
    if (request == null) throw new IllegalArgumentException("request should not be null");
    return handlers.get(request.type());
  }
}
