/*
 * Created on Dec 7, 2007
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
package org.fest.swing.remote.core;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link RemoteActionFailure}</code>.
 *
 * @author Alex Ruiz
 */
public class RemoteActionFailureTest {

  @Test public void shouldReturnSameRemoteActionFailure() {
    RemoteActionFailure original = new RemoteActionFailure("Testing");
    RemoteActionFailure failure = RemoteActionFailure.failure("Some message", original);
    assertThat(failure).isSameAs(original);
  }

  @Test public void shouldCreateRemoteActionFailureHavingGivenExceptionAsCause() {
    Exception original = new Exception("Testing");
    RemoteActionFailure failure = RemoteActionFailure.failure("Some message", original);
    assertThat(failure.getCause()).isSameAs(original);
    assertThat(failure.getMessage()).isEqualTo("Some message");
  }
}
