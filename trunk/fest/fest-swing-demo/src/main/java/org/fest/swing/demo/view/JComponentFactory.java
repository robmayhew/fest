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
package org.fest.swing.demo.view;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import org.jdesktop.swinghelper.layer.JXLayer;

import com.jhlabs.image.BlurFilter;

/**
 * Understands a factory of <code>{@link javax.swing.JComponent}</code>s.
 *
 * @author Alex Ruiz
 */
final class JComponentFactory {

  JButton buttonWithMnemonic(I18n i18n, String key) {
    JButton button = new JButton();
    button.setMnemonic(i18n.mnemonic(key));
    button.setText(i18n.message(key));
    return button;
  }
  
  JLabel labelWithMnemonic(I18n i18n, String key) {
    JLabel label = new JLabel();
    label.setDisplayedMnemonic(i18n.mnemonic(key));
    label.setText(i18n.message(key));
    return label;
  }
  
  <T extends JComponent> JXLayer<T> blurFilteredLayer(T content) {
    JXLayer<T> layer = new JXLayer<T>(content);
    layer.setPainter(new ImageOpPainter<T>(new BlurFilter()));
    layer.setLocked(false);
    return layer;
  }
  
  JToggleButton toggleButtonWithMnemonic(I18n i18n, String key) {
    JToggleButton button = new JToggleButton();
    button.setMnemonic(i18n.mnemonic(key));
    button.setText(i18n.message(key));
    return button;    
  }
  
  static JComponentFactory instance() {
    return SingletonHolder.INSTANCE;
  }
  
  private static class SingletonHolder {
    static final JComponentFactory INSTANCE = new JComponentFactory();
  }
  
  private JComponentFactory() {}
}
