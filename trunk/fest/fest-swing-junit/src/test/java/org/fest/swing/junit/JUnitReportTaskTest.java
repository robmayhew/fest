/*
 * Created on Jul 20, 2007
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
package org.fest.swing.junit;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.AggregateTransformer;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.resources.Union;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;
import static org.fest.util.Files.temporaryFolder;

/**
 * Tests for <code>{@link JUnitReportTask}</code>.
 *
 * @author Alex Ruiz
 */
public class JUnitReportTaskTest {

  private JUnitReportTask task;
  private Project project;
  
  @BeforeMethod public void setUp() {
    project = new Project();
    task = new JUnitReportTask();
    task.setProject(project);
  }
  
  @Test public void shouldCreateClassPathUsingProject() {
    Path classpath = task.createClasspath();
    assertThat(classpath.getProject()).isSameAs(project);
  }
  
  @Test(dependsOnMethods = "shouldCreateClassPathUsingProject")
  public void shouldCreateTransformerWithCurrentClasspath() {
    File temporaryFolder = temporaryFolder();
    task.createClasspath().addFileset(allFilesIn(temporaryFolder));
    AggregateTransformer transformer = task.createReport();
    assertThat(transformer).isInstanceOf(ReportTransformer.class);
    Path expected = field("classpath").ofType(Path.class).in(task).get();
    assertClasspathAdded((ReportTransformer)transformer, expected);
  }
  
  private FileSet allFilesIn(File temporaryFolder) {
    FileSet fileSet = new FileSet();
    fileSet.setDir(temporaryFolder);
    fileSet.setIncludes("*.*");
    return fileSet;
  }

  private void assertClasspathAdded(ReportTransformer target, Path expected) {
    Path targetPath = field("classpath").ofType(Path.class).in(target).get();
    Union union = field("union").ofType(Union.class).in(targetPath).get();
    List<?> list = field("rc").ofType(List.class).in(union).get();
    assertThat(list).hasSize(1);
    Object mayBePath = list.get(0);
    assertThat(mayBePath).isInstanceOf(Path.class);
    Path actual = (Path)mayBePath;
    assertThat(actual.list()).isEqualTo(expected.list());
  }
}
