/*
 * Created on Apr 29, 2007
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

import static java.io.File.separator;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Commons.packageNameAsPathFrom;
import static org.fest.util.Strings.concat;

/**
 * Understands source folders.
 *
 * @author Yvonne Wang
 */
abstract class SourceFolders {

  private static final String SRC = concat("src", separator);
  private static final String CURRENT_FOLDER = currentFolder();
  private static final String PACKAGE_NAME_AS_PATH = packageNameAsPathFrom(SourceFolders.class);
  
  private static String currentFolder() {
    try {
      return new File(".").getCanonicalPath();
    } catch (IOException e) {
      throw new RuntimeException("Unable to get the path of current folder", e);
    }
  }

  static final SourceFolders MAIN_FOLDER = new SourceFolders() {
    private final String javaFolderPath = javaFolderPath("main");

    @Override String filePathFor(String className) {
      return filePathFor(javaFolderPath, className);
    }

    @Override String packageNameAsAbsolutePath() {
      return packageNameAsAbsolutePath(javaFolderPath);
    }
  };
  
  static final SourceFolders TEST_FOLDER = new SourceFolders() {
    private final String javaFolderPath = javaFolderPath("test");

    @Override String filePathFor(String className) {
      return filePathFor(javaFolderPath, className);
    }

    @Override String packageNameAsAbsolutePath() {
      return packageNameAsAbsolutePath(javaFolderPath);
    }
  };

  final String packageNameAsPath;
  
  private SourceFolders() {
    packageNameAsPath = packageNameAsPathFrom(getClass());
  }
  
  abstract String filePathFor(String className);
  
  abstract String packageNameAsAbsolutePath();
  
  final String packageNameAsAbsolutePath(String javaFolderPath) {
    return concat(CURRENT_FOLDER, separator, javaFolderPath, PACKAGE_NAME_AS_PATH);
  }
    
  final String javaFolderPath(String sourceFolderName) {
    return concat(SRC, sourceFolderName, separator, "java", separator);
  }
  
  final String filePathFor(String sourceFolder, String className) {
    return concat(sourceFolder, packageNameAsPath, className, ".java");    
  }
}
