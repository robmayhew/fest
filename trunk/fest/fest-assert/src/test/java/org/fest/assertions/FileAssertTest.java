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
import static org.fest.util.Strings.concat;
import static org.fest.util.Systems.LINE_SEPARATOR;
import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;

import org.fest.assertions.FileContentComparator.LineDiff;
import org.fest.test.CodeToTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FileAssert}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FileAssertTest {

  private static FileStub file;

  @BeforeMethod public void setUp() {
    file = new FileStub("c:\\temp\\file.txt");
  }

  @Test public void shouldSetDescription() {
    FileAssert assertion = new FileAssert(file);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    FileAssert assertion = new FileAssert(file);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNullFileCondition extends Condition<File> {
    @Override public boolean matches(File f) {
      return f != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new FileAssert(file).satisfies(new NotNullFileCondition());
  }

  @Test public void shouldThrowErrorIfConditionIsNull() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new FileAssert(file).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<null>").on(new CodeToTest() {
      public void run() {
        new FileAssert(null).satisfies(new NotNullFileCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<null>").on(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").satisfies(new NotNullFileCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<non-null object> but was:<null>").on(new CodeToTest() {
      public void run() {
        new FileAssert(null).satisfies(new NotNullFileCondition().as("non-null object"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<non-null object> but was:<null>").on(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").satisfies(new NotNullFileCondition().as("non-null object"));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfNotExist() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).doesNotExist();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfNotExist() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").doesNotExist();
      }
    });
  }

  @Test public void shouldFailIfActualExistsAndExpectingNotToExist() {
    file.exists(true);
    expectAssertionError("file:<c:\\temp\\file.txt> should not exist").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).doesNotExist();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualExistsAndExpectingNotToExist() {
    file.exists(true);
    expectAssertionError("[A Test] file:<c:\\temp\\file.txt> should not exist").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").doesNotExist();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfExist() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).exists();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfExist() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").exists();
      }
    });
  }

  @Test public void shouldFailIfActualDoesNotExistAndExpectingToExist() {
    file.exists(false);
    expectAssertionError("file:<c:\\temp\\file.txt> should exist").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).exists();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotExistAndExpectingToExist() {
    file.exists(false);
    expectAssertionError("[A Test] file:<c:\\temp\\file.txt> should exist").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").exists();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsDirectory() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).isDirectory();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsDirectory() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").isDirectory();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotDirectoryAndExpectingDirectory() {
    file.directory(false);
    expectAssertionError("file:<c:\\temp\\file.txt> should be a directory").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isDirectory();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotDirectoryAndExpectingDirectory() {
    file.directory(false);
    expectAssertionError("[A Test] file:<c:\\temp\\file.txt> should be a directory").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isDirectory();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsFile() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).isFile();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsFile() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").isFile();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotFileAndExpectingFile() {
    file.file(false);
    expectAssertionError("file:<c:\\temp\\file.txt> should be a file").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isFile();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotFileAndExpectingFile() {
    file.file(false);
    expectAssertionError("[A Test] file:<c:\\temp\\file.txt> should be a file").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isFile();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingSize() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).hasSize(8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingSize() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").hasSize(8);
      }
    });
  }

  @Test public void shouldFailIfActualSizeIsNotEqualToExpectedAndExpectingEqual() {
    file.length(8);
    expectAssertionError("size of file:<c:\\temp\\file.txt> expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).hasSize(6);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualSizeIsNotEqualToExpectedAndExpectingEqual() {
    file.length(8);
    expectAssertionError("[A Test] size of file:<c:\\temp\\file.txt> expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").hasSize(6);
      }
    });
  }

  @Test public void shouldPassIfActualExists() {
    file.exists(true);
    new FileAssert(file).exists();
  }

  @Test public void shouldPassIfActualDoesNotExist() {
    file.exists(false);
    new FileAssert(file).doesNotExist();
  }

  @Test public void shouldPassIfActualIsDirectory() {
    file.directory(true);
    new FileAssert(file).isDirectory();
  }

  @Test public void shouldPassIfActualIsFile() {
    file.file(true);
    new FileAssert(file).isFile();
  }

  @Test public void shouldReturnLongAssertForFileSize() {
    file.length(8);
    new FileAssert(file).size().isEqualTo(8);
  }
  
  @Test public void shouldFailIfActualIsNullWhenGettingSize() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).size();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenGettingSize() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").size();
      }
    });
  }

  @Test public void shouldPassIfSizeOfActualIsEqualToExpected() {
    file.length(8);
    new FileAssert(file).hasSize(8);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingSameContent() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).hasSameContentAs(file);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingSameContent() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").hasSameContentAs(file);
      }
    });
  }

  @Test public void shouldFailIfFilesHaveDifferentContentAndExpectingSame() {
    file.exists(true);
    final FileContentComparatorStub comparator = new FileContentComparatorStub();
    comparator.lineDiffs(new LineDiff(6, "abc", "xyz"), new LineDiff(8, "xyz", "abc"));
    final String message =
      concat("file:<c:\\temp\\file.txt> and file:<c:\\temp\\expected.txt> do not have same contents:",
          LINE_SEPARATOR, "line:<6>, expected:<'xyz'> but was:<'abc'>",
          LINE_SEPARATOR, "line:<8>, expected:<'abc'> but was:<'xyz'>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        FileStub expected = new FileStub("c:\\temp\\expected.txt");
        expected.exists(true);
        new FileAssert(file, comparator).hasSameContentAs(expected);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFilesHaveDifferentContentAndExpectingSame() {
    file.exists(true);
    final FileContentComparatorStub comparator = new FileContentComparatorStub();
    comparator.lineDiffs(new LineDiff(6, "abc", "xyz"), new LineDiff(8, "xyz", "abc"));
    final String message =
      concat("[A Test] file:<c:\\temp\\file.txt> and file:<c:\\temp\\expected.txt> do not have same contents:",
          LINE_SEPARATOR, "line:<6>, expected:<'xyz'> but was:<'abc'>",
          LINE_SEPARATOR, "line:<8>, expected:<'abc'> but was:<'xyz'>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        FileStub expected = new FileStub("c:\\temp\\expected.txt");
        expected.exists(true);
        new FileAssert(file, comparator).as("A Test").hasSameContentAs(expected);
      }
    });
  }

  @Test public void shouldSucceedIfFilesHaveSameContent() {
    file.exists(true);
    FileContentComparatorStub comparator = new FileContentComparatorStub();
    FileStub expected = new FileStub("c:\\temp\\expected.txt");
    expected.exists(true);
    new FileAssert(file, comparator).hasSameContentAs(expected);
  }

  @Test public void shouldFailIfIOExceptionThrownWhenComparingFiles() {
    file.exists(true);
    FileContentComparatorStub comparator = new FileContentComparatorStub();
    IOException toThrow = new IOException();
    comparator.exceptionToThrow(toThrow);
    FileStub expected = new FileStub("c:\\temp\\expected.txt");
    expected.exists(true);
    try {
      new FileAssert(file, comparator).hasSameContentAs(expected);
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(),
          "unable to compare contents of files:<c:\\temp\\file.txt> and <c:\\temp\\expected.txt>");
      assertSame(e.getCause(), toThrow);
    }
  }

  @Test public void shouldFailShowingDescriptionIfIOExceptionThrownWhenComparingFiles() {
    file.exists(true);
    FileContentComparatorStub comparator = new FileContentComparatorStub();
    IOException toThrow = new IOException();
    comparator.exceptionToThrow(toThrow);
    FileStub expected = new FileStub("c:\\temp\\expected.txt");
    expected.exists(true);
    try {
      new FileAssert(file, comparator).as("A Test").hasSameContentAs(expected);
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(),
          "[A Test] unable to compare contents of files:<c:\\temp\\file.txt> and <c:\\temp\\expected.txt>");
      assertSame(e.getCause(), toThrow);
    }
  }

  @Test public void shouldThrowErrorIfFileToCompareToIsNull() {
    expectIllegalArgumentException("File to compare to should not be null").on(new CodeToTest() {
      public void run() throws Throwable {
        new FileAssert(file).hasSameContentAs(null);
      }
    });
  }

  static class FileContentComparatorStub extends FileContentComparator {
    private LineDiff[] diffs = new LineDiff[0];
    private IOException e;

    void lineDiffs(LineDiff...diffs) {
      this.diffs = diffs;
    }

    void exceptionToThrow(IOException e) {
      this.e = e;
    }

    @Override LineDiff[] compareContents(File actual, File expected) throws IOException {
      if (e != null) throw e;
      return diffs;
    }
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsAbsolute() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).isAbsolute();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsAbsolute() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").isAbsolute();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotAbsoluteAndExpectingAbsolute() {
    file.absolute(false);
    expectAssertionError("file:<c:\\temp\\file.txt> should be an absolute path").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isAbsolute();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotAbsoluteAndExpectingAbsolute() {
    file.absolute(false);
    expectAssertionError("[A Test] file:<c:\\temp\\file.txt> should be an absolute path").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isAbsolute();
      }
    });
  }

  @Test public void shouldPassIfActualIsAbsolute() {
    file.absolute(true);
    new FileAssert(file).isAbsolute();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsRelative() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).isRelative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsRelative() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test").isRelative();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotRelativeAndExpectingRelative() {
    file.absolute(true);
    expectAssertionError("file:<c:\\temp\\file.txt> should be a relative path").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isRelative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotRelativeAndExpectingRelative() {
    file.absolute(true);
    expectAssertionError("[A Test] file:<c:\\temp\\file.txt> should be a relative path").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isRelative();
      }
    });
  }

  @Test public void shouldSPassIfActualIsRelative() {
    file.absolute(false);
    new FileAssert(file).isRelative();
  }


  @Test public void shouldPassIfFilesAreSame() {
    new FileAssert(file).isSameAs(file);
  }

  @Test public void shouldFailIfFilesAreNotSameAndExpectingSame() {
    expectAssertionError("expected same instance but found:<c:\\temp\\file.txt> and:<c:\\>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isSameAs(new FileStub("c:\\"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFilesAreNotSameAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<c:\\temp\\file.txt> and:<c:\\>").on(
        new CodeToTest() {
          public void run() {
            new FileAssert(file).as("A Test").isSameAs(new FileStub("c:\\"));
          }
        });
  }

  @Test public void shouldPassIfFilesAreNotSame() {
    new FileAssert(file).isNotSameAs(new FileStub("c:\\"));
  }

  @Test public void shouldFailIfFilesAreSameAndExpectingNotSame() {
    expectAssertionError("given objects are same:<c:\\temp\\file.txt>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isNotSameAs(file);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFilesAreSameAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<c:\\temp\\file.txt>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isNotSameAs(file);
      }
    });
  }

  @Test public void shouldPassIfFilesAreEqual() {
    new FileAssert(file).isEqualTo(new FileStub("c:\\temp\\file.txt"));
  }

  @Test public void shouldFailIfActualsAreNotEqual() {
    expectAssertionError("expected:<c:\\> but was:<c:\\temp\\file.txt>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isEqualTo(new FileStub("c:\\"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualsAreNotEqual() {
    expectAssertionError("[A Test] expected:<c:\\> but was:<c:\\temp\\file.txt>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isEqualTo(new FileStub("c:\\"));
      }
    });
  }

  @Test public void shouldPassIfFilesAreNotEqual() {
    new FileAssert(file).isNotEqualTo(new FileStub("c:\\"));
  }

  @Test public void shouldFailIfActualsAreEqual() {
    expectAssertionError("actual value:<c:\\temp\\file.txt> should not be equal to:<c:\\temp\\file.txt>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isNotEqualTo(new FileStub("c:\\temp\\file.txt"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualsAreEqual() {
    expectAssertionError("[A Test] actual value:<c:\\temp\\file.txt> should not be equal to:<c:\\temp\\file.txt>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test").isNotEqualTo(new FileStub("c:\\temp\\file.txt"));
      }
    });
  }
}
