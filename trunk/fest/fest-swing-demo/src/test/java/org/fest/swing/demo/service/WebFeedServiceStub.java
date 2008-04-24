/*
 * Created on Apr 20, 2008
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

import java.util.HashMap;
import java.util.Map;

import org.fest.swing.demo.model.WebFeed;

public final class WebFeedServiceStub implements WebFeedService {
  
  private final Map<String, WebFeed> feeds = new HashMap<String, WebFeed>();
  
  public String obtainFeedName(String address) {
    return null;
  }

  public void saveWebFeed(WebFeed webFeed) {
    try {
      store(webFeed);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void updateWebFeeds(WebFeed[] webFeeds) {
    for (WebFeed feed : webFeeds) store(feed);
  }
  
  private WebFeed store(WebFeed webFeed) {
    return feeds.put(webFeed.name(), webFeed);
  }

  public WebFeed webFeed(String name) {
    return feeds.get(name);
  }
}