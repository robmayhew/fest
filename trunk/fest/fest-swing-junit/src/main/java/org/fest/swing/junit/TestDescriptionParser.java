/*
 * Created on Nov 19, 2007
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.Description;

import org.fest.swing.annotation.GUITestFinder;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands a parser for <code>{@link Description}</code> objects describing test methods. Due to limitations of
 * JUnit, this parser only supports test methods that do not have parameters.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDescriptionParser {

  private static final Pattern DISPLAY_NAME_PATTERN = Pattern.compile("([a-z_A-z0-9]+)\\(([a-z_A-z0-9\\.]+)\\)");

  private static Logger logger = Logger.getLogger(TestDescriptionParser.class.getName());

  /**
   * Parses the display name of the given description, returning as result the test class, the test method and whether 
   * the test method is a GUI test or not.
   * @param description the description to parse.
   * @return the test class, the test method and whether the test method is a GUI test or not.
   */
  public ParseResult parse(Description description) {
    if (!description.isTest()) return null;
    String displayName = description.getDisplayName();
    Matcher matcher = DISPLAY_NAME_PATTERN.matcher(displayName);
    if (!matcher.matches()) return null;
    try {
      return createResult(matcher.group(2), matcher.group(1));
    } catch (Exception e) {
      logger.log(Level.WARNING, concat("Unable to parse description ", quote(displayName)), e);
      return null;
    }
  }

  private ParseResult createResult(String testClassName, String testMethodName) throws Exception {
    Class<?> testClass = Class.forName(testClassName);
    Method testMethod = testClass.getDeclaredMethod(testMethodName);
    return new ParseResult(testClass, testMethod);
  }
  
  /**
   * Understands the result of parsing a <code>{@link Description}</code>.
   * 
   * @author Alex Ruiz
   * @author Yvonne Wang
   */
  public static class ParseResult {

    private final Class<?> testClass;
    private final Method testMethod;
    private final boolean isGUITest;

    /**
     * Creates a new <code>{@link ParseResult}</code>.
     * @param testClass the test class obtained when parsing a <code>Description</code>.
     * @param testMethod the test method obtained when parsing a <code>Description</code>.
     */
    public ParseResult(Class<?> testClass, Method testMethod) {
      this.testClass = testClass;
      this.testMethod = testMethod;
      isGUITest = GUITestFinder.isGUITest(testClass, testMethod);
    }

    /**
     * Returns the test class obtained when parsing a <code>Description</code>.
     * @return the test class obtained when parsing a <code>Description</code>.
     */
    public final Class<?> testClass() { return testClass; }

    /**
     * Returns the test method obtained when parsing a <code>Description</code>.
     * @return the test method obtained when parsing a <code>Description</code>.
     */
    public final Method testMethod() { return testMethod; }
    
    /**
     * Returns whether the test method is a GUI test or not.
     * @return whether the test method is a GUI test or not.
     * @see GUITestFinder#isGUITest(Class, Method)
     */
    public final boolean isGUITest() { return isGUITest; }
    
  }
}
