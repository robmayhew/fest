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

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import static org.fest.util.Strings.concat;

/**
 * Icons used by the application.
 *
 * @author Alex Ruiz
 */
final class Icons {

  private static final String IMAGE_LOCATION = "org/fest/swing/demo/images/";

  static final Icon DIALOG_ERROR_ICON = new ImageIcon(iconURL("dialog-error.png"));
  static final Icon FOLDER_ICON = new ImageIcon(iconURL("folder.png"));
  static final Icon FOLDER_SMALL_ICON = new ImageIcon(iconURL("folder-small.png"));
  static final Icon INTERNET_FEEDS_ICON = new ImageIcon(iconURL("internet-feeds.png")); 
  
  private static URL iconURL(String imageName) {
    return Icons.class.getClassLoader().getResource(concat(IMAGE_LOCATION, imageName));
  }
  
  private Icons() {}
}
