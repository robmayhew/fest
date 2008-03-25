/*
 * Created on Mar 24, 2008
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;

/**
 * Understands mappings between key modifier masks and the codes for the keys:
 * <ul>
 * <li>Alt</li>
 * <li>AltGraph</li>
 * <li>Control</li>
 * <li>Meta</li>
 * <li>Shift</li>
 * </ul>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Modifiers {

  private static final Map<Integer, Integer> mapping = new LinkedHashMap<Integer, Integer>();

  static {
    mapping.put(ALT_GRAPH_MASK, VK_ALT_GRAPH);
    mapping.put(ALT_MASK, VK_ALT);
    mapping.put(SHIFT_MASK, VK_SHIFT);
    mapping.put(CTRL_MASK, VK_CONTROL);
    mapping.put(META_MASK, VK_META);
  }

  /**
   * Returns the key codes for the given modifier masks.
   * @param modifierMasks the given modifier masks.
   * @return the key codes for the given modifier masks.
   */
  public static int[] keysFor(int modifierMasks) {
    List<Integer> keyList = new ArrayList<Integer>();
    for (Integer mask : mapping.keySet()) 
      if ((modifierMasks & mask.intValue()) != 0) keyList.add(mapping.get(mask));
    int keyCount = keyList.size();
    int[] keys = new int[keyCount];
    for (int i = 0; i < keyCount; i++) keys[i] = keyList.get(i);
    return keys;
  }
}
