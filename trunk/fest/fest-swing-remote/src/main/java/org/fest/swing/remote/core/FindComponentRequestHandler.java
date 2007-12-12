/*
 * Created on Dec 11, 2007
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

import static org.fest.swing.remote.core.RemoteActionFailure.failure;
import static org.fest.swing.remote.core.Response.failure;
import static org.fest.swing.remote.core.Response.success;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

import java.awt.Component;
import java.awt.Container;
import java.util.UUID;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.remote.factory.ComponentFixtureFactories;

/**
 * Understands processing of a "find component" request.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FindComponentRequestHandler extends RequestHandler {

  private final RobotFixture robot;


  /**
   * Creates a new </code>{@link FindComponentRequestHandler}</code>.
   * @param robot the robot to use to find a component.
   */
  public FindComponentRequestHandler(RobotFixture robot) {
    this.robot = robot;
  }

  /** @see org.fest.swing.remote.core.RequestHandler#doProcess(org.fest.swing.remote.core.Request) */
  @Override protected Response doProcess(Request request) {
    return process((FindComponentRequest)request);
  }

  private Response process(FindComponentRequest request) {
    if (!isEmpty(request.name())) return findByName(request);
    return null;
  }

  private Response findByName(FindComponentRequest request) {
    try {
      return success(findByName(request.rootId(), request.name(), request.type()));
    } catch (RemoteActionFailure e) {
      return failure(e);
    }
  }

  private UUID findByName(UUID rootId, String name, Class<? extends Component> type) {
    try {
      Component c = findByName(root(rootId), name, type);
      ComponentFixture<? extends Component> fixture = ComponentFixtureFactories.instance().createFixture(robot, c);
      return ComponentFixtures.instance().add(fixture);
    } catch (RuntimeException e) {
      throw failure(concat("Unable to find component with name ", quote(name), " and type ", type.getName()), e);
    }
  }

  private Container root(UUID rootId) {
    if (!ComponentFixtures.instance().containsFixture(rootId)) return null;
    ComponentFixture<? extends Component> fixture = ComponentFixtures.instance().fixtureWithId(rootId);
    Component target = fixture.target;
    if (target instanceof Container) return (Container)target;
    throw new IllegalArgumentException(concat("There is no container under the key " , rootId));
  }

  private Component findByName(Container root, String name, Class<? extends Component> type) {
    if (root == null) return robot.finder().findByName(name, type);
    return robot.finder().findByName(root, name, type);
  }

  /**
   * Indicates that this handle supports request of type <code>{@link FindComponentRequest}</code>.
   * @return <code>FindComponentRequest.class</code>.
   */
  @Override public Class<? extends Request> supportedType() {
    return FindComponentRequest.class;
  }

}
