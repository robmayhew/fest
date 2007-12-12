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

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.util.HashMap;
import java.util.Map;

import org.fest.swing.core.RobotFixture;

/**
 * Understands a registry of <code>{@link RequestHandler}</code>s. Each <code>{@link RequestHandler}</code> is
 * associated to a specific request type.
 *
 * @author Alex Ruiz
 */
public class RequestDispatcher {

  private final Map<Class<? extends Request>, RequestHandler> handlers = new HashMap<Class<? extends Request>, RequestHandler>();
  private final TestServer server;
  final RobotFixture robot;

  /**
   * Creates a new </code>{@link RequestDispatcher}</code>.
   * @param server the GUI testing server in use.
   * @param robot the robot to use to simulate user input.
   */
  public RequestDispatcher(TestServer server, RobotFixture robot) {
    this.server = server;
    this.robot = robot;
    reqisterHandlers();
  }

  private void reqisterHandlers() {
    register(new PingRequestHandler(server));
  }

  private void register(RequestHandler handler) {
    Class<? extends Request> supportedType = handler.supportedType();
    if (handlers.containsKey(supportedType)) throw handlerAlreadyRegistered(handler);
    handlers.put(supportedType, handler);
  }

  private IllegalStateException handlerAlreadyRegistered(RequestHandler newHandler) {
    Class<? extends Request> supportedType = newHandler.supportedType();
    String message = concat(
        "Cannot register handler ", quote(newHandler), ". ",
        "The handler ", quote(handlers.get(supportedType)), " is already register to process requests of type ",
        quote(supportedType.getName()));
    throw new IllegalArgumentException(message);
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
    return handlers.get(request.getClass());
  }
}
