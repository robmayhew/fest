/*
 * Created on Nov 5, 2008
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
package org.fest.swing.driver;

import java.awt.Insets;

/**
 * Understands information needed to resize a component.
 *
 * @author Alex Ruiz
 */
class ComponentResizeInfo {

  public final boolean resizable;
  public final int width;
  public final int height;
  public final Insets insets;

  ComponentResizeInfo(boolean resizable, int width, int height, Insets insets) {
    this.resizable = resizable;
    this.width = width;
    this.height = height;
    this.insets = insets;
  }
}
