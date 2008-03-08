/*
 * Created on Mar 7, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.demo.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImageOp;

import javax.swing.JComponent;

import org.jdesktop.swinghelper.layer.JXLayer;
import org.jdesktop.swinghelper.layer.effect.Effect;
import org.jdesktop.swinghelper.layer.effect.ImageOpEffect;
import org.jdesktop.swinghelper.layer.painter.BufferedPainter;

/**
 * Adapted from <code>LockedLayerDemo</code> from <a href="https://swinghelper.dev.java.net/" target="_blank">JXLayer
 * Project</a>.
 * @param <V> the type of component decorated by a <code>JXLayer</code>.
 * 
 * @author Alex Ruiz
 */
class ImageOpPainter<V extends JComponent> extends BufferedPainter<V> {
  private final Effect disablingEffect;

  public ImageOpPainter(BufferedImageOp disablingEffect) {
    this.disablingEffect = new ImageOpEffect(disablingEffect);
  }

  @Override protected void paintToBuffer(Graphics2D g2, JXLayer<V> layer) {
    super.paintToBuffer(g2, layer);
    if (layer.isLocked()) {
      // the view is hidden, so we need to paint it manually, it is important to call print() instead of paint() here
      // because print() doesn't affect the frame's double buffer
      layer.getView().print(g2);
      disablingEffect.apply(getBuffer(), g2.getClip());
    }
  }

  // Child components of a locked layer are locked as well, so we can speed the painting up. Moreover EmbossFilter 
  // doesn't like incremental updates
  @Override public boolean isIncrementalUpdate(JXLayer<V> l) {
    return !l.isLocked();
  }
}