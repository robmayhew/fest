/*
 * Created on Jun 18, 2007
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

import static java.util.logging.Level.SEVERE;
import static org.fest.util.Strings.concat;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;

/**
 * Understands generation of assertion methods for arrays of primitives.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class PrimitiveFailGenerator extends VelocityCodeGenerator {

  private final String javaFileTemplatePath;
  
  PrimitiveFailGenerator() throws Exception {
    javaFileTemplatePath = concat(packageNameAsPath, "PrimitiveFailTemplate.vm");
  }
  
  void generate() throws Exception {
    List<String> types = new ArrayList<String>();
    types.add(boolean.class.getSimpleName());
    types.add(char.class.getSimpleName());
    types.add(byte.class.getSimpleName());
    types.add(short.class.getSimpleName());
    types.add(int.class.getSimpleName());
    types.add(long.class.getSimpleName());
    types.add(float.class.getSimpleName());
    types.add(double.class.getSimpleName());
    generate(types);
  }
  
  private void generate(List<String> primitiveTypeNames) throws Exception {
    String className = "PrimitiveFail";
    VelocityContext context = newContext();
    context.put("className", className);
    context.put("types", primitiveTypeNames);
    generateJavaFile(className, context); 
  }

  String javaFileTemplatePath() { return javaFileTemplatePath; }

  String testFileTemplatePath() { return null; }
  
  public static void main(String[] args) {
    try {
      new PrimitiveFailGenerator().generate();
    } catch (Exception e) {
      logger.log(SEVERE, "Unable to generate code", e);
    }
  }
}
