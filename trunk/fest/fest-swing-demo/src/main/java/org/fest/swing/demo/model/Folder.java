/*
 * Created on Mar 7, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.demo.model;

import static org.fest.util.Objects.*;
import static org.fest.util.Strings.*;

/**
 * Understands a folder containing web feeds.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Folder {

  private long id;
  private String name;

  /** Creates a new </code>{@link Folder}</code>. */
  public Folder() {}

  /**
   * Creates a new </code>{@link Folder}</code>.
   * @param name the name of the folder.
   */
  public Folder(String name) {
    name(name);
  }

  /**
   * Returns this folder's id.
   * @return this folder's id.
   */
  public long id() {
    return id;
  }

  /**
   * Updates the id of this folder.
   * @param id the new id to set.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns this folder's name.
   * @return this folder's name.
   */
  public String name() {
    return name;
  }

  /**
   * Updates this folder's name.
   * @param name the new name to set.
   */
  public void name(String name) {
    this.name = name;
  }

  /** @see java.lang.Object#hashCode() */
  @Override public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + hashCodeFor(id);
    result = prime * result + hashCodeFor(name);
    return result;
  }

  /** @see java.lang.Object#equals(java.lang.Object) */
  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Folder other = (Folder)obj;
    return areEqual(name, other.name);
  }

  /** @see java.lang.Object#toString() */
  @Override public String toString() {
    return concat(getClass().getSimpleName(), "[name=", quote(name), "]");
  }
}
