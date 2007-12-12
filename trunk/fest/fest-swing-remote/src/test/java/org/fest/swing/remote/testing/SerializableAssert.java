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
package org.fest.swing.remote.testing;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.fest.assertions.AssertExtension;

/**
 * Understands assertion methods related to serializable objects.
 *
 * @author Yvonne Wang
 */
public class SerializableAssert implements AssertExtension {

  private final Serializable object;

  /**
   * Creates a new </code>{@link SerializableAssert}</code>.
   * @param object the object to verify.
   */
  public SerializableAssert(Serializable object) {
    this.object = object;
  }

  public SerializableAssert isSerializable() throws Exception {
    Object deserialized = serializeAndDeserialize(object);
    assertThat(deserialized).isEqualTo(object).isNotSameAs(object);
    return this;
  }

  @SuppressWarnings("unchecked")
  public static <T> T serializeAndDeserialize(T target) throws Exception {
    Object deserialized = deserialized(serialize(target));
    return (T)deserialized;
  }

  private static byte[] serialize(Object o) throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(o);
    oos.flush();
    return out.toByteArray();
  }

  private static Object deserialized(byte[] serialized) throws Exception {
    ByteArrayInputStream in = new ByteArrayInputStream(serialized);
    ObjectInputStream ois = new ObjectInputStream(in);
    Object deserialized = ois.readObject();
    return deserialized;
  }
}
