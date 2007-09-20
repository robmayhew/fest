/*
 * Created on Sep 16, 2007
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
package org.fest.assertions;

/**
 * Understands a template for assertion methods for primitive values.
 *
 * @author Yvonne Wang
 */
abstract class PrimitiveAssert {

  private String description;

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract PrimitiveAssert as(String description);

  protected PrimitiveAssert description(String description) {
    this.description = description;
    return this;
  }
  
  /**
   * Alternative to <code>{@link as}</code>, since "as" is a keyword in 
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract PrimitiveAssert describedAs(String description);

  /**
   * Returns the description of the actual <code>boolean</code> value in this assertion.
   * @return the description of the actual <code>boolean</code> value in this assertion.
   */
  public final String description() {
    return description;
  }

}
