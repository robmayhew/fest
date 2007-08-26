/*
 * Created on Aug 22, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import static java.awt.datatransfer.DataFlavor.stringFlavor;

/**
 * Understands importing and exporting strings. 
 * Adapted from the tutorial 
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/dnd/intro.html" target="_blank">Introduction to Drag and Drop and Data Transfer</a>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class StringTransferHandler extends TransferHandler {

  protected abstract void importString(JComponent c, String s);
  protected abstract String exportString(JComponent c);
  protected abstract void cleanup(JComponent c, boolean remove);

  @Override protected Transferable createTransferable(JComponent c) {
    return new StringSelection(exportString(c));
  }

  @Override public int getSourceActions(JComponent c) {
    return COPY_OR_MOVE;
  }

  @Override public boolean importData(JComponent c, Transferable t) {
    if (!canImport(c, t.getTransferDataFlavors())) return false; 
    try {
      String str = (String) t.getTransferData(stringFlavor);
      importString(c, str);
      return true;
    } catch (Exception ignored) {}
    return false;
  }

  @Override protected void exportDone(JComponent c, Transferable data, int action) {
    cleanup(c, action == MOVE);
  }

  @Override public boolean canImport(JComponent c, DataFlavor[] flavors) {
    for (int i = 0; i < flavors.length; i++) 
      if (stringFlavor.equals(flavors[i])) return true; 
    return false;
  }
}
