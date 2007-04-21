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
package com.jtuzi.fest.assertions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jtuzi.fest.assertions.PrimitiveTypeArrayAssertGenerator;

import static com.jtuzi.fest.assertions.Commons.packageNameAsPathFrom;
import static com.jtuzi.fest.util.Strings.*;
import static java.io.File.separator;


import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link PrimitiveTypeArrayAssertGenerator}</code>.
 *
 * @author Alex Ruiz
 */
public class PrimitiveTypeArrayAssertGeneratorTest {

  private static final String JAVA_PACKAGE_PATH = fullPathFor(join(separator, mainFolder(), "java"));
  private static final String TEST_PACKAGE_PATH = fullPathFor(join(separator, mainFolder(), "test"));

  private static String fullPathFor(String sourceFolder) {
    return join(separator, sourceFolder, packageNameAsPathFrom(PrimitiveTypeArrayAssertGeneratorTest.class));
  }
  
  private static String mainFolder() {
    return join(separator, currentFolder(), "src", "main");
  }

  private static String currentFolder() {
    try {
      return new File(".").getCanonicalPath();
    } catch (IOException e) {
      throw new RuntimeException("Unable to get the path of current folder", e);
    }
  }

  private static final List<String> EXPECTED_GENERATED_JAVA_FILES = new ArrayList<String>();
  static {
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "BooleanArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "CharArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "ByteArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "ShortArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "IntArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "LongArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "FloatArrayAssert.java");
    EXPECTED_GENERATED_JAVA_FILES.add(JAVA_PACKAGE_PATH + "DoubleArrayAssert.java");
  }
  
  private static final List<String> EXPECTED_GENERATED_TEST_FILES = new ArrayList<String>();
  static {
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "BooleanArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "CharArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "ByteArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "ShortArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "IntArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "LongArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "FloatArrayAssertTest.java");
    EXPECTED_GENERATED_TEST_FILES.add(TEST_PACKAGE_PATH + "DoubleArrayAssertTest.java");
  }
  
  private PrimitiveTypeArrayAssertGenerator generator;
  
  @BeforeClass public void setUp() throws Exception {
    generator = new PrimitiveTypeArrayAssertGenerator();
  }
  
  @Test public void shouldGenerateArrayAssertFiles() throws Exception {
    generator.generate();
    for (String javaFile : EXPECTED_GENERATED_JAVA_FILES) assertThatFileExists(javaFile);
    for (String testFile : EXPECTED_GENERATED_TEST_FILES) assertThatFileExists(testFile);
  }
  
  private void assertThatFileExists(String filePath) {
    File file = new File(filePath);
    String absolutePath = file.getAbsolutePath();
    assertTrue(file.exists(), "file " + quote(absolutePath) + "should exist");
    assertTrue(file.isFile());
    assertTrue(file.getTotalSpace() > 0);
  }
}
