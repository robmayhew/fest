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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Objects.*;

/**
 * Tests for <code>{@link Serialization}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class SerializationTest {

  @Test public void shouldSerializeAndDeserializeObject() throws Exception {
    Person person = new Person("Frodo", "Baggins", 15);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Serialization.serialize(person, out);
    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    Person deserialized = Serialization.deserialize(in, Person.class);
    assertThat(deserialized).isNotNull().isEqualTo(person).isNotSameAs(person);
  }

  private static class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(String firstName, String lastName, int age) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
    }

    @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + age;
      result = prime * result + hashCodeFor(firstName);
      result = prime * result + hashCodeFor(lastName);
      return result;
    }

    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Person)) return false;
      Person other = (Person) obj;
      if (!areEqual(firstName, other.firstName)) return false;
      if (!areEqual(lastName, other.lastName)) return false;
      return age == other.age;
    }

    String firstName() { return firstName; }
    String lastName() { return lastName; }
    int age() { return age; }
  }
}
