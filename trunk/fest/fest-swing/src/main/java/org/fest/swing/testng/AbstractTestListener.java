/*
 * Created on May 6, 2007
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
package org.fest.swing.testng;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Understands a base class for TestNG listeners.
 *
 * @author Alex Ruiz
 */
public abstract class AbstractTestListener implements ITestListener {

  /** {@inheritDoc} */
  public void onFinish(ITestContext context) {}

  /** {@inheritDoc} */
  public void onStart(ITestContext context) {}

  /** {@inheritDoc} */
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  /** {@inheritDoc} */
  public void onTestFailure(ITestResult result) {}

  /** {@inheritDoc} */
  public void onTestSkipped(ITestResult result) {}

  /** {@inheritDoc} */
  public void onTestStart(ITestResult result) {}

  /** {@inheritDoc} */
  public void onTestSuccess(ITestResult result) {}

}
