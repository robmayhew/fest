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
   * @throws RuntimeException if this method cannot find or create the system's temporary folder.
   */
  public static File temporaryFolder() {
    File temp = new File(temporaryFolderPath());
    if (!temp.isDirectory()) throw new RuntimeException("Unable to find temporary folder");
    return temp;
  }
  
  /**
   * @return the path of the system's temporary folder. This method appends the system's file separator at the end of
   *          the returned path.
   */
  public static String temporaryFolderPath() {
    return append(separator).to(System.getProperty("java.io.tmpdir"));
  }
  
  private Files() {}

}
