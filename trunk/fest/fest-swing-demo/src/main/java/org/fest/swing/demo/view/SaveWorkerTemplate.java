/*
 * Created on Mar 9, 2008
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

import java.awt.Window;

import javax.swing.SwingWorker;

/**
 * Understands a template for saving data in the database using a worker thread.
 * @param <T> the type of data to save.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class SaveWorkerTemplate<T> extends SwingWorker<Void, Void> {

  private final InputForm inputForm;
  private final Window progressWindow;

  final T target;

  /**
   * Creates a new </code>{@link SaveWorkerTemplate}</code>.
   * @param target the data to save.
   * @param inputForm form to be notified when saving the data to the database is complete. 
   * @param progressWindow the window showing progress made by this worker.
   */
  SaveWorkerTemplate(T target, InputForm inputForm, Window progressWindow) {
    this.target = target;
    this.inputForm = inputForm;
    this.progressWindow = progressWindow;
  }

  /** @see javax.swing.SwingWorker#done() */
  @Override protected void done() {
    if (progressWindow != null) progressWindow.setVisible(false);
    inputForm.saveComplete(target);
  }
}
