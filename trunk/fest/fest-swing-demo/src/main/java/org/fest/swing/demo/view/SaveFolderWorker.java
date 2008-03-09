/*
 * Created on Mar 8, 2008
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

import org.fest.swing.demo.model.Folder;
import org.fest.swing.demo.service.Services;

/**
 * Understands saving a folder in a database using worker thread.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class SaveFolderWorker extends SwingWorker<Void, Void> {

  private final Folder folder;

  /**
   * Creates a new </code>{@link SaveFolderWorker}</code>.
   * @param folder the folder to save.
   */
  SaveFolderWorker(Folder folder) {
    this.folder = folder;
  }

  /** @see javax.swing.SwingWorker#doInBackground() */
  protected Void doInBackground() throws Exception {
    Services.instance().folderService().saveFolder(folder);
    return null;
  }
}
