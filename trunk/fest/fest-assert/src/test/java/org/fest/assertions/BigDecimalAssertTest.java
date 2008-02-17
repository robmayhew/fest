/*
 * Created on Jan 10, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.testng.Assert.*;

import java.math.BigDecimal;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BigDecimalAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BigDecimalAssertTest {

  @Test public void shouldSetDescription() {
    BigDecimalAssert assertion = new BigDecimalAssert(eight());
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    BigDecimalAssert assertion = new BigDecimalAssert(eight());
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNullBigDecimalCondition extends Condition<BigDecimal> {
    @Override public boolean matches(BigDecimal o) {
      return o != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new BigDecimalAssert(eight()).satisfies(new NotNullBigDecimalCondition());
  }

  @Test public void shouldThrowErrorIfConditionIsNull() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).satisfies(new NotNullBigDecimalCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").satisfies(new NotNullBigDecimalCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<non-null object> but was:<null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).satisfies(new NotNullBigDecimalCondition().as("non-null object"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<non-null object> but was:<null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").satisfies(new NotNullBigDecimalCondition().as("non-null object"));
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new BigDecimalAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<8> should be null").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <8> should be null").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new BigDecimalAssert(eight()).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfValuesAreSame() {
    BigDecimal eight = eight();
    new BigDecimalAssert(eight).isSameAs(eight);
  }

  @Test public void shouldFailIfValuesAreNotSameAndExpectingSame() {
    expectAssertionError("expected same instance but found:<8> and:<8>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isSameAs(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotSameAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<8> and:<8>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isSameAs(eight());
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotSame() {
    new BigDecimalAssert(eight()).isNotSameAs(eight());
  }

  @Test public void shouldFailIfValuesAreSameAndExpectingNotSame() {
    expectAssertionError("given objects are same:<8>").on(new CodeToTest() {
      public void run() {
        BigDecimal eight = eight();
        new BigDecimalAssert(eight).isNotSameAs(eight);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreSameAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<8>").on(new CodeToTest() {
      public void run() {
        BigDecimal eight = eight();
        new BigDecimalAssert(eight).as("A Test").isNotSameAs(eight);
      }
    });
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new BigDecimalAssert(eight()).isEqualTo(eight());
  }

  @Test public void shouldFailIfValuesAreNotEqual() {
    expectAssertionError("expected:<9> but was:<8>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isEqualTo(nine());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqual() {
    expectAssertionError("[A Test] expected:<9> but was:<8>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isEqualTo(new BigDecimal(9));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new BigDecimalAssert(eight()).isNotEqualTo(nine());
  }

  @Test public void shouldFailIfValuesAreEqual() {
    expectAssertionError("actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isNotEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqual() {
    expectAssertionError("[A Test] actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isNotEqualTo(eight());
      }
    });
  }

  private BigDecimal eight() {
    return new BigDecimal(8.0);
  }

  private BigDecimal nine() {
    return new BigDecimal(9.0);
  }
}
