/*
 * Created on May 21, 2007
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
package org.fest.swing.fixture;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import abbot.tester.JTreeLocation;
import abbot.tester.JTreeTester;

import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JTree}</code> and output verification.
 * 
 * @author Keith Coughtrey
 */
public class JTreeFixture extends AbstractComponentFixture<JTree> {

  /**
   * Creates a new </code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTree</code>.
   * @param treeName the name of the tree to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public JTreeFixture(RobotFixture robot, String treeName) {
    super(robot, treeName, JTree.class);
  }

  /**
   * Creates a new </code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on the given tree.
   * @param target the target tree.
   */
  public JTreeFixture(RobotFixture robot, JTree target) {
    super(robot, target);
  }

  /**
   * Simulates a user selecting the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   */
  public JTreeFixture selectRow(int row) {
    treeTester().actionSelectRow(target, new JTreeLocation(row));
    return this;
  }

  /**
   * Simulates a user toggling the open/closed state of the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   */
  public JTreeFixture toggleRow(int row) {
    treeTester().actionToggleRow(target, new JTreeLocation(row));
    return this;
  }

  /**
   * Select the given path, expanding parent nodes if necessary. TreePath must consist of usable String representations
   * that can be used in later comparisons. The default &ltclassname&gt;@&lt;hashcode&gt; returned by
   * {@link Object#toString()} is not usable; if that is all that is available, refer to the row number instead.
   * @param treePath A path comprising an array of Strings that match the toString()'s of the path nodes
   * @return this fixture.
   */
  public JTreeFixture selectPath(TreePath treePath) {
    treeTester().actionSelectPath(target, treePath);
    return this;
  }

  private JTreeTester treeTester() {
    return testerCastedTo(JTreeTester.class);
  }

  /** {@inheritDoc} */
  public final JTreeFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final JTreeFixture focus() {
    doFocus();
    return this;
  }

  /** {@inheritDoc} */
  public final JTreeFixture requireVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JTreeFixture requireNotVisible() {
    assertIsNotVisible();
    return this;
  }

}