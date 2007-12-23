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
package org.fest.swing.core;

import static org.fest.util.Strings.concat;

import org.fest.swing.exception.ScreenLockException;

/**
 * Understands a lock that each GUI test should acquire before being executed, to guarantee sequential execution of
 * GUI tests and to prevent GUI tests from blocking each other.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ScreenLock {

  private boolean locked;
  private Object owner;
  
  /**
   * Acquires the lock.
   * @param owner the new owner of the lock.
   */
  public synchronized void acquire(Object owner) {
    while (locked) {
      try {
        wait();
      } catch (InterruptedException e) {
        break;
      }
      acquire(owner);
      notifyAll();
    }
    locked = true;
    this.owner = owner;
  }
  
  /**
   * Releases the lock.
   * @param owner the current owner of the lock.
   * @throws ScreenLockException if the lock has not been previously acquired.
   * @throws ScreenLockException if the given owner is not the same as the current owner of the lock. 
   */
  public synchronized void release(Object owner) {
    if (!locked) throw new ScreenLockException("No lock to release");
    if (this.owner != owner) throw new ScreenLockException(concat(owner, " is not the lock owner"));
    locked = false;
    this.owner = null;
  }
  
  /**
   * Returns the singleton instance of this class. 
   * @return the singleton instance of this class. 
   */
  public static ScreenLock instance() { return ScreenLockHolder.instance; }
  
  private static class ScreenLockHolder {
    static ScreenLock instance = new ScreenLock();
  }
  
  private ScreenLock() {}
}
