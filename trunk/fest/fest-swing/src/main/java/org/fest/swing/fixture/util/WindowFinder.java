/*
 * Created on Jul 30, 2007
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
package org.fest.swing.fixture.util;

import java.awt.Component;
import java.awt.Frame;

import static java.lang.System.currentTimeMillis;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import org.fest.swing.ComponentFinder;
import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.FrameFixture;

/**
 * Understands lookup of <code>{@link Frame}</code>s and <code>{@link java.awt.Dialog}</code>s.
 *
 * @author Alex Ruiz
 */
public final class WindowFinder {
 
  private WindowFinder() {}

  public static FinderBuilder findFrame(String name) {
    return new FinderBuilder(name);
  }

  public static class FinderBuilder {
    private static final long TIMEOUT = 5000;
    
    private final String componentName;
    private long timeout = TIMEOUT;
    
    public FinderBuilder(String componentName) {
      this.componentName = componentName;
    }

    public FinderBuilder timeout(long timeout) {
      this.timeout = timeout;
      return this;
    }

    public FrameFixture with(RobotFixture robot) {
      ComponentFinder finder = robot.finder();
      ComponentMatcher matcher = frameMatcher();
      long start = currentTimeMillis();
      int i = 0;
      while (true) {
        System.out.println(++i);
        Component c = null;
        try {
          c = finder.find(matcher);
        } catch (ComponentLookupException e) {
          if (isTimeout(start)) throw cannotFindFrame();
          sleep();
          continue;
        }
        return new FrameFixture(robot, (Frame)c);
      }
    }

    private ComponentMatcher frameMatcher() {
      ComponentMatcher matcher = new ComponentMatcher() {
        public boolean matches(Component c) {
          return c instanceof Frame && componentName.equals(c.getName());
        }
      };
      return matcher;
    }
    
    private boolean isTimeout(long startTime) {
      return (currentTimeMillis() - startTime) >= timeout;
    }
    
    private ComponentLookupException cannotFindFrame() {
      throw new ComponentLookupException(concat("Cannot find frame with name ", quote(componentName)));
    }

    private void sleep() {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
