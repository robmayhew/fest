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

import java.awt.Window;

/**
 * Understands a window that shows progress when data is saved to the database.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class SaveProgressWindow extends ProgressWindow {

  private static final long serialVersionUID = 1L;

  private static final String LABEL_SAVING_KEY = "label.saving";

  SaveProgressWindow(Window owner) {
    super(owner, i18n().message(LABEL_SAVING_KEY));
  }

  void save(InputForm inputForm) {
    setVisible(true);
    InputFormPanel source = inputForm.selectedPanel();
    source.save(inputForm, this);
  }

  private static I18n i18n() {
    return I18nSingletonHolder.INSTANCE;
  }
  
  private static class I18nSingletonHolder {
    static final I18n INSTANCE = new I18n(SaveProgressWindow.class);
  }
}
