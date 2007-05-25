/*
 * Created on May 24, 2007
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
package org.fest.swing;

import static org.fest.util.Strings.concat;

/**
 * Understands a lock that each GUI test should acquire before being executed to prevent blocking GUI tests.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ScreenLock {

  private boolean locked;
  private Object owner;
  
  public synchronized boolean acquire(Object owner) {
    while (locked) acquire(owner);
    locked = true;
    this.owner = owner;
    return true;
  }
  
  public synchronized void release(Object owner) {
    if (!locked) throw new RuntimeException("No lock to release");
    if (this.owner != owner) throw new RuntimeException(concat(owner, " is not the lock owner"));
    locked = false;
    this.owner = null;
  }
  
  public static ScreenLock instance() { return ScreenLockHolder.instance; }
  
  private static class ScreenLockHolder {
    static ScreenLock instance = new ScreenLock();
  }
  
  private ScreenLock() {}
}
