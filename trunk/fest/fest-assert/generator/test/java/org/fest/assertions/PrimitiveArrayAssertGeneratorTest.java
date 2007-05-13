/*
 * Created on Mar 8, 2007
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
package org.fest.assertions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.SourceFolders.MAIN_FOLDER;
import static org.fest.assertions.SourceFolders.TEST_FOLDER;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import static org.testng.Assert.assertTrue;

/**
 * Tests for <code>{@link PrimitiveArrayAssertGenerator}</code>.
 *
 * @author Alex Ruiz
 */
public class PrimitiveArrayAssertGeneratorTest {

  private static final List<String> EXPECTED_GENERATED_JAVA_FILES = new ArrayList<String>();
  static {
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("BooleanArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("CharArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("ByteArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("ShortArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("IntArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("LongArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("FloatArrayAssert.java"));
    EXPECTED_GENERATED_JAVA_FILES.add(javaFilePath("DoubleArrayAssert.java"));
  }
  
  private static String javaFilePath(String javaFileName) {
    return concat(MAIN_FOLDER.packageNameAsAbsolutePath(), javaFileName);
  }
  
  private static final List<String> EXPECTED_GENERATED_TEST_FILES = new ArrayList<String>();
  static {
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("BooleanArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("CharArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("ByteArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("ShortArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("IntArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("LongArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("FloatArrayAssertTest.java"));
    EXPECTED_GENERATED_TEST_FILES.add(testFilePath("DoubleArrayAssertTest.java"));
  }
  
  private static String testFilePath(String javaFileName) {
    return concat(TEST_FOLDER.packageNameAsAbsolutePath(), javaFileName);
  }
  
  private PrimitiveArrayAssertGenerator generator;
  
  @BeforeClass public void setUp() throws Exception {
    generator = new PrimitiveArrayAssertGenerator();
  }
  
  @Test public void shouldGenerateArrayAssertFiles() throws Exception {
    generator.generate();
    for (String javaFile : EXPECTED_GENERATED_JAVA_FILES) assertThatFileExists(javaFile);
    for (String testFile : EXPECTED_GENERATED_TEST_FILES) assertThatFileExists(testFile);
  }
  
  private void assertThatFileExists(String filePath) {
    File file = new File(filePath);
    String absolutePath = file.getAbsolutePath();
    assertTrue(file.exists(), concat("file ", quote(absolutePath), "should exist"));
    assertTrue(file.isFile());
    assertTrue(file.getTotalSpace() > 0);
  }
}
