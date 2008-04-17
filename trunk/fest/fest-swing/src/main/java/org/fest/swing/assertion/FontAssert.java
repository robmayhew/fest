/*
 * Created on Apr 16, 2008
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
package org.fest.swing.assertion;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.*;

import java.awt.Font;

import org.fest.assertions.AssertExtension;

/**
 * Understands assertion methods for <code>{@link Font}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontAssert implements AssertExtension {

  private static final String PROPERTY_SEPARATOR = " - ";
  private static final String BOLD_PROPERTY = "bold";
  private static final String FAMILY_PROPERTY = "family";
  private static final String ITALIC_PROPERTY = "italic";
  private static final String NAME_PROPERTY = "name";
  private static final String PLAIN_PROPERTY = "plain";
  private static final String SIZE_PROPERTY = "size";

  private final Font actual;

  private String description;

  public FontAssert(Font actual) {
    this.actual = actual;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(font).<strong>as</strong>(&quot;name&quot;).isEqualTo(&quot;Frodo&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FontAssert as(String description) {
    this.description = description;
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(val).<strong>describedAs</strong>(&quot;name&quot;).isEqualTo(&quot;Frodo&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FontAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the family name of the actual font is equal to the given one.
   * @param family the expected family name.
   * @return this assertion object.
   * @throws AssertionError if the family name of the actual font is not equal to the given one.
   * @see Font#getFamily()
   */
  public FontAssert hasFamily(String family) {
    assertThat(actual.getFamily()).as(addProperty(FAMILY_PROPERTY)).isEqualTo(family);
    return this;
  }

  /**
   * Verifies that the logical name of the actual font is equal to the given one.
   * @param name the expected logical name.
   * @return this assertion object.
   * @throws AssertionError if the logical name of the actual font is not equal to the given one.
   * @see Font#getName()
   */
  public FontAssert hasName(String name) {
    assertThat(actual.getName()).as(addProperty(NAME_PROPERTY)).isEqualTo(name);
    return this;
  }

  /**
   * Verifies that the point size of the actual font is equal to the given one.
   * @param size the expected point size.
   * @return this assertion object.
   * @throws AssertionError is the actual font's point size is not equal to the given one.
   * @see Font#getSize()
   */
  public FontAssert hasSize(int size) {
    assertThat(actual.getSize()).as(addProperty(SIZE_PROPERTY)).isEqualTo(size);
    return this;
  }

  /**
   * Verifies that the actual font is equal to the given one.
   * @param font the font to compare to.
   * @return this assertion object.
   * @throws AssertionError if the actual font is not equal to the given one.
   */
  public FontAssert isEqualTo(Font font) {
    assertThat(actual).as(description).isEqualTo(font);
    return this;
  }

  /**
   * Verifies that the actual font is not equal to the given one.
   * @param font the font to compare to.
   * @return this assertion object.
   * @throws AssertionError if the actual font is equal to the given one.
   */
  public FontAssert isNotEqualTo(Font font) {
    assertThat(actual).as(description).isNotEqualTo(font);
    return this;
  }

  /**
   * Verifies that the actual font's object style is bold.
   * @return this assertion object.
   * @throws AssertionError if the actual font's object style is not bold.
   * @see Font#isBold()
   */
  public FontAssert isBold() {
    return isBold(true);
  }

  /**
   * Verifies that the actual font's object style is not bold.
   * @return this assertion object.
   * @throws AssertionError if the actual font's object style is bold.
   * @see Font#isBold()
   */
  public FontAssert isNotBold() {
    return isBold(false);
  }

  private FontAssert isBold(boolean bold) {
    assertThat(actual.isBold()).as(addProperty(BOLD_PROPERTY)).isEqualTo(bold);
    return this;
  }

  /**
   * Verifies that the actual font's object style is italic.
   * @return this assertion object.
   * @throws AssertionError if the actual font's object style is not italic.
   * @see Font#isItalic()
   */
  public FontAssert isItalic() {
    return isItalic(true);
  }

  /**
   * Verifies that the actual font's object style is not italic.
   * @return this assertion object.
   * @throws AssertionError if the actual font's object style is italic.
   * @see Font#isItalic()
   */
  public FontAssert isNotItalic() {
    return isItalic(false);
  }

  private FontAssert isItalic(boolean italic) {
    assertThat(actual.isItalic()).as(addProperty(ITALIC_PROPERTY)).isEqualTo(italic);
    return this;
  }

  /**
   * Verifies that the actual font's object style is plain.
   * @return this assertion object.
   * @throws AssertionError if the actual font's object style is not plain.
   * @see Font#isPlain()
   */
  public FontAssert isPlain() {
    return isPlain(true);
  }

  /**
   * Verifies that the actual font's object style is not plain.
   * @return this assertion object.
   * @throws AssertionError if the actual font's object style is plain.
   * @see Font#isPlain()
   */
  public FontAssert isNotPlain() {
    return isPlain(false);
  }

  private FontAssert isPlain(boolean plain) {
    assertThat(actual.isBold()).as(addProperty(PLAIN_PROPERTY)).isEqualTo(plain);
    return this;
  }

  private String addProperty(String s) {
    if (isEmpty(description)) return s;
    return concat(description, PROPERTY_SEPARATOR, s);
  }
}
