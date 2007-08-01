/*
 * Created on Jul 30, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture.util;

import java.awt.Dialog;
import java.awt.Frame;

/**
 * Understands lookup of <code>{@link Frame}</code>s and <code>{@link Dialog}</code>s.
 *
 * @author Alex Ruiz
 */
public final class WindowFinder {
 
  private WindowFinder() {}

  public static FrameFinder findFrame(String frameName) {
    return new FrameFinder(frameName);
  }

  public static FrameFinder findFrame(Class<? extends Frame> frameType) {
    return new FrameFinder(frameType);
  }

  public static DialogFinder findDialog(String dialogName) {
    return new DialogFinder(dialogName);
  }

  public static DialogFinder findFrame(Class<? extends Dialog> dialogType) {
    return new DialogFinder(dialogType);
  }
}
