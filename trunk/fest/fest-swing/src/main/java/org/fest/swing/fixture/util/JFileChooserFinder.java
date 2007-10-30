/*
 * Created on Oct 29, 2007
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

import javax.swing.JFileChooser;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.JFileChooserFixture;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public final class JFileChooserFinder extends ComponentFinderTemplate<JFileChooser> {

  public static JFileChooserFinder find() {
    return new JFileChooserFinder();
  }

  public JFileChooserFixture using(RobotFixture robot) {
    return new JFileChooserFixture(robot, findComponentWith(robot));
  }

  @Override protected ComponentLookupException cannotFindComponent() {
    throw new ComponentLookupException("Unable to find JFileChooser");
  }

  @Override protected ComponentMatcher nameMatcher() { return null; }

  JFileChooserFinder() {
    super(JFileChooser.class);
  }
}
