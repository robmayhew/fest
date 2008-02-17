/*
 * Created on Dec 23, 2007
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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.fest.assertions.ThrowableAssert.CauseHierarchyAssert;
import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ThrowableAssert}</code>.
 * 
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class ThrowableAssertTest {

  @Test public void shouldSetDescription() {
    ThrowableAssert assertion = new ThrowableAssert(new Exception());
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ThrowableAssert assertion = new ThrowableAssert(new Exception());
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNullThrowable extends Condition<Throwable> {
    public NotNullThrowable() {}

    public NotNullThrowable(String description) {
      super(description);
    }

    @Override public boolean matches(Throwable t) {
      return t != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ThrowableAssert(new Exception()).satisfies(new NotNullThrowable());
  }

  @Test public void shouldThrowErrorIfConditionIsNull() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<null> should satisfy condition").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).satisfies(new NotNullThrowable());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").satisfies(new NotNullThrowable());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<null> should satisfy condition:<non-null>").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).satisfies(new NotNullThrowable("non-null"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[Test] actual value:<null> should satisfy condition:<non-null>").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("Test").satisfies(new NotNullThrowable("non-null"));
      }
    });
  }

  @Test public void shouldPassIfActualMessageIsEqualToGivenOne() {
    new ThrowableAssert(new Exception("An exception")).hasMessage("An exception");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingMessage() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).hasMessage("");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingMessage() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").hasMessage("");
      }
    });
  }

  @Test public void shouldFailIfActualMessageIsNotEqualToExpectedAndExpectingEqual() {
    expectAssertionError("expected:<'Hi'> but was:<'An exception'>").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception("An exception")).hasMessage("Hi");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualMessageIsNotEqualToExpectedAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<'Hi'> but was:<'An exception'>").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception("An exception")).as("A Test").hasMessage("Hi");
      }
    });
  }

  @Test public void shouldReturnStringAssertWithActualMessage() {
    StringAssert message = new ThrowableAssert(new Exception("Hi")).message();
    message.isEqualTo("Hi");
  }

  @Test public void shouldFailIfActualIsNullWhenReturningMessage() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).message();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenReturningMessage() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").message();
      }
    });
  }

  @Test public void shouldReturnCauseInThrowableAssert() {
    Exception cause = new Exception();
    ThrowableAssert root = new ThrowableAssert(new Exception(cause));
    assertSame(root.cause().actual, cause);
  }

  @Test public void shouldFailIfActualIsNullWhenGettingCause() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).cause();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenGettingCause() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").cause();
      }
    });
  }

  @Test public void shouldPassIfThrowableHasNoCause() {
    new ThrowableAssert(new Exception()).hasNoCause();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingHasNoCause() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).hasNoCause();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingHasNoCause() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").hasNoCause();
      }
    });
  }

  @Test public void shouldFailIfThrowableHasCauseAndExpectingNoCause() {
    expectAssertionError("expected exception without cause, but cause was:<java.io.IOException>").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception(new IOException())).hasNoCause();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfThrowableHasCauseAndExpectingNoCause() {
    expectAssertionError("[A Test] expected exception without cause, but cause was:<java.io.IOException>").on(
        new CodeToTest() {
          public void run() {
            Exception e = new Exception(new IOException());
            new ThrowableAssert(e).as("A Test").hasNoCause();
          }
        });
  }

  @Test public void shouldPassIfThrowableIsInstanceOfExpectedClass() {
    new ThrowableAssert(new IllegalStateException()).isInstanceOf(Exception.class).isInstanceOf(Throwable.class);
  }

  @Test public void shouldThrowErrorIfGivenClassIsNullWhenCheckingIfInstanceOf() {
    shouldThrowErrorIfTypeIsNullWhenCheckingIfInstanceOf(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).isInstanceOf(null);
      }
    });
  }

  @Test public void shouldFailIfActualIsNotInstanceOfExpectedClass() {
    expectAssertionError(
        "expected instance of:<java.lang.NullPointerException> but was instance of:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).isInstanceOf(NullPointerException.class);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotInstanceOfExpectedClass() {
    expectAssertionError(
        "[A Test] expected instance of:<java.lang.NullPointerException> but was instance of:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).as("A Test").isInstanceOf(NullPointerException.class);
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIsInstanceOfExpectedClass() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).isInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIsInstanceOfExpectedClass() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").isInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test public void shouldPassIfThrowableIsExactlyInstanceOfExpectedClass() {
    new ThrowableAssert(new IllegalStateException()).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void shouldThrowErrorIfGivenClassIsNullWhenCheckingIfExactlyInstanceOf() {
    shouldThrowErrorIfTypeIsNullWhenCheckingIfInstanceOf(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).isExactlyInstanceOf(null);
      }
    });
  }

  @Test public void shouldFailIfActualIsInstanceOfExpectedClassButNotExactly() {
    expectAssertionError(
        "expected exactly the same type:<java.lang.Exception> but was:<java.lang.NullPointerException>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new NullPointerException()).isExactlyInstanceOf(Exception.class);
          }
        });
  }

  @Test public void shouldFailIfActualIsNotExactlyInstanceOfExpectedClass() {
    expectAssertionError(
        "expected exactly the same type:<java.lang.NullPointerException> but was:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).isExactlyInstanceOf(NullPointerException.class);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotExactlyInstanceOfExpectedClass() {
    expectAssertionError(
        "[A Test] expected exactly the same type:<java.lang.NullPointerException> but was:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).as("A Test").isExactlyInstanceOf(NullPointerException.class);
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIsExactlyInstanceOfExpectedClass() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).isExactlyInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIsExactlyInstanceOfExpectedClass() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").isExactlyInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test public void shouldPassIfThrowablesAreSame() {
    Exception e = new Exception();
    new ThrowableAssert(e).isSameAs(e);
  }

  @Test public void shouldFailIfThrowablesAreNotSameAndExpectingSame() {
    expectAssertionError(
        "expected same instance but found:<java.lang.Exception> and:<java.lang.IllegalArgumentException>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).isSameAs(new IllegalArgumentException());
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfThrowablesAreNotSameAndExpectingSame() {
    expectAssertionError(
        "[A Test] expected same instance but found:<java.lang.Exception> and:<java.lang.IllegalArgumentException>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).as("A Test").isSameAs(new IllegalArgumentException());
          }
        });
  }

  @Test public void shouldPassIfThrowablesAreNotSame() {
    new ThrowableAssert(new Exception()).isNotSameAs(new Exception());
  }

  @Test public void shouldFailIfThrowablesAreSameAndExpectingNotSame() {
    expectAssertionError("given objects are same:<java.lang.Exception>").on(new CodeToTest() {
      public void run() {
        Exception e = new Exception();
        new ThrowableAssert(e).isNotSameAs(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfThrowablesAreSameAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<java.lang.Exception>").on(new CodeToTest() {
      public void run() {
        Exception e = new Exception();
        new ThrowableAssert(e).as("A Test").isNotSameAs(e);
      }
    });
  }

  @Test public void shouldPassIfThrowablesAreEqual() {
    Exception e = new Exception();
    new ThrowableAssert(e).isEqualTo(e);
  }

  @Test public void shouldFailIfActualsAreNotEqual() {
    expectAssertionError("expected:<java.lang.NullPointerException> but was:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).isEqualTo(new NullPointerException());
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualsAreNotEqual() {
    expectAssertionError("[A Test] expected:<java.lang.NullPointerException> but was:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).as("A Test").isEqualTo(new NullPointerException());
          }
        });
  }

  @Test public void shouldPassIfThrowablesAreNotEqual() {
    new ThrowableAssert(new Exception()).isNotEqualTo(new NullPointerException());
  }

  @Test public void shouldFailIfActualsAreEqual() {
    expectAssertionError("actual value:<java.lang.Exception> should not be equal to:<java.lang.Exception>").on(
        new CodeToTest() {
          public void run() {
            Exception e = new Exception();
            new ThrowableAssert(e).isNotEqualTo(e);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualsAreEqual() {
    expectAssertionError("[A Test] actual value:<java.lang.Exception> should not be equal to:<java.lang.Exception>")
        .on(new CodeToTest() {
          public void run() {
            Exception e = new Exception();
            new ThrowableAssert(e).as("A Test").isNotEqualTo(e);
          }
        });
  }

  @Test public void shouldReturnCauseHierarchy() {
    CauseHierarchyAssert causeHierarchy = new ThrowableAssert(new Exception()).causeHierarchy();
    assertNotNull(causeHierarchy);
  }

  @Test public void shouldFailIfActualIsNullWhenGettingCauseHierarchy() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).causeHierarchy();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenGettingCauseHierarchy() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test").causeHierarchy();
      }
    });
  }

  @Test public void shouldPassIsCauseHierarchyHasCauseOfType() {
    Exception e = new Exception(new IllegalArgumentException(new FileNotFoundException()));
    new ThrowableAssert(e).causeHierarchy().hasCauseOfType(IOException.class);
  }

  @Test public void shouldFailIfActualDoesNotHaveCauseWhenCheckingForCauseOfType() {
    expectAssertionError("expected a cause of type:<java.lang.Exception>, but found none").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).causeHierarchy().hasCauseOfType(Exception.class);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveCauseWhenCheckingForCauseOfType() {
    expectAssertionError("[A Test] expected a cause of type:<java.lang.Exception>, but found none").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).as("A Test").causeHierarchy().hasCauseOfType(Exception.class);
          }
        });
  }

  @Test public void shouldFailIfActualDoesNotHaveCauseOfGivenType() {
    expectAssertionError("expected a cause of type:<java.lang.NullPointerException>, but found none").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception(new Exception())).causeHierarchy().hasCauseOfType(
                NullPointerException.class);
          }
        });
  }

  @Test public void shouldPassIsCauseHierarchyHasCauseOfExactType() {
    Exception e = new Exception(new IllegalArgumentException(new NullPointerException()));
    new ThrowableAssert(e).causeHierarchy().hasCauseOfExactType(NullPointerException.class);
  }

  @Test public void shouldFailIfActualDoesNotHaveCauseWhenCheckingForCauseOfExactType() {
    expectAssertionError("expected a cause of exact type:<java.lang.Exception>, but found none").on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).causeHierarchy().hasCauseOfExactType(Exception.class);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveCauseWhenCheckingForCauseOfExactType() {
    expectAssertionError("[A Test] expected a cause of exact type:<java.lang.Exception>, but found none").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception()).as("A Test").causeHierarchy().hasCauseOfExactType(Exception.class);
          }
        });
  }

  @Test public void shouldFailIfActualDoesNotHaveCauseOfExactGivenType() {
    expectAssertionError("expected a cause of exact type:<java.lang.NullPointerException>, but found none").on(
        new CodeToTest() {
          public void run() {
            new ThrowableAssert(new Exception(new Exception())).causeHierarchy().hasCauseOfExactType(
                NullPointerException.class);
          }
        });
  }

  private void shouldThrowErrorIfTypeIsNullWhenCheckingIfInstanceOf(CodeToTest codeToTest) {
    expectIllegalArgumentException("The given type to check against should not be null").on(codeToTest);
  }
}
