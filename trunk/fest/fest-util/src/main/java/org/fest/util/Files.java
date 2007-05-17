/*
 * Created on Sep 23, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Strings.append;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands utility methods related to files.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Files {

  /**
   * Returns the names of the files inside the specified directory.
   * @param dirName the name of the directory to start the search from.
   * @param recurse if <code>true</code>, we will look in subdirectories.
   * @return the names of the files inside the specified directory.
   * @throws IllegalArgumentException if the given directory name does not point to an existing directory.
   */
  public static List<String> fileNamesIn(String dirName, boolean recurse) {
    File dir = new File(dirName);
    if (!dir.isDirectory()) 
      throw new IllegalArgumentException(concat(quote(dirName), " is not a directory or does not exist"));
    return fileNamesIn(dir, recurse);
  }

  /**
   * Returns the names of the files inside the specified directory.
   * @param dir the name of the directory to start the search from.
   * @param recurse if <code>true</code>, we will look in subdirectories.
   * @return the names of the files inside the specified directory.
   */
  private static List<String> fileNamesIn(File dir, boolean recurse) {
    List<String> scriptNames = new ArrayList<String>();
    File[] existingFiles = dir.listFiles();
    if (isEmpty(existingFiles)) return scriptNames;
    for (File existingFile : existingFiles) {
      if (existingFile.isDirectory()) {
        if (recurse) scriptNames.addAll(fileNamesIn(existingFile, recurse));
        continue;
      }
      String filename = existingFile.getAbsolutePath();
      if (!scriptNames.contains(filename)) scriptNames.add(filename);
    }
    return scriptNames;
  }

  /**
   * @return the system's temporary folder. 
   * @throws FilesException if this method cannot find or create the system's temporary folder.
   */
  public static File temporaryFolder() {
    File temp = new File(temporaryFolderPath());
    if (!temp.isDirectory()) throw new FilesException("Unable to find temporary folder");
    return temp;
  }
  
  /**
   * @return the path of the system's temporary folder. This method appends the system's file separator at the end of
   *          the returned path.
   */
  public static String temporaryFolderPath() {
    return append(separator).to(System.getProperty("java.io.tmpdir"));
  }

  /**
   * Creates a new file using the given path.
   * @param path the path of the new file.
   * @return the new created file.
   * @throws FilesException if the path belongs to an existing non-empty directory.
   * @throws FilesException if the path belongs to an existing file.
   * @throws FilesException if any I/O error is thrown when creating the new file.
   */
  public static File newFile(String path) {
    File file = new File(path);
    if (file.isDirectory() && !isEmpty(file.list()))
      throw cannotCreateNewFile(path, "a non-empty directory was found with the same path");
    try {
      if (!file.createNewFile()) throw cannotCreateNewFile(path, "a file was found with the same path");
    } catch (IOException e) {
      throw cannotCreateNewFile(path, e);
    }
    return file;
  }

  private static FilesException cannotCreateNewFile(String path, String reason) {
    throw cannotCreateNewFile(path, reason, null);
  }

  private static FilesException cannotCreateNewFile(String path, Exception cause) {
    throw cannotCreateNewFile(path, null, cause);
  }

  private static FilesException cannotCreateNewFile(String path, String reason, Exception cause) {
    String message = concat("Unable to create the new file ", quote(path));
    if (!Strings.isEmpty(reason)) message = concat(message, ": ", reason);
    if (cause != null) throw new FilesException(message, cause);
    throw new FilesException(message);
  }
  
  private Files() {}

}
