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
import java.util.Arrays;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.AggregateTransformer;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.util.Files.*;

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
  
//  @Test(dependsOnMethods = "shouldCreateClassPathUsingProject")
//  public void shouldCreateTransformerWithCurrentClasspath() {
//    File temporaryFolder = temporaryFolder();
//    FileSet fileSet = new FileSet();
//    fileSet.setDir(temporaryFolder);
//    fileSet.setIncludes("*.*");
//    Path cp = task.createClasspath();
//    cp.addFileset(fileSet);
//    AggregateTransformer transformer = task.createReport();
//    assertThat(transformer).isInstanceOf(ReportTransformer.class);
//    ReportTransformer reportTransformer = (ReportTransformer)transformer;
//    System.out.println(Arrays.toString(temporaryFolder.list()));
//    System.out.println(Arrays.toString(reportTransformer.createClasspath().list()));
//    ///assertThat(reportTransformer.createClasspath().list()).isEqualTo(temporaryFolder.list());
//  }
}
