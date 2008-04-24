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
import java.awt.datatransfer.UnsupportedFlavorException;

import org.fest.swing.demo.model.WebFeed;

/**
 * Understands a <code>Transferable</code> which implements the capability required to transfer a 
 * <code>{@link WebFeed}</code>.
 *
 * @author Alex Ruiz
 */
class WebFeedSelection implements Transferable {

  static final DataFlavor WEB_FEED_FLAVOR = new DataFlavor(WebFeed.class, "Web Feed");
  static final DataFlavor flavors[] = { WEB_FEED_FLAVOR };

  private final WebFeed[] data;

  /**
   * Creates a new </code>{@link WebFeedSelection}</code>.
   * @param data the data to be transferred.
   */
  WebFeedSelection(WebFeed[] data) {
    this.data = data;
  }
  
  WebFeed[] webFeeds() { return data; }
  
  /**
   * Returns an array of <code>{@link WebFeed}</code>s to be transferred.
   * @param flavor the requested flavor for the data.
   * @return an array of <code>WebFeed</code>s to be transferred.
   * @exception UnsupportedFlavorException if the requested data flavor is not <code>WEB_FEED_FLAVOR</code>.
   */
  public WebFeed[] getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
    if (isDataFlavorSupported(flavor)) return data;
    throw new UnsupportedFlavorException(flavor);
  }

  /**
   * Returns an array of <code>DataFlavor</code> objects containing <code>{@link #WEB_FEED_FLAVOR}</code> only.
   * @return an array of data flavors in which this data can be transferred.
   */
  public DataFlavor[] getTransferDataFlavors() {
    return flavors.clone();
  }

  /**
   * Returns whether or not the specified data flavor is equal to <code>{@link #WEB_FEED_FLAVOR}</code>.
   * @param flavor the requested flavor for the data.
   * @return <code>true</code> if the given data flavor is equal to <code>FEED_FLAVOR</code>, <code>false</code>
   *         otherwise.
   */
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return WEB_FEED_FLAVOR.equals(flavor);
  }

}
