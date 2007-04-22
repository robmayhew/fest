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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.io.File.separator;


import static org.fest.util.Objects.isEmpty;
import static org.fest.util.Strings.quote;

import static org.testng.Assert.assertTrue;

/**
 * Understands creation and deletion of directories in the file system.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FolderFixture {

  private static Logger logger = Logger.getLogger(FolderFixture.class.getName());
  
  private List<FolderFixture> folders = new ArrayList<FolderFixture>();
  private List<FileFixture> files = new ArrayList<FileFixture>();
  
  final String name;
  final FolderFixture parent;
  
  private File dir;
  
  public FolderFixture(String name) {
    this(name, null);
  }

  public FolderFixture(String name, FolderFixture parent) {
    this.name = name;
    this.parent = parent;
    create();
  }
  
  private void create() {
    String path = relativePath();
    dir = new File(path);
    if (!dir.exists()) {
      assertTrue(dir.mkdir(), "Unable to create directory " + quote(path));
      logger.info("Created directory " + quote(path));
      return;
    }
    assertTrue(dir.isDirectory(), "The file " + quote(path) + " should be a directory");
    logger.info("The directory " + quote(path) + " already exists");
  }
  
  public FolderFixture addFolder(String name) {
    FolderFixture child = new FolderFixture(name, this);
    folders.add(child);
    return child;
  }
  
  public FolderFixture addFiles(String... names) throws IOException {
    for (String name : names) files.add(new FileFixture(name, this));
    return this;
  }
  
  public void delete() {
    for (FolderFixture folder : folders) folder.delete();
    for (FileFixture file : files) file.delete();
    String path = relativePath();
    assertTrue(dir.delete(), "Unable to delete directory " + quote(path));
    logger.info("The directory " + quote(path) + " was deleted");
  }
  
  String relativePath() {
    return parent != null ? parent.relativePath() + separator + name : name;
  }

  public FolderFixture folder(String path) {
    String[] names = path.split(separatorAsRegEx());
    if (isEmpty(names)) return null;
    int i = 0;
    if (!name.equals(names[i++])) return null;
    FolderFixture current = this;
    for (; i < names.length; i++) {
      current = current.childFolder(names[i]);
      if (current == null) break;
    }
    return current;
  }
  
  private FolderFixture childFolder(String name) {
    for (FolderFixture folder : folders)
      if (folder.name.equals(name)) return folder;
    return null;
  }
  
  private String separatorAsRegEx() {  
    String regex = separator;
    if ("\\".equals(regex)) regex = "\\\\";
    return regex;
  }
}
