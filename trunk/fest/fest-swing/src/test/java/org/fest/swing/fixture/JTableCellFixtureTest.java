/*
 * Created on Mar 2, 2008
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
package org.fest.swing.fixture;

import static java.awt.Color.BLUE;
import static java.awt.Font.PLAIN;
import static java.awt.event.KeyEvent.*;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.fixture.MouseClickInfo.leftButton;
import static org.fest.swing.fixture.TableCell.row;

import java.awt.Font;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableCellFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JTableCellFixtureTest {

  private JTableFixture table;
  private TableCell cell;
  private JTableCellFixture fixture;

  @BeforeMethod public void setUp() {
    table = createMock(JTableFixture.class);
    cell = row(8).column(6);
    fixture = new JTableCellFixture(table, cell);
  }

  @Test public void shouldSelect() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.selectCell(cell)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.select());
      }
    }.run();
  }

  @Test public void shouldClick() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.selectCell(cell)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click());
      }
    }.run();
  }

  @Test public void shouldClickUsingMouseClickInfo() {
    final MouseClickInfo mouseClickInfo = leftButton().times(2);
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.click(cell, mouseClickInfo)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click(mouseClickInfo));
      }
    }.run();
  }

  @Test public void shouldDoubleClick() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        table.click(cell, LEFT_BUTTON, 2);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClick());
      }
    }.run();
  }

  @Test public void shouldRightClick() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.click(cell, RIGHT_BUTTON)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.rightClick());
      }
    }.run();
  }

  @Test public void shouldShowPopupMenu() {
    final JPopupMenuFixture popup = createMock(JPopupMenuFixture.class);
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.showPopupMenuAt(cell)).andReturn(popup);
      }

      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenu();
        assertThat(result).isSameAs(popup);
      }
    }.run();
  }

  @Test public void shouldReturnContents() {
    final String content = "Hello";
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.valueAt(cell)).andReturn(content);
      }

      protected void codeToTest() {
        Object result = fixture.value();
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  @Test public void shouldPassIfContentIsEqualToExpected() {
    final String content = "Hello";
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.valueAt(cell)).andReturn(content);
      }

      protected void codeToTest() {
        fixture.requireValue(content);
      }
    }.run();
  }

  @Test public void shouldReturnFontForCell() {
    final FontFixture fontFixture = new FontFixture(new Font("SansSerif", PLAIN, 8));
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.fontAt(cell)).andReturn(fontFixture);
      }

      protected void codeToTest() {
        FontFixture result = fixture.font();
        assertThat(result).isSameAs(fontFixture);
      }
    }.run();
  }

  @Test public void shouldReturnBackgroundColorForCell() {
    final ColorFixture colorFixture = new ColorFixture(BLUE);
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.backgroundAt(cell)).andReturn(colorFixture);
      }

      protected void codeToTest() {
        ColorFixture result = fixture.background();
        assertThat(result).isSameAs(colorFixture);
      }
    }.run();
  }

  @Test public void shouldReturnForegroundColorForCell() {
    final ColorFixture colorFixture = new ColorFixture(BLUE);
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.foregroundAt(cell)).andReturn(colorFixture);
      }

      protected void codeToTest() {
        ColorFixture result = fixture.foreground();
        assertThat(result).isSameAs(colorFixture);
      }
    }.run();
  }

  @Test public void shouldFailIfContentIsNotEqualToExpected() {
    try {
      new EasyMockTemplate(table) {
        protected void expectations() {
          expect(table.valueAt(cell)).andReturn("Bye");
        }

        protected void codeToTest() {
          fixture.requireValue("Hello");
        }
      }.run();
    } catch (AssertionError e) {
      assertThat(e).message().contains("expected:<'Hello'> but was:<'Bye'>");
    }
  }

  @Test public void shouldDrag() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.drag(cell)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag());
      }
    }.run();
  }

  @Test public void shouldDrop() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.drop(cell)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop());
      }
    }.run();
  }

  @Test public void shouldPressAndReleaseKeys() {
    final int[] keys = { VK_A, VK_B };
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.pressAndReleaseKeys(keys)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressAndReleaseKeys(keys));
      }
    }.run();
  }

  @Test public void shouldPressKey() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.pressKey(VK_A)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressKey(VK_A));
      }
    }.run();
  }

  @Test public void shouldReleaseKey() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.releaseKey(VK_A)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.releaseKey(VK_A));
      }
    }.run();
  }

  @Test public void shouldReturnRow() {
    assertThat(fixture.row()).isEqualTo(cell.row);
  }

  @Test public void shouldReturnColumn() {
    assertThat(fixture.column()).isEqualTo(cell.column);
  }

  private void assertThatReturnsThis(JTableCellFixture result) {
    assertThat(result).isSameAs(fixture);
  }

  @Test public void shouldRequireValue() {
    final String value = "Hello";
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.requireCellValue(cell, value)).andReturn(table);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireValue(value));
      }
    }.run();
  }
}
