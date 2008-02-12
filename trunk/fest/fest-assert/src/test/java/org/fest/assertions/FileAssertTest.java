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

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.concat;
import static org.fest.util.Systems.LINE_SEPARATOR;

import java.io.File;

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

  @Test public void shouldPassIfActualExistsAsAnticipated() {
    file.exists(true);
    new FileAssert(file).exists();
  }

  @Test public void shouldPassIfActualDoesNotExistAsAnticipated() {
    file.exists(false);
    new FileAssert(file).doesNotExist();
  }

  @Test public void shouldPassIfActualIsDirectoryAsAnticipated() {
    file.directory(true);
    new FileAssert(file).isDirectory();
  }

  @Test public void shouldPassIfActualIsFileAsAnticipated() {
    file.file(true);
    new FileAssert(file).isFile();
  }

  @Test public void shouldPassIfSizeOfActualIsEqualToExpectedAsAnticipated() {
    file.length(8);
    new FileAssert(file).hasSize(8);
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

  @Test public void shouldSucceedIfFilesHaveSameContentAsAnticipated() {
    file.exists(true);
    FileContentComparatorStub comparator = new FileContentComparatorStub();
    FileStub expected = new FileStub("c:\\temp\\expected.txt");
    expected.exists(true);
    new FileAssert(file, comparator).hasSameContentAs(expected);
  }

  static class FileContentComparatorStub extends FileContentComparator {
    private LineDiff[] diffs = new LineDiff[0];

    void lineDiffs(LineDiff...diffs) {
      this.diffs = diffs;
    }

    @Override LineDiff[] compareContents(File actual, File expected) {
      return diffs;
    }
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

  @Test public void shouldPassIfActualIsAbsoluteAsAnticipated() {
    file.absolute(true);
    new FileAssert(file).isAbsolute();
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

  @Test public void shouldSPassIfActualIsRelativeAsAnticipated() {
    file.absolute(false);
    new FileAssert(file).isRelative();
  }
}
