/*
 * Created on Apr 22, 2008
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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.fest.swing.demo.model.WebFeed;

import static org.fest.swing.demo.view.WebFeedSelection.WEB_FEED_FLAVOR;
import static org.fest.util.Arrays.isEmpty;

/**
 * Understands a template for handling transfers of <code>{@link WebFeedSelection}</code>s.
 * @param <T> the type of GUI component involved in the transfer
 *
 * @author Alex Ruiz
 */
abstract class WebFeedTransferHandler<T extends JComponent> extends TransferHandler {

  private static final long serialVersionUID = 1L;
  
  private final Class<T> componentType;

  /**
   * Creates a new </code>{@link WebFeedTransferHandler}</code>.
   * @param componentType the type of GUI component involved in the transfer.
   */
  WebFeedTransferHandler(Class<T> componentType) {
    this.componentType = componentType;
  }

  /**
   * Creates a <code>{@link WebFeedSelection}</code> to use as the source for a data transfer. Returns
   * <code>null</code> if the given component does not belong to the type specified in this handler or if an array
   * of <code>{@link WebFeed}</code>s cannot be obtained.
   * @param c the component holding the data to be transferred.
   * @return a <code>WebFeedSelection</code> to be transferred, or <code>null</code> if the the given component does not
   *         belong to the type specified in this handler or if an array of <code>WebFeed</code>s cannot be obtained.
   */
  @Override protected final WebFeedSelection createTransferable(JComponent c) {
    if (!componentType.isInstance(c)) return null;
    WebFeed[] webFeeds = exportWebFeeds(componentType.cast(c));
    if (isEmpty(webFeeds)) return null;
    return new WebFeedSelection(webFeeds);
  }
  
  /**
   * Returns the <code>{@link WebFeed}</code> to be transferred.
   * @param c the component holding the data to be transferred.
   * @return an array of <code>WebFeed</code>s to be transferred.
   */
  abstract WebFeed[] exportWebFeeds(T c);

  /**
   * Returns the type of transfer actions supported by the source. In this case it returns 
   * <code>{@link TransferHandler#MOVE}</code>.
   * @return <code>MOVE</code>.
   */
  @Override public final int getSourceActions(JComponent c) {
    return MOVE;
  }
  
  /**
   * Causes a transfer to a component from a DND drop operation. The <code>Transferable</code> represents the data to
   * be imported into the component.
   * @param c the component to receive the transfer.
   * @param t the data to import.
   * @return <code>true</code> if the data was inserted into the component, <code>false</code> otherwise.
   */
  @Override public final boolean importData(JComponent c, Transferable t) {
    if (!canImport(c, t.getTransferDataFlavors())) return false;
    try {
      WebFeed[] webFeeds = (WebFeed[]) t.getTransferData(WEB_FEED_FLAVOR);
      importWebFeeds(componentType.cast(c), webFeeds);
      return true;
    } catch (Exception ignored) {
      return false;
    }
  }

  /**
   * Indicates whether a component will accept an import of the given set of data flavors prior to actually attempting
   * to import it. Only components belonging to the type specified in this handler can accept an import. In order to
   * import data the data flavor should be <code>{@link WebFeedSelection#WEB_FEED_FLAVOR}</code>.
   * @param c the component to receive the transfer; provided to enable sharing of <code>TransferHandler</code>s
   * @param flavors the data formats available.
   * @return <code>true</code> if the data can be inserted into the component, <code>false</code> otherwise.
   */
  @Override public final boolean canImport(JComponent c, DataFlavor[] flavors) {
    if (!componentType.isInstance(c)) return false;
    boolean found = false;
    for (DataFlavor flavor : flavors) {
      if (!WEB_FEED_FLAVOR.equals(flavor)) continue;
      found = true;
      break;
    }
    if (found) return importAllowed(componentType.cast(c));
    return false;
  }
  
  /**
   * Indicates whether the given component allows an import of <code>{@link WebFeed}</code>s.
   * @param c the given component.
   * @return <code>true</code> if the given component allows an import of <code>WebFeed</code>s, <code>false</code>
   *         otherwise.
   */
  boolean importAllowed(T c) {
    return true;
  }
  
  /**
   * Imports the given <code>{@link WebFeed}</code>s into the given component.
   * @param c the given component.
   * @param webFeeds the web feeds to import.
   */
  abstract void importWebFeeds(T c, WebFeed[] webFeeds);

  /**
   * Removes any data that was transferred, only if the given component belongs to the type specified in this handler.
   * @param c the component that was the source of the data.
   * @param data the data that was transferred.
   * @param action the actual action that was performed.
   */
  @Override protected final void exportDone(JComponent c, Transferable data, int action) {
    if (!componentType.isInstance(c)) return;
    WebFeedSelection selection = null;
    if (data instanceof WebFeedSelection) selection = (WebFeedSelection)data;
    cleanup(componentType.cast(c), selection, action == MOVE);
  }
  
  /**
   * Cleans up after any data transfer.
   * @param c the component that was the source of the data.
   * @param selection the web feeds that were transferred.
   * @param remove indicates if the data should be removed.
   */
  abstract void cleanup(T c, WebFeedSelection selection, boolean remove);
}
