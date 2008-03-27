/*
 * Created on Mar 27, 2008
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
package org.fest.swing.keystroke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.KeyStroke;

/**
 * Understands mapping characters to <code>{@link KeyStroke}</code>s for locale en_US.
 *
 * @author Alex Ruiz
 */
public class EnUsKeyStrokeMappingProvider extends EnglishKeyStrokeMappingProviderTemplate {

  /** 
   * Returns the mapping between characters and <code>{@link KeyStroke}</code>s for locale en_US. 
   * @return the mapping between characters and <code>{@link KeyStroke}</code>s for locale en_US. 
   */
  public Collection<KeyStrokeMapping> keyStrokeMappings() {
    List<KeyStrokeMapping> mappings = new ArrayList<KeyStrokeMapping>(100);
    mappings.addAll(universalKeyStrokes());
    mappings.addAll(englishKeyStrokes());
    return mappings;
  }
}
