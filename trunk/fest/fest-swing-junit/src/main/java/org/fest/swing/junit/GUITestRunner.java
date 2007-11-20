/*
 * Created on Nov 17, 2007
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TestClassRunner;
import org.junit.internal.runners.TestIntrospector;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

import org.fest.reflect.ReflectionError;
import org.fest.util.Collections;

import static org.fest.reflect.Reflection.field;
import static org.fest.util.TypeFilter.byType;

/**
 * Understands a JUnit 4 test runner that takes a screenshot of a failed GUI test.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestRunner extends Runner {

  private static final List<RunListener> NO_LISTENERS = new ArrayList<RunListener>();
  
  final Runner delegate;
  final List<Method> testMethods;
  final Class<?> testClass;

  /**
   * Creates a new <code>{@link GUITestRunner}</code>.
   * @param testClass the class containing the tests to run.
   * @throws InitializationError if something goes wrong when creating this runner. 
   */
  public GUITestRunner(Class<?> testClass) throws InitializationError  {
    this(testClass, new TestClassRunner(testClass));
  }
  
  GUITestRunner(Class<?> testClass, Runner delegate) {
    this.testClass = testClass;
    this.delegate = delegate;
    testMethods = new TestIntrospector(testClass).getTestMethods(Test.class);
  }
  
  /**
   * Run the tests for this runner, taking screenshots of failing tests.
   * @param notifier will be notified of events while tests are being run, started, finishing, and failing.
   */
  @Override public void run(RunNotifier notifier) {
    addFailedGUITestListenerTo(notifier);
    delegate.run(notifier);
    removeFailedGUITestListenersFrom(notifier);
  }

  private void addFailedGUITestListenerTo(RunNotifier notifier) {
    if (!failedGUITestListenersIn(notifier).isEmpty()) return;
    notifier.addListener(new FailedGUITestListener());
  }

  private void removeFailedGUITestListenersFrom(RunNotifier notifier) {
    for(RunListener listener : failedGUITestListenersIn(notifier))
      notifier.removeListener(listener);
  }
  
  private List<? extends RunListener> failedGUITestListenersIn(RunNotifier notifier) {
    List<RunListener> listeners = listenersIn(notifier);
    if (listeners.isEmpty()) return NO_LISTENERS;
    return Collections.filter(listeners, byType(FailedGUITestListener.class));
  }
  
  @SuppressWarnings("unchecked") 
  private List<RunListener> listenersIn(RunNotifier notifier) {
    try {
      return field("fListeners").ofType(List.class).in(notifier).get();
    } catch (ReflectionError e) {
      return NO_LISTENERS;
    }
  }

  /**
   * Returns a <code>{@link Description}</code> showing the tests to be run by the receiver.
   * @return a <code>Description</code> showing the tests to be run by the receiver.
   */
  @Override public Description getDescription() {
    return delegate.getDescription();
  }
}
