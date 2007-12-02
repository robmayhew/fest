/*
 * Created on Dec 1, 2007
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
package org.fest.swing.remote.util;

import java.util.logging.Logger;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link Castings}</code>.
 *
 * @author Yvonne Wang
 */
public class CastingTests {

  private static Logger logger = Logger.getLogger(CastingTests.class.getName());
  
  @Test public void shouldCastObjectToGivenType() {
    Object o = "Hello";
    String s = Castings.cast(o, String.class);
    assertThat(o).isSameAs(s);
  }
  
  @Test public void shouldThrowClassCastExceptionIfCastingNotPossible() {
    try {
      Castings.cast("Hello", Integer.class);
      fail(concat("Expecting a ", ClassCastException.class.getSimpleName()));
    } catch (ClassCastException expected) {
      logger.info(expected.getMessage());
    }
  }
  
  @Test public void shouldCastNullObject() {
    String s = Castings.cast(null, String.class);
    assertThat(s).isNull();
  }
}
