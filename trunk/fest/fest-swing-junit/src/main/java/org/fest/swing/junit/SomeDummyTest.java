package org.fest.swing.junit;

import org.junit.Test;

import org.fest.swing.GUITest;

@GUITest
public class SomeDummyTest {

  @Test public void dummy() {
    throw new AssertionError();
  }
  
  @Test public void success() {
    
  }
}
