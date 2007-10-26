/*
 * Created on Mar 2, 2007
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

import org.apache.velocity.VelocityContext;

import static java.util.logging.Level.SEVERE;
import static org.apache.velocity.util.StringUtils.capitalizeFirstLetter;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.join;

/**
 * Understands generation of assertion methods for arrays of primitives.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class PrimitiveArrayAssertGenerator extends VelocityCodeGenerator {

  private final String javaFileTemplatePath;
  private final String testFileTemplatePath;
  
  PrimitiveArrayAssertGenerator() throws Exception {
    javaFileTemplatePath = concat(packageNameAsPath, "ArrayAssertTemplate.vm");
    testFileTemplatePath = concat(packageNameAsPath, "ArrayAssertTestTemplate.vm");
  }
  
  void generate() throws Exception {
    generate(boolean.class, arrays(array("true"), array("false"), array("true", "false")));
    generate(char.class, arrays(array("'a'", "'b'"), array("'c'", "'d'"), array("'a', 'b', 'c'")));
    generate(byte.class, arrays(byteArray("8", "6"), byteArray("7"), byteArray("8", "6", "7")));
    generate(short.class, arrays(shortArray("43", "68"), shortArray("98"), shortArray("43", "68", "98")));
    generate(int.class, arrays(array("459", "23"), array("90, 82"), array("459", "23", "88")));
    generate(long.class, arrays(array("43l", "53l"), array("434l"), array("43l", "53l", "88l")));
    generate(float.class, arrays(array("34.90f"), array("88.43f"), array("34.90f", "88.6f")));
    generate(double.class, arrays(array("55.03", "4345.91"), array("5323.2"), array("55.03", "4345.91", "88.6")));
  }

  private String byteArray(String...elements) {
    return array(castedArray("byte", elements));
  }
  
  private String shortArray(String...elements) {
    return array(castedArray("short", elements));
  }
  
  private String[] castedArray(String type, String[] elements) {
    for (int i = 0; i < elements.length; i++)
      elements[i] = concat("(", type, ")", elements[i]);
    return elements;
  }
  
  private String array(String...elements) {
    return join(elements).with(", ");
  }
  
  private TestArrays arrays(String base, String differentThanBase, String superSetOfBase) {
    return new TestArrays(base, differentThanBase, superSetOfBase);
  }

  private void generate(Class<?> arrayType, TestArrays testArrays) throws Exception {
    String arrayTypeName = arrayType.getSimpleName();
    String className = concat(capitalizeFirstLetter(arrayTypeName), "ArrayAssert");
    String testName = concat(className, "Test");
    VelocityContext context = newContext();
    context.put("arrayType", arrayTypeName);
    context.put("className", className);
    context.put("testName", testName);
    context.put("classToTest", className);
    context.put("baseArray", testArrays.base);
    context.put("differentThanBaseArray", testArrays.differentThanBase);
    context.put("superSetOfBaseArray", testArrays.superSetOfBase);
    generateJavaFile(className, context); 
    generateTestFile(testName, context);
  }

  String javaFileTemplatePath() { return javaFileTemplatePath; }

  String testFileTemplatePath() { return testFileTemplatePath; }
  
  private static class TestArrays {
    final String base;
    final String differentThanBase;
    final String superSetOfBase;
    
    TestArrays(String base, String differentThanBase, String superSetOfBase) {
      this.base = base;
      this.differentThanBase = differentThanBase;
      this.superSetOfBase = superSetOfBase;
    }
  }
  
  public static void main(String[] args) {
    try {
      new PrimitiveArrayAssertGenerator().generate();
    } catch (Exception e) {
      logger.log(SEVERE, "Unable to generate code", e);
    }
  }
}
