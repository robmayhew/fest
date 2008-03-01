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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link MaskKeyMappings}</code>.
 *
 * @author Alex Ruiz
 */
public class MaskKeyMappingsTest {

  @Test(dataProvider = "altMasks") 
  public void shouldReturnMappingForAlt(int altMask) {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(altMask);
    assertThat(mappings).hasSize(1);
    MaskKeyMapping mapping = mappings[0];
    assertThat(mapping.modifierMask).isEqualTo(altMask);
    assertThat(mapping.keyCode).isEqualTo(VK_ALT);
  }

  @DataProvider(name = "altMasks") public Object[][] altMasks() {
    return new Object[][] { { ALT_MASK }, { ALT_DOWN_MASK } }; 
  }

  @Test(dataProvider = "altGraphMasks") 
  public void shouldReturnMappingForAltGraph(int altGraphMask) {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(altGraphMask);
    assertThat(mappings).hasSize(1);
    MaskKeyMapping mapping = mappings[0];
    assertThat(mapping.modifierMask).isEqualTo(altGraphMask);
    assertThat(mapping.keyCode).isEqualTo(VK_ALT_GRAPH);
  }

  @DataProvider(name = "altGraphMasks") public Object[][] altGraphMasks() {
    return new Object[][] { { ALT_GRAPH_MASK }, { ALT_GRAPH_DOWN_MASK } }; 
  }

  @Test(dataProvider = "controlMasks") 
  public void shouldReturnMappingForControl(int ctrlMask) {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(ctrlMask);
    assertThat(mappings).hasSize(1);
    MaskKeyMapping mapping = mappings[0];
    assertThat(mapping.modifierMask).isEqualTo(ctrlMask);
    assertThat(mapping.keyCode).isEqualTo(VK_CONTROL);
  }

  @DataProvider(name = "controlMasks") public Object[][] controlMasks() {
    return new Object[][] { { CTRL_MASK }, { CTRL_DOWN_MASK } }; 
  }

  @Test(dataProvider = "metaMasks") 
  public void shouldReturnMappingForMeta(int metaMask) {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(metaMask);
    assertThat(mappings).hasSize(1);
    MaskKeyMapping mapping = mappings[0];
    assertThat(mapping.modifierMask).isEqualTo(metaMask);
    assertThat(mapping.keyCode).isEqualTo(VK_META);
  }

  @DataProvider(name = "metaMasks") public Object[][] metaMasks() {
    return new Object[][] { { META_MASK }, { META_DOWN_MASK } }; 
  }

  @Test(dataProvider = "shiftMasks") 
  public void shouldReturnMappingForShift(int shiftMask) {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(shiftMask);
    assertThat(mappings).hasSize(1);
    MaskKeyMapping mapping = mappings[0];
    assertThat(mapping.modifierMask).isEqualTo(shiftMask);
    assertThat(mapping.keyCode).isEqualTo(VK_SHIFT);
  }

  @DataProvider(name = "shiftMasks") public Object[][] shiftMasks() {
    return new Object[][] { { SHIFT_MASK }, { SHIFT_DOWN_MASK } }; 
  }
  
  @Test public void shouldReturnNoMappingsForUnsupportedModifiers() {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(Integer.MIN_VALUE);
    assertThat(mappings).isEmpty();
  }
  
  @Test public void shouldReturnMappingsForMoreThanOneModifier() {
    MaskKeyMapping[] mappings = MaskKeyMappings.mappingsFor(CTRL_MASK | META_MASK);
    assertThat(mappings).hasSize(2);
    boolean ctrlMappingFound = false;
    boolean metaMappingFound = false;
    for (MaskKeyMapping mapping : mappings) {
      if (isMapping(mapping, CTRL_MASK, VK_CONTROL)) ctrlMappingFound = true;
      if (isMapping(mapping, META_MASK, VK_META)) metaMappingFound = true;
    }
    assertThat(ctrlMappingFound && metaMappingFound).isTrue();
  }
  
  private boolean isMapping(MaskKeyMapping mapping, int expectedModifier, int expectedKeyCode) {
    if (mapping.modifierMask != expectedModifier) return false; 
    assertThat(mapping.keyCode).isEqualTo(expectedKeyCode);
    return true;    
  }
}
