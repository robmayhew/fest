/*
 * Created on Aug 14, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.edt;

/**
 * Understands the base class for actions that are executed in the event dispatch thread.
 * 
 * @author Alex Ruiz
 */
abstract class GuiAction implements Runnable {

  private boolean executedInEDT;
  private Throwable catchedException;
  
  final Throwable catchedException() { return catchedException; }
  
  final void catchedException(Throwable catched) {
    catchedException = catched;
  }

  final synchronized void executedInEDT() {
    executedInEDT = true;
  }
  
  final synchronized boolean wasExecutedInEDT() {
    return executedInEDT;
  }
}