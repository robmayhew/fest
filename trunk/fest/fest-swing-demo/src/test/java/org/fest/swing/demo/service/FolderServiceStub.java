/*
 * Created on Apr 20, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.fest.swing.demo.model.Folder;

public final class FolderServiceStub implements FolderService {
  private final List<Folder> folders = new ArrayList<Folder>();

  public void saveFolder(Folder folder) {
    folders.add(folder);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Folder[] allFolders() {
    Folder[] sortedFolders = folders.toArray(new Folder[folders.size()]);
    Arrays.sort(sortedFolders, new Comparator<Folder>() {
      public int compare(Folder f1, Folder f2) {
        return f1.name().compareTo(f2.name());
      }
    });
    return sortedFolders;
  }
}