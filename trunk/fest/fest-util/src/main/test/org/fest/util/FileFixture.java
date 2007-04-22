/*
 * Created on Sep 25, 2006
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
import java.io.IOException;
import java.util.logging.Logger;

import static java.io.File.separator;


import static org.fest.util.Strings.quote;

import static org.testng.Assert.assertTrue;

/**
 * Understands creation and deletion of files in the file system.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FileFixture {

  private static Logger logger = Logger.getLogger(FolderFixture.class.getName());
  
  final String name;
  final FolderFixture parent;
  
  private File file;
  
  public FileFixture(String name, FolderFixture parent) throws IOException {
    this.name = name;
    this.parent = parent;
    create();
  }
  
  private void create() throws IOException {
    String path = relativePath();
    file = new File(path);
    if (!file.exists()) {
      assertTrue(file.createNewFile(), "Unable to create file " + quote(path));
      logger.info("Created file " + quote(path));
    }
    assertTrue(file.isFile(), "The file " + quote(path) + " should be a file");
    logger.info("The file " + quote(path) + " exists");
  }

  public void delete() {
    String path = relativePath();
    assertTrue(file.delete(), "Unable to delete file " + quote(path));
    logger.info("The file " + quote(path) + " was deleted");
  }

  String relativePath() {
    return parent != null ? parent.relativePath() + separator + name : name;
  }
  
  String absolutePath() {
    return file.getAbsolutePath();
  }
}
