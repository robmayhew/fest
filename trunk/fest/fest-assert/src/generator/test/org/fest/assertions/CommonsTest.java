/*
 * Created on Mar 8, 2007
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

import static java.io.File.separator;

import org.fest.assertions.Commons;



import static org.fest.assertions.Assertions.assertThat;


import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link Commons}</code>.
 *
 * @author Alex Ruiz
 */
public class CommonsTest {

  @Test public void shouldReturnPathFromGivenPackageName() {
    String expected = "org" + separator + "fest" + separator + "assertions" + separator;
    assertThat(Commons.packageNameAsPath("org.fest.assertions")).isEqualTo(expected);
  }
  
  @Test public void shouldReturnPathFromPackageOfGivenClass() {
    Class<?> c = getClass();
    String expected = "org" + separator + "fest" + separator + "assertions" + separator;
    assertThat(Commons.packageNameAsPathFrom(c)).isEqualTo(expected);
  }
}
