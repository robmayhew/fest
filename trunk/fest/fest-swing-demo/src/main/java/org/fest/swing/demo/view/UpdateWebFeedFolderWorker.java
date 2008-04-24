/*
 * Created on Apr 23, 2008
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
package org.fest.swing.demo.view;

import javax.swing.SwingWorker;

import org.fest.swing.demo.model.WebFeed;
import org.fest.swing.demo.service.Services;

/**
 * Understands a worker thread that updates web feeds when they have been moved from one folder to another.
 *
 * @author Alex Ruiz
 */
class UpdateWebFeedFolderWorker extends SwingWorker<Void, Void> {

  private final WebFeed[] webFeeds;

  /**
   * Creates a new </code>{@link UpdateWebFeedFolderWorker}</code>.
   * @param webFeeds the web feeds to update.
   */
  UpdateWebFeedFolderWorker(WebFeed[] webFeeds) {
    this.webFeeds = webFeeds;
  }

  /** @see javax.swing.SwingWorker#doInBackground() */
  protected Void doInBackground() {
    Services.instance().webFeedService().updateWebFeeds(webFeeds);
    return null;
  }

}
