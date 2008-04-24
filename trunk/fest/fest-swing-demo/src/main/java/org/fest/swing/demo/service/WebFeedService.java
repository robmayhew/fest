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

/**
 * Understands a service that manages web feeds.
 *
 * @author Alex Ruiz
 */
public interface WebFeedService {
  
  /**
   * Obtains the name of the feed at the given address.
   * @param address the address of the web feed.
   * @return the name of the feed, or <code>null</code> if it could not be found.
   */
  String obtainFeedName(String address);
  
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
   * Returns the web feed whose name matches the given one.
   * @param name the name to match.
   * @return a web feed with a matching name, or <code>null</code> if it could not be found.
   */
  WebFeed webFeed(String name);
}
