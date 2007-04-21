/*
 * Created on Mar 19, 2007
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
package org.fest.swing.assertions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


import static org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER;
import static org.fest.swing.assertions.Commons.packageNameAsPathFrom;

import static java.io.File.separator;

/**
 * Understands a template for code generators using Apache Velocity.
 *
 * @author Alex Ruiz
 */
abstract class VelocityCodeGenerator {

  static final Logger logger = Logger.getAnonymousLogger();
  
  static class SourceFolders {
    static final String ROOT = "src" + separator + "main" + separator;
    static final String JAVA = ROOT + "java" + separator;
    static final String TEST = ROOT + "test" + separator;
  }

  final String packageNameAsPath;

  public VelocityCodeGenerator() throws Exception {
    packageNameAsPath = packageNameAsPathFrom(getClass());
    setUpVelocity();
  }

  private void setUpVelocity() throws Exception {
    Properties p = new Properties();
    p.setProperty(RESOURCE_LOADER, "classpath");
    p.setProperty("classpath." + RESOURCE_LOADER + ".class", ClasspathResourceLoader.class.getName());
    Velocity.init(p);
  }

  final void generateJavaFile(String className, VelocityContext context) throws Exception {
    String javaFilePath = SourceFolders.JAVA + filePathFor(className);
    generateFile(javaFilePath, javaFileTemplate(), context);
  }

  abstract Template javaFileTemplate();

  final void generateTestFile(String testName, VelocityContext context) throws Exception {
    String testFilePath = SourceFolders.TEST + filePathFor(testName);
    generateFile(testFilePath, testFileTemplate(), context);
  }
  
  abstract Template testFileTemplate();

  private String filePathFor(String className) {
    return packageNameAsPath + className + ".java";
  }

  private void generateFile(String fileToGeneratePath, Template template, VelocityContext context) throws Exception {
    Writer writer = null;
    try {
      writer = fileWriter(fileToGeneratePath);
      template.merge(context, writer);
      logFileCreated(fileToGeneratePath);
    } finally {
      flushAndClose(writer);
    }    
  }
  
  private Writer fileWriter(String filePath) throws Exception {
    File file = new File(filePath);
    if (file.exists()) file.delete();
    return new BufferedWriter(new FileWriter(file));
  }
  
  private void logFileCreated(String fileName) {
    logger.info("File " + fileName + " generated!");
  }
  
  private void flushAndClose(Writer writer) {
    if (writer == null) return;
    try { writer.flush(); } catch (Exception e) { logSevere("Unable to flush writer", e); }
    try { writer.close(); } catch (Exception e) { logSevere("Unable to close writer", e); }
  }
  
  final void logSevere(String message, Exception e) {
    logger.log(Level.SEVERE, message, e);
  }
  
}
