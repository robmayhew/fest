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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Window;

import javax.swing.JDialog;

import org.jdesktop.swingx.JXBusyLabel;

import static org.fest.swing.demo.view.Swing.center;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
class SaveProgressDialog extends JDialog {

  private static final long serialVersionUID = 1L;

  private final I18n i18n;
  
  SaveProgressDialog(Window owner) {
    super(owner, DEFAULT_MODALITY_TYPE);
    i18n = new I18n(this);
    setUndecorated(true);
    //setLayout(new GridBagLayout());
    setPreferredSize(new Dimension(200, 100));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = c.gridy = 0;
    JXBusyLabel busyLabel = new JXBusyLabel();
    busyLabel.setText("Hello");
    add(busyLabel);
    busyLabel.setBusy(true);
    pack();
    center(this);
  }
}
