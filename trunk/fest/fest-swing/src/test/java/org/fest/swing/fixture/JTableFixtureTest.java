/*
 * Created on Jul 12, 2007
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

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.fixture.MouseClickInfo.leftButton;
import static org.fest.swing.fixture.TableCell.row;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.MouseButton;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTableDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableFixtureTest extends JPopupMenuInvokerFixtureTestCase<JTable> {

  private JTableDriver driver;
  private JTable target;
  private JTableFixture fixture;
  private TableCell cell;

  void onSetUp() {
    driver = createMock(JTableDriver.class);
    target = new JTable();
    fixture = new JTableFixture(robot(), target);
    fixture.updateDriver(driver);
    cell = row(6).column(8);
  }

  @Test public void shouldSelectCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectCell(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectCell(cell));
      }
    }.run();
  }

  @Test public void shouldSelectCells() {
    final TableCell[] cells = { cell };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectCells(target, cells);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectCells(cells));
      }
    }.run();
  }

  @Test public void shouldReturnSelectionContents() {
    final String content = "A Cell";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.selectionText(target)).andReturn(content);
      }

      protected void codeToTest() {
        String result = fixture.selectionContents();
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  @Test public void shouldDragAtCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, cell);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(cell));
      }
    }.run();
  }

  @Test public void shouldDropAtCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, cell);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(cell));
      }
    }.run();
  }

  @Test public void shouldReturnPointAtCell() {
    final Point p = new Point(6, 8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.pointAt(target(), cell)).andReturn(p);
      }

      protected void codeToTest() {
        Point result = fixture.pointAt(cell);
        assertThat(result).isSameAs(p);
      }
    }.run();
  }

  @Test public void shouldReturnCellContent() {
    final String content = "A Cell";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.text(target(), cell)).andReturn(content);
      }

      protected void codeToTest() {
        String result = fixture.contentAt(cell);
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  @Test public void shouldClickCellWithGivenMouseButton() {
    final MouseButton button = LEFT_BUTTON;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.click(target, cell, button, 1);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click(cell, button));
      }
    }.run();
  }

  @Test public void shouldClickCellWithGivenMouseClickInfo() {
    final MouseClickInfo mouseClickInfo = leftButton().times(2);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.click(target, cell, mouseClickInfo.button(), mouseClickInfo.times());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click(cell, mouseClickInfo));
      }
    }.run();
  }

  @Test public void shouldShowJPopupMenuAtCell() {
    final JPopupMenu popup = new JPopupMenu();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenuAt(target, cell)).andReturn(popup);
      }

      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(cell);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  @Test public void shouldReturnJTableHeaderFixture() {
    JTableHeaderFixture tableHeader = fixture.tableHeader();
    assertThat(tableHeader.target).isSameAs(target.getTableHeader());
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldThrowErrorIfJTableHeaderIsNull() {
    target.setTableHeader(null);
    fixture.tableHeader();
  }

  ComponentDriver driver() { return driver; }
  JTable target() { return target; }
  JPopupMenuInvokerFixture<JTable> fixture() { return fixture; }
}
