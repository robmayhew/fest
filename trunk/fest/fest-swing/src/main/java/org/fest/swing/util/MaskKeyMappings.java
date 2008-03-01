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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;

/**
 * Understands mappings between key modifier masks and key codes for the keys:
 * <ul>
 * <li>Alt</li>
 * <li>AltGraph</li>
 * <li>Control</li>
 * <li>Meta</li>
 * <li>Shift</li>
 * </ul>
 *
 * @author Alex Ruiz 
 */
public final class MaskKeyMappings {

  private static final Map<Integer, MaskKeyMapping> MASK_KEY_MAPPINGS = new HashMap<Integer, MaskKeyMapping>();
  
  static {
    add(new MaskKeyMapping(ALT_MASK, VK_ALT));
    add(new MaskKeyMapping(ALT_DOWN_MASK, VK_ALT));
    add(new MaskKeyMapping(ALT_GRAPH_MASK, VK_ALT_GRAPH));
    add(new MaskKeyMapping(ALT_GRAPH_DOWN_MASK, VK_ALT_GRAPH));
    add(new MaskKeyMapping(CTRL_MASK, VK_CONTROL));
    add(new MaskKeyMapping(CTRL_DOWN_MASK, VK_CONTROL));
    add(new MaskKeyMapping(META_MASK, VK_META));
    add(new MaskKeyMapping(META_DOWN_MASK, VK_META));
    add(new MaskKeyMapping(SHIFT_MASK, VK_SHIFT));
    add(new MaskKeyMapping(SHIFT_DOWN_MASK, VK_SHIFT));
  }

  private static void add(MaskKeyMapping mapping) {
    MASK_KEY_MAPPINGS.put(mapping.modifierMask, mapping);
  }

  /**
   * Returns all the modifier-key mappings for the given modifiers.
   * @param modifiers the given modifiers.
   * @return all the modifier-key mappings for the given modifiers.
   */
  public static MaskKeyMapping[] mappingsFor(int modifiers) {
    List<MaskKeyMapping> mappings = new ArrayList<MaskKeyMapping>();
    for (Map.Entry<Integer, MaskKeyMapping> entry : MASK_KEY_MAPPINGS.entrySet())
      if ((modifiers & entry.getKey().intValue()) != 0) mappings.add(entry.getValue()); 
    return mappings.toArray(new MaskKeyMapping[mappings.size()]);
  }

  private MaskKeyMappings() {}
}
