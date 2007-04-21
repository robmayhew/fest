/*
 * Created on Jan 10, 2007
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
package org.fest.swing.assertions;

import org.fest.swing.assertions.ObjectAssert;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link ObjectAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class ObjectAssertTest {

  private static class Person {
    final String name;
    final int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override public int hashCode() {
      final int PRIME = 31;
      int result = 1;
      result = PRIME * result + age;
      result = PRIME * result + ((name == null) ? 0 : name.hashCode());
      return result;
    }

    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      final Person other = (Person) obj;
      if (age != other.age) return false;
      if (name == null) {
        if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      return true;
    }
  }
  
  @Test public void shouldPassIfObjectIsNull() {
    new ObjectAssert(null).isNull();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfObjectIsNotNull() {
    new ObjectAssert("").isNull();
  }

  @Test public void shouldPassIfObjectIsNotNull() {
    new ObjectAssert("").isNotNull();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfObjectIsNull() {
    new ObjectAssert(null).isNotNull();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfObjectIsNotInstanceOfExpectedClass() {
    new ObjectAssert(yoda()).isInstanceOf(String.class);
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfObjectIsNullAndNotInstanceOfExpectedClass() {
    new ObjectAssert(null).isInstanceOf(String.class);
  }
  
  @Test public void shouldSucceedIfObjectIsInstanceOfExpectedClass() {
    new ObjectAssert(yoda()).isInstanceOf(Person.class);   
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfObjectsAreNotSame() {
    new ObjectAssert(yoda()).isSameAs("Yoda");
  }

  @Test public void shouldSucceedIfObjectsAreSame() {
    Person yoda = yoda();
    new ObjectAssert(yoda).isSameAs(yoda);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfObjectsAreNotEqual() {
    new ObjectAssert(yoda()).isEqualTo("Yoda");
  }

  @Test public void shouldSucceedIfObjectsAreEqual() {
    new ObjectAssert(yoda()).isEqualTo(yoda());
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfObjectsAreEqual() {
    new ObjectAssert(yoda()).isNotEqualTo(yoda());
  }

  @Test public void shouldSucceedIfObjectsAreNotEqual() {
    new ObjectAssert(yoda()).isNotEqualTo(new Person("Alex", 31));
  }

  private Person yoda() {
    return new Person("Yoda", 600);
  }
}
