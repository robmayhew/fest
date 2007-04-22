/*
 * Created on Sep 23, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.util;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import static org.fest.util.Objects.asList;
import static org.fest.util.Strings.quote;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link Files}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FilesTest {

  private FolderFixture root;
  
  @BeforeClass public void setUp() throws Exception {
    root = new FolderFixture("root");
    root.addFolder("dir_1").addFiles("file_1_1", "file_1_2").addFolder("dir_1_1").addFiles("file_1_1_1");
    root.addFolder("dir_2").addFiles("file_2_1", "file_2_2", "file_2_3");
  }
  
  @AfterClass public void tearDown() {
    root.delete();
  }
  
  @Test public void shouldReturnNamesOfFilesInGivenDirectoryWithoutLookingInSubdirectories() {
    String path = "root" + File.separator + "dir_1";
    assertContainsFiles(Files.fileNamesIn(path, false), asList("file_1_1", "file_1_2"));
  }
  
  @Test public void shouldReturnNamesOfFilesInGivenDirectoryAndItsSubdirectories() {
    String path = "root" + File.separator + "dir_1";
    assertContainsFiles(Files.fileNamesIn(path, true), asList("file_1_1", "file_1_2", "file_1_1_1"));
  }

  private void assertContainsFiles(List<String> actualFiles, List<String> expectedFiles) {
    assertNoDuplicates(actualFiles);
    for (String fileName : actualFiles) {
      String name = new File(fileName).getName();
      assertTrue(expectedFiles.remove(name), "The file " + quote(name) + " should not be in the list");
    }
    assertTrue(expectedFiles.isEmpty(), "Actual list of files should contain " + expectedFiles);
  }
  
  private void assertNoDuplicates(List<String> actualFiles) {
    if (actualFiles == null || actualFiles.isEmpty()) return;
    HashSet<String> withoutDuplicates = new HashSet<String>(actualFiles);
    assertEquals(actualFiles.size(), withoutDuplicates.size(), actualFiles + " contains duplicates");
  }
}
