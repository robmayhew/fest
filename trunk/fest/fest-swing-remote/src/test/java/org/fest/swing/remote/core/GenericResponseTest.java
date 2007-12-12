/*
 * Created on Dec 10, 2007
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.remote.testing.SerializableAssert.serializeAndDeserialize;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link GenericResponse}</code>.
 *
 * @author Alex Ruiz
 */
public class GenericResponseTest {

  @Test public void shouldBeSerializableWhenSuccessful() throws Exception {
    Response original = Response.success();
    Response deserialized = serializeAndDeserialize(original);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.successful()).isTrue();
  }

  @Test public void shouldBeSerializableWhenFailed() throws Exception {
    Response original = Response.failure(cause());
    Response deserialized = serializeAndDeserialize(original);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.successful()).isFalse();
    RemoteActionFailure cause = deserialized.cause();
    assertThat(cause).isNotNull();
    assertThat(cause.getStackTrace()).isNotEmpty();
  }

  private RemoteActionFailure cause() {
    try {
      throw new RemoteActionFailure("Failing on purpose");
    } catch (RemoteActionFailure e) {
      return e;
    }
  }
}
