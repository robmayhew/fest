/*
 * Created on Apr 25, 2008
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

import java.util.Date;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class WebFeedEntry {

  private long id;
  private String title;
  private String content;
  private Date date;
  
  /**
   * Creates a new </code>{@link WebFeedEntry}</code>.
   * @param title the title of this web feed entry.
   * @param content the content of this web feed entry.
   * @param date the date this web feed entry was published.
   */
  public WebFeedEntry(String title, String content, Date date) {
    this.title = title;
    this.content = content;
    this.date = date;
  }

  /**
   * Returns the id of this web feed entry.
   * @return the id of this web feed entry.
   */
  public long id() {
    return id;
  }

  /**
   * Updates the id of this web feed entry.
   * @param id the new id to set.
   */
  public void id(long id) {
    this.id = id;
  }

  /**
   * Returns the title of this web feed entry.
   * @return the title of this web feed entry.
   */
  public String title() {
    return title;
  }

  /**
   * Updates the title of this web feed entry.
   * @param title the new title to set.
   */
  public void title(String title) {
    this.title = title;
  }

  /**
   * Returns the content of this web feed entry.
   * @return the content of this web feed entry.
   */
  public String content() {
    return content;
  }

  /**
   * Updates the content of this web feed entry.
   * @param content the new content to set.
   */
  public void content(String content) {
    this.content = content;
  }

  /**
   * Returns the date this web feed entry was published.
   * @return the date this web feed entry was published.
   */
  public Date date() {
    return date;
  }

  /**
   * Updates the date this web feed entry was published.
   * @param date the new date to set.
   */
  public void date(Date date) {
    this.date = date;
  }
}
