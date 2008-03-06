/*
 * Created on Mar 5, 2008
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static java.awt.GridBagConstraints.NORTHWEST;

/**
 * Understands the panel where users can add a new subscription.
 *
 * @author Alex Ruiz
 */
public class AddSubscriptionPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new </code>{@link AddSubscriptionPanel}</code>.
   */
  public AddSubscriptionPanel() {
    super(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = NORTHWEST;
    c.gridx = c.gridy = 0;
    JTextField addressField = addressField();
    add(addressLabel(addressField), c);
    c.gridx++;
    add(addressField, c);
  }
  
  private JTextField addressField() {
    JTextField field = new JTextField(20);
    field.setName("address");
    return field;
  }

  private JLabel addressLabel(JTextField addressField) {
    JLabel label = new JLabel("Address:");
    label.setDisplayedMnemonic('A');
    label.setLabelFor(addressField);
    return label;
  }
}
