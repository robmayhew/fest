/*
 * Created on Mar 9, 2008
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
package org.fest.swing.demo.view;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import org.fest.swing.demo.model.WebFeedEntry;

import static org.fest.util.Arrays.array;

/**
 * Understands the table displaying the items in a web feed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class WebFeedItemsTable extends JXTable {

  private static final long serialVersionUID = 1L;

  private static final String COLUMN_DATE_NAME = "table.column.date.name";
  private static final String COLUMN_TITLE_NAME = "table.column.title.name";

  private final DefaultTableModel model;

  WebFeedItemsTable() {
    String[] columnNames = i18n().messages(COLUMN_TITLE_NAME, COLUMN_DATE_NAME);
    setAutoResizeMode(AUTO_RESIZE_OFF);
    model = new DefaultTableModel(columnNames, 0);
    setModel(model);
    setName("webFeedItems");
    setEditable(false);
  }

  void resizeColumns(Dimension availableSize) {
    updatePreferredColumnWidths(availableSize, 0.8, 0.2);
  }
  
  private void updatePreferredColumnWidths(Dimension availableSize, double... percentages) {
    double total = 0;
    for (int i = 0; i < getColumnModel().getColumnCount(); i++) total += percentages[i];
    for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
      TableColumn column = getColumnModel().getColumn(i);
      column.setPreferredWidth((int) (availableSize.width * (percentages[i] / total)));
    }
  }
  
  private static I18n i18n() {
    return I18nSingletonHolder.INSTANCE;
  }
  
  private static class I18nSingletonHolder {
    static final I18n INSTANCE = new I18n(WebFeedItemsTable.class);
  }

  void removeAllRows() {
    model.setRowCount(0);
  }
  
  void display(WebFeedEntry[] entries) {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    for (WebFeedEntry entry : entries) model.addRow(rowFrom(entry, dateFormat));
  }
  
  private Object[] rowFrom(WebFeedEntry entry, DateFormat dateFormat) {
    String formattedDate = dateFormat.format(entry.date());
    Object[] row = array(entry.title(), formattedDate);
    return row;
  }
}
