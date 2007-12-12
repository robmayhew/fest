/*
 * Created on Dec 6, 2007
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

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Objects.hashCodeFor;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.Component;
import java.util.UUID;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class FindComponentRequest implements Request {

  private static final long serialVersionUID = 1L;

  private final String name;
  private final Class<? extends Component> type;
  private final UUID rootId;

  /**
   * Creates a request to find a GUI component by name.
   * @param name the name of the component we are looking for.
   * @param type the type of component we are looking for.
   * @return the created request.
   */
  public static FindComponentRequest findByName(String name, Class<? extends Component> type) {
    return new FindComponentRequest(name, type);
  }

  public static FindComponentRequest findByName(UUID rootId, String name, Class<? extends Component> type) {
    return new FindComponentRequest(rootId, name, type);
  }

  private FindComponentRequest(String name, Class<? extends Component> type) {
    this(null, name, type);
  }

  private FindComponentRequest(UUID rootId, String name, Class<? extends Component> type) {
    this.rootId = rootId;
    this.name = name;
    this.type = type;
  }

  /** @see java.lang.Object#hashCode() */
  @Override public int hashCode() {
    int prime = 31;
    int result = 1;
    result = prime * result + hashCodeFor(rootId);
    result = prime * result + hashCodeFor(name);
    result = prime * result + hashCodeFor(typeName());
    return result;
  }

  /** @see java.lang.Object#equals(java.lang.Object) */
  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof FindComponentRequest)) return false;
    FindComponentRequest other = (FindComponentRequest) obj;
    if (!areEqual(rootId, other.rootId)) return true;
    if (!areEqual(name, other.name)) return true;
    return areEqual(type, other.type);
  }

  /**
   * Returns the id of the root containing the component to look for.
   * @return the id of the root containing the component to look for.
   */
  public UUID rootId() { return rootId; }

  /**
   * Returns the name of the component to look for.
   * @return the name of the component to look for.
   */
  public String name() { return name; }

  /**
   * Returns the type of the component to look for.
   * @return the type of the component to look for.
   */
  public Class<? extends Component> type() { return type; }

  /** @see java.lang.Object#toString() */
  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "rootId:", rootId, ", ",
        "name:", quote(name), ", ",
        "type:", typeName(),
        "]"
    );
  }

  private String typeName() {
    return type != null ? type.getName() : null;
  }
}
