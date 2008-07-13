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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTableDriver;

import static java.awt.Color.BLUE;
import static java.awt.Font.PLAIN;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.fixture.MouseClickInfo.leftButton;
import static org.fest.swing.fixture.TableCell.row;

/**
 * Tests for <code>{@link JTableFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test
public class JTableFixtureTest extends CommonComponentFixtureTestCase<JTable> {

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

  public void shouldCreateFixtureWithGivenComponentName() {
    new FixtureCreationByNameTemplate() {
      ComponentFixture<JTable> fixtureWithName(String name) {
        return new JTableFixture(robot(), name);
      }
    }.run();
  }

  public void shouldSelectCell() {
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

  public void shouldRequireNoSelection() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNoSelection(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNoSelection());
      }
    }.run();
  }

  public void shouldSelectCells() {
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

  public void shouldReturnSelectionContents() {
    final String content = "A Cell";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.selectionValue(target)).andReturn(content);
      }

      protected void codeToTest() {
        Object result = fixture.selectionValue();
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  public void shouldDragAtCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, cell);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(cell));
      }
    }.run();
  }

  public void shouldDropAtCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, cell);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(cell));
      }
    }.run();
  }

  public void shouldReturnPointAtCell() {
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

  public void shouldReturnCellContent() {
    final String content = "A Cell";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.value(target(), cell)).andReturn(content);
      }

      protected void codeToTest() {
        Object result = fixture.valueAt(cell);
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  public void shouldClickCellWithGivenMouseButton() {
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

  public void shouldClickCellWithGivenMouseClickInfo() {
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

  public void shouldShowJPopupMenuAtCell() {
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

  public void shouldReturnJTableHeaderFixture() {
    JTableHeaderFixture tableHeader = fixture.tableHeader();
    assertThat(tableHeader.target).isSameAs(target.getTableHeader());
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldThrowErrorIfJTableHeaderIsNull() {
    target.setTableHeader(null);
    fixture.tableHeader();
  }

  public void shouldSetCellReaderInDriver() {
    final JTableCellReader reader = createMock(JTableCellReader.class);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.cellReader(reader);
        expectLastCall().once();
      }

      protected void codeToTest() {
        fixture.cellReader(reader);
      }
    }.run();
  }

  public void shouldReturnCellFont() {
    final Font font = new Font("SansSerif", PLAIN, 8);
    final TableCell cell = row(6).column(8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.font(target, cell)).andReturn(font);
      }

      protected void codeToTest() {
        FontFixture fontFixture = fixture.fontAt(cell);
        assertThat(fontFixture.target()).isSameAs(font);
        assertThat(fontFixture.description()).contains(target.getClass().getName())
                                             .contains("property:'font' - [row=6, column=8]");
      }
    }.run();
  }

  public void shouldReturnCellBackgroundColor() {
    final Color background = BLUE;
    final TableCell cell = row(6).column(8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.background(target, cell)).andReturn(background);
      }

      protected void codeToTest() {
        ColorFixture colorFixture = fixture.backgroundAt(cell);
        assertThat(colorFixture.target()).isSameAs(background);
        assertThat(colorFixture.description()).contains(target.getClass().getName())
                                              .contains("property:'background' - [row=6, column=8]");
      }
    }.run();
  }

  public void shouldReturnCellForegroundColor() {
    final Color foreground = BLUE;
    final TableCell cell = row(6).column(8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.foreground(target, cell)).andReturn(foreground);
      }

      protected void codeToTest() {
        ColorFixture colorFixture = fixture.foregroundAt(cell);
        assertThat(colorFixture.target()).isSameAs(foreground);
        assertThat(colorFixture.description()).contains(target.getClass().getName())
                                              .contains("property:'foreground' - [row=6, column=8]");
      }
    }.run();
  }

  public void shouldRequireCellValue() {
    final TableCell cell = row(6).column(8);
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireCellValue(target, cell, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireCellValue(cell, value));
      }
    }.run();
  }

  public void shouldRequireEditableCell() {
    final TableCell cell = row(0).column(0);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEditable(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEditable(cell));
      }
    }.run();
  }

  public void shouldRequireNotEditableCell() {
    final TableCell cell = row(0).column(0);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotEditable(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotEditable(cell));
      }
    }.run();
  }

  public void shouldEnterValueInCell() {
    final TableCell cell = row(0).column(0);
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.enterValueInCell(target, cell, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.enterValue(cell, value));
      }
    }.run();
  }

  public void shouldReturnCell() {
    final TableCell cell = row(0).column(0);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.validate(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        JTableCellFixture cellFixture = fixture.cell(cell);
        assertThat(cellFixture.table()).isSameAs(fixture);
        assertThat(cellFixture.cell()).isSameAs(cell);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JTable target() { return target; }
  JTableFixture fixture() { return fixture; }
}
