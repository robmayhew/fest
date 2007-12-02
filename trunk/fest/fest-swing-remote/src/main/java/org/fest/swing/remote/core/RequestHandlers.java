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
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class RequestHandlers {

  private final Map<Type, RequestHandler> handlers = new HashMap<Type, RequestHandler>();
  private final TestServer server;
  
  /**
   * Creates a new </code>{@link RequestHandlers}</code>.
   * @param server the GUI testing server in use. 
   */
  public RequestHandlers(TestServer server) {
    this.server = server;
    populate();
  }

  private void populate() {
    handlers.put(PING, new PingRequestHandler(server));
  }
  
  public RequestHandler handlerFor(Request request) {
    if (request == null) throw new NullPointerException("request should not be null");
    return handlers.get(request.type());
  }
}
