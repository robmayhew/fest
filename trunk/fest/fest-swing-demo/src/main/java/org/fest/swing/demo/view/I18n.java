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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.fest.util.Strings.concat;

/**
 * Understands I18N messages.
 *
 * @author Alex Ruiz
 */
final class I18n {

  private static final String TEXT_KEY_SUFFIX = ".text";
  private static final String MNEMONIC_KEY_SUFFIX = ".mnemonic";
  
  private final ResourceBundle resourceBundle;
  
  /**
   * Creates a new </code>{@link I18n}</code>.
   * @param ownerType the type of the owner who is going to display the messages. 
   */
  I18n(Class<?> ownerType) {
    String bundleName = ownerType.getName().replace(".", "/");
    resourceBundle = ResourceBundle.getBundle(bundleName);
  }

  /**
   * Finds the messages stored under the given keys.
   * @param keys the given keys.
   * @return the found messages.
   * @throws MissingResourceException if no message for the given key can be found.
   */
  String[] messages(String...keys) {
    int keyCount = keys.length;
    String[] messages = new String[keyCount];
    for (int i = 0; i < keyCount; i++)
      messages[i] = message(keys[i]);
    return messages;
  }  
  
  /**
   * Finds the message stored under the given key.
   * @param key the given key.
   * @return the found message.
   * @throws MissingResourceException if no message for the given key can be found.
   */
  String message(String key) {
    return resourceBundle.getString(concat(key, TEXT_KEY_SUFFIX));
  }

  /**
   * Finds the mnemonic stored under the given key.
   * @param key the given key.
   * @return the found mnemonic.
   * @throws MissingResourceException if no mnemonic for the given key can be found.
   */
  int mnemonic(String key) {
    String value = resourceBundle.getString(concat(key, MNEMONIC_KEY_SUFFIX));
    return value.codePointAt(0);
  }
}
