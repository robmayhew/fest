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

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Understands the table displaying the items in a web feed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class WebFeedItemsTable extends JTable {

  private static final long serialVersionUID = 1L;

  private final I18n i18n;
  
  WebFeedItemsTable() {
    i18n = new I18n(this);
    String[] columnNames = i18n.messages("table.column.title.name", "table.column.date.name");
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    setModel(model);
  }
}