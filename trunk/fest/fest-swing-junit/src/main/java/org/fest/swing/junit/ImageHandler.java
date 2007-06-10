/*
 * Created on Jun 8, 2007
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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import static java.util.logging.Level.SEVERE;

import static org.fest.swing.util.ScreenshotTaker.PNG_EXTENSION;
import static org.fest.util.Files.flushAndClose;

/**
 * Understands base64 encoding and decoding of an image.
 *
 * @author Alex Ruiz
 */
public final class ImageHandler {

  private static Logger logger = Logger.getAnonymousLogger();
  
  public static String encodeBase64(BufferedImage image) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, PNG_EXTENSION, out);
      byte[] encoded = Base64.encodeBase64(out.toByteArray());
      return new String(encoded, "UTF-8");
    } catch (IOException e) {
      logger.log(SEVERE, "Unable to encode image", e);
      return null;
    } finally {
      flushAndClose(out);
    }
  }

  public static BufferedImage decodeBase64(String encoded) {
    ByteArrayInputStream in = null;
    try {
      byte[] toDecode = encoded.getBytes("UTF-8");
      in = new ByteArrayInputStream(Base64.decodeBase64(toDecode));
      return ImageIO.read(in);
    } catch (IOException e) {
      logger.log(SEVERE, "Unable to decode image", e);
      return null;
    } 
  }
  
  private ImageHandler() {}
}
