/*
 * Created on Jan 24, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.util;

import org.testng.annotations.DataProvider;

/**
 * Understands a TestNG data provider of <code>null</code> and empty <code>String</code>s.
 *
 * @author Alex Ruiz
 */
public class NullAndEmptyStringProvider {

  @DataProvider(name = "nullAndEmptyStrings") 
  public static Object[][] nullAndEmptyStrings() {
    return new Object[][] { { null }, { "" } };
  }

}
