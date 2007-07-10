/*
 * Created on Jul 9, 2007
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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JFileChooserFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JFileChooserFixtureTest extends ComponentFixtureTestCase<JFileChooser> {

  private JFileChooserFixture fixture;

  @Test public void shouldFindCancelButton() {
    JButtonFixture cancelButton = fixture.cancelButton();
    assertThat(cancelButton.target).isNotNull();
    cancelButton.requireText(UIManager.getString("FileChooser.cancelButtonText"));
  }
  
  @Test(dependsOnMethods = "shouldFindCancelButton")
  public void shouldCancelFileSelection() throws InterruptedException {
    JButton cancelButton = fixture.cancelButton().target;
    ComponentEvents events = ComponentEvents.attachTo(cancelButton);
    fixture.cancel();
    assertThat(events.clicked()).isTrue();
  }
  
  protected JFileChooser createTarget() {
    JFileChooser target = new JFileChooser();
    target.setName("target");
    return target;
  }

  protected ComponentFixture<JFileChooser> createFixture() {
    fixture = new JFileChooserFixture(robot(), "target");
    return fixture;
  }
}
