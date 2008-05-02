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
package org.fest.swing.demo.service;

import org.fest.swing.demo.model.WebFeed;
import org.fest.swing.demo.model.WebFeedEntry;

/**
 * Understands a service that manages web feeds.
 *
 * @author Alex Ruiz
 */
public interface WebFeedService {
  
  /**
   * Saves the given web feed in the database.
   * @param webFeed the web feed to save.
   */
  void saveWebFeed(WebFeed webFeed);

  /**
   * Updates the values of the given web feeds in the database.
   * @param webFeeds the given web feeds to update.
   */
  void updateWebFeeds(WebFeed[] webFeeds);

  /**
   * Returns the entries of the given web feed from the database.
   * @param webFeed the given web feed.
   * @return the entries of the given web feed.
   */
  WebFeedEntry[] entriesOf(WebFeed webFeed);

  /**
   * Returns a web feed whose name matches the given one
   * @param name the name of to match.
   * @return the matching web feed, or <code>null</code> if no matching web feed was found.
   */
  WebFeed webFeedWithName(String name);
}
