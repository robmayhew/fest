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

/**
 * Understands a window where users enter data to be processed.
 *
 * @author Alex Ruiz
 */
interface InputForm {

  /**
   * Returns the selected panel containing the information to process.
   * @return the selected panel containing the information to process.
   */
  InputFormPanel selectedPanel();
  
  /**
   * Notification that the data entered by the user has been successfully saved.
   * @param saved the data entered by the user.
   */
  void saveComplete(Object saved);
}
