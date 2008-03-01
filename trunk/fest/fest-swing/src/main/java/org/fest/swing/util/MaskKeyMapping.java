/*
 * Created on Feb 26, 2008
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
package org.fest.swing.util;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Understands a mapping between a key modifier mask (from <code>{@link InputEvent}</code>) and a key code (from
 * <code>{@link KeyEvent}</code>.)
 * 
 * @author Alex Ruiz
 */
public final class MaskKeyMapping {

  /** Key modifier mask. */
  public final int modifierMask;

  /** Key code. */
  public final int keyCode;

  MaskKeyMapping(int modifierMask, int keyCode) {
    this.modifierMask = modifierMask;
    this.keyCode = keyCode;
  }
}
