/*
 * Created on Mar 7, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.demo.model;

import static org.fest.util.Objects.*;
import static org.fest.util.Strings.*;

/**
 * Understands a web feed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WebFeed {

  private static final String NO_FOLDER = null;
  
  private long id;
  private String address;
  private String name;
  private Folder folder;
  
  /** Creates a new </code>{@link WebFeed}</code>. */
  public WebFeed() {}

  /**
   * Creates a new </code>{@link WebFeed}</code>.
   * @param address the address of the feed.
   * @param name the name of the feed.
   */
  public WebFeed(String address, String name) {
    this(address, name, NO_FOLDER);
  }

  /**
   * Creates a new </code>{@link WebFeed}</code>.
   * @param address the address of the feed.
   * @param name the name of the feed.
   * @param folder the folder where to store the feed.
   */
  public WebFeed(String address, String name, String folder) {
    this(address, name, new Folder(folder));
  }

  /**
   * Creates a new </code>{@link WebFeed}</code>.
   * @param address the address of the feed.
   * @param name the name of the feed.
   * @param folder the folder where to store the feed.
   */
  public WebFeed(String address, String name, Folder folder) {
    address(address);
    name(name);
    folder(folder);
  }

  /**
   * Returns this feed's id.
   * @return this feed's id.
   */
  public long id() {
    return id;
  }

  /**
   * Updates the id of this feed.
   * @param id the new id to set.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns this feed's address.
   * @return this feed's address.
   */
  public String address() {
    return address;
  }

  /**
   * Updates this feed's address.
   * @param address the new address.
   */
  public void address(String address) {
    this.address = address;
  }

  /**
   * Returns this feed's name.
   * @return this feed's name.
   */
  public String name() {
    return name;
  }

  /**
   * Updates this feed's name.
   * @param name the new name.
   */
  public void name(String name) {
    this.name = name;
  }

  /**
   * Returns the folder this feed belongs to.
   * @return the folder this feed belongs to.
   */
  public Folder folder() {
    return folder;
  }

  /**
   * Updates the folder this feed belongs to.
   * @param folder the new folder.
   */
  public void folder(Folder folder) {
    this.folder = folder;
  }

  /** @see java.lang.Object#hashCode() */
  @Override public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + hashCodeFor(address);
    result = prime * result + hashCodeFor(name);
    result = prime * result + hashCodeFor(folder);
    return result;
  }

  /** @see java.lang.Object#equals(java.lang.Object) */
  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    WebFeed other = (WebFeed) obj;
    if (!areEqual(address, other.address)) return false;
    if (!areEqual(name, other.name)) return false;
    return areEqual(folder, other.folder);
  }

  /** @see java.lang.Object#toString() */
  @Override public String toString() {
    return concat(getClass().getSimpleName(), 
        "[address=", quote(address), ", name=", quote(name), ", folder=", folder, "]");
  }
}
