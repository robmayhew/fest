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
package org.fest.assertions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.DateTool;

import static java.io.File.separator;
import static org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER;

import static org.fest.assertions.Commons.packageNameAsPathFrom;
import static org.fest.assertions.SourceFolders.MAIN_FOLDER;
import static org.fest.assertions.SourceFolders.TEST_FOLDER;
import static org.fest.util.Files.flushAndClose;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands a template for code generators using Apache Velocity.
 *
 * @author Alex Ruiz
 */
abstract class VelocityCodeGenerator {

  static final Logger logger = Logger.getAnonymousLogger();
  
  private static final String STANDARD_PATH_SEPARATOR = "/";
  
  final String packageNameAsPath;

  public VelocityCodeGenerator() throws Exception {
    packageNameAsPath = packageNameAsPathFrom(getClass());
    setUpVelocity();
  }

  private void setUpVelocity() throws Exception {
    Properties p = new Properties();
    p.setProperty(RESOURCE_LOADER, "classpath");
    p.setProperty(concat("classpath.", RESOURCE_LOADER, ".class"), ClasspathResourceLoader.class.getName());
    Velocity.init(p);
  }

  abstract void generate() throws Exception;
  
  /** @return a new Velocity context containing information about this code generator and the current date. */
  final VelocityContext newContext() {
    VelocityContext context = new VelocityContext();
    context.put("generator", getClass().getName());
    context.put("generated", dateInISO8601Format());
    context.put("date", new DateTool());
    return context;
  }
  
  final void generateJavaFile(String javaClassName, VelocityContext context) throws Exception {
    String javaFilePath = MAIN_FOLDER.filePathFor(javaClassName);
    String javaFileTemplatePath = javaFileTemplatePath();
    context.put("javaFileTemplatePath", standardPath(javaFileTemplatePath));
    generateFile(javaFilePath, Velocity.getTemplate(javaFileTemplatePath), context);
  }

  abstract String javaFileTemplatePath();

  final void generateTestFile(String testClassName, VelocityContext context) throws Exception {
    String testFilePath = TEST_FOLDER.filePathFor(testClassName);
    String testFileTemplatePath = testFileTemplatePath();
    context.put("testFileTemplatePath", standardPath(testFileTemplatePath));
    generateFile(testFilePath, Velocity.getTemplate(testFileTemplatePath), context);
  }
  
  abstract String testFileTemplatePath();

  private String standardPath(String path) {
    if (separator.equals(STANDARD_PATH_SEPARATOR)) return quote(path);
    return quote(path.replace(separator, STANDARD_PATH_SEPARATOR));
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
  
  final String dateInISO8601Format() {
    String result = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").format(new Date());
    return concat(result.substring(0, 19), result.substring(22, result.length()));
  }
  
  private Writer fileWriter(String filePath) throws Exception {
    File file = new File(filePath);
    if (file.exists()) file.delete();
    return new BufferedWriter(new FileWriter(file));
  }
  
  private void logFileCreated(String fileName) {
    logger.info(concat("File ", fileName, " generated!"));
  }
  
  final void logSevere(String message, Exception e) {
    logger.log(Level.SEVERE, message, e);
  }
  
}
