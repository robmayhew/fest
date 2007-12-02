/*
 * Created on Dec 1, 2007
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
package org.fest.swing.remote.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;

import static org.fest.swing.remote.util.Castings.cast;

/**
 * Understands utility methods releated to Java serialization.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Serialization {
  
  /**
   * Serializes the given object to the given <code>{@link OutputStream}</code>.
   * @param o the object to serialize.
   * @param out the <code>OutputStream</code> to write the object to.
   * @throws IOException if an I/O error occurs while writing stream header.
   * @throws SecurityException if untrusted subclass illegally overrides security-sensitive methods.
   * @throws NullPointerException if <code>out</code> is <code>null</code>.
   */
  public static void serialize(Object o, OutputStream out) throws IOException {
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(o);
    oos.flush();
  }

  /**
   * Deserializes an object from the given <code>{@link InputStream}</code> and returns it casted to the given type.
   * @param <T> the generic type of the class to cast the deserialized object to.
   * @param in the given <code>InputStream</code>.
   * @param type the type to cast the deserialized object to.
   * @return the deserialized object casted to the given type.
   * @throws StreamCorruptedException if the stream header is incorrect.
   * @throws SecurityException if untrusted subclass illegally overrides security-sensitive methods.
   * @throws NullPointerException if <code>in</code> is <code>null</code>.
   * @throws ClassNotFoundException class of a serialized object cannot be found.
   * @throws InvalidClassException something is wrong with a class used by serialization.
   * @throws StreamCorruptedException control information in the stream is inconsistent.
   * @throws OptionalDataException primitive data was found in the stream instead of objects.
   * @throws IOException any of the usual Input/Output related exceptions.
   * @throws ClassCastException if the deserialized object cannot be casted to the given type.
   */
  public static <T> T deserialize(InputStream in, Class<T> type) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(in);
    return cast(ois.readObject(), type);
  }
  
  private Serialization() {}
}
