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
package org.fest.swing.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.fest.swing.util.Objects.isEmpty;
import static org.fest.swing.util.Strings.quote;

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
    if (!isDirectory(dir))
      throw new IllegalArgumentException(quote(dirName) + " is not a directory or does not exist");
    return fileNamesIn(dir, recurse);
  }

  private static boolean isDirectory(File dir) {
    return dir.exists() && dir.isDirectory();
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

  private Files() {}

}
