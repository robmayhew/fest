/*
 * Created on Apr 12, 2008
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
package org.fest.swing.driver;


/**
 * Understands the base implementation of all default readers in this package.
 *
 * @author Alex Ruiz
 */
public abstract class BaseValueReader {

  private final CellRendererComponentReader cellRendererComponentReader;

  /**
   * Creates a new </code>{@link BaseValueReader}</code> that uses a
   * <code>{@link BasicCellRendererComponentReader}</code> to read the value from a cell renderer component.
   */
  public BaseValueReader() {
    this(new BasicCellRendererComponentReader());
  }
  
  /**
   * Creates a new </code>{@link BaseValueReader}</code>.
   * @param cellRendererComponentReader reads the value from a cell renderer component.
   */
  public BaseValueReader(CellRendererComponentReader cellRendererComponentReader) {
    this.cellRendererComponentReader = cellRendererComponentReader;
  }
  
  /**
   * Returns the <code>{@link CellRendererComponentReader}</code> used to read the value from a cell renderer component.
   * @return the <code>CellRendererComponentReader</code> used to read the value from a cell renderer component.
   */
  protected final CellRendererComponentReader cellRendererComponentReader() {
    return cellRendererComponentReader;
  }
}
