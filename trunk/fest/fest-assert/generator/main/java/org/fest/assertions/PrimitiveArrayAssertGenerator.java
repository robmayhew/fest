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
import org.apache.velocity.tools.generic.DateTool;

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
    generate(boolean.class, new TestArrays(array("true", "false"), array("false", "true")));
    generate(char.class, new TestArrays(array("'a'", "'b'"), array("'c'", "'d'")));
    generate(byte.class, new TestArrays(array("(byte)8", "(byte)6"), array("(byte)8")));
    generate(short.class, new TestArrays(array("(short)43", "(short)68"), array("(short)98")));
    generate(int.class, new TestArrays(array("459", "23"), array("90, 82")));
    generate(long.class, new TestArrays(array("43l", "53l"), array("434l")));
    generate(float.class, new TestArrays(array("34.90f"), array("88.43f")));
    generate(double.class, new TestArrays(array("55.03", "4345.91"), array("5323.2")));
  }

  private String array(String...elements) {
    return join(elements).with(", ");
  }

  private void generate(Class<?> arrayType, TestArrays testArrays) throws Exception {
    String arrayTypeName = arrayType.getSimpleName();
    String className = concat(capitalizeFirstLetter(arrayTypeName), "ArrayAssert");
    String testName = concat(className, "Test");
    VelocityContext context = new VelocityContext();
    addGeneratorInfoTo(context);
    context.put("arrayType", arrayTypeName);
    context.put("className", className);
    context.put("testName", testName);
    context.put("classToTest", className);
    context.put("firstArray", testArrays.first);
    context.put("secondArray", testArrays.second);
    context.put("date", new DateTool());
    generateJavaFile(className, context); 
    generateTestFile(testName, context);
  }

  @Override String javaFileTemplatePath() { return javaFileTemplatePath; }

  @Override String testFileTemplatePath() { return testFileTemplatePath; }
  
  private static class TestArrays {
    final String first;
    final String second;

    TestArrays(String first, String second) {
      this.first = first;
      this.second = second;
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
