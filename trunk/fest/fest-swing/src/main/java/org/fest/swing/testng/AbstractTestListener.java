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

  /** @see org.testng.ITestListener#onFinish(org.testng.ITestContext) */
  public void onFinish(ITestContext context) {}

  /** @see org.testng.ITestListener#onStart(org.testng.ITestContext) */
  public void onStart(ITestContext context) {}

  /** @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult) */
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  /** @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult) */
  public void onTestFailure(ITestResult result) {}

  /** @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult) */
  public void onTestSkipped(ITestResult result) {}

  /** @see org.testng.ITestListener#onTestStart(org.testng.ITestResult) */
  public void onTestStart(ITestResult result) {}

  /** @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult) */
  public void onTestSuccess(ITestResult result) {}

}
