/*
 * Created on Dec 19, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.core;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.StopWatch;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Timeout.timeout;
import static org.fest.swing.testing.StopWatch.startNewStopWatch;

/**
 * Tests for <code>{@link Pause}</code>.
 *
 * @author Alex Ruiz
 */
public class PauseTest {

  @Test public void shouldSleepForTheGivenTime() {
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    Pause.pause(delay);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
  }
  
  @Test public void shouldSleepForTheGivenTimeInUnits() {
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    Pause.pause(2, TimeUnit.SECONDS);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfUnitIsNull() {
    Pause.pause(2, null);
  }

  @Test public void shouldWaitTillConditionIsTrue() {
    class CustomCondition extends Condition {
      boolean satisfied;
      
      public CustomCondition(String description) {
        super(description);
      }

      @Override public boolean test() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        satisfied = true;
        return satisfied;
      }
    };
    CustomCondition condition = new CustomCondition("Some condition");
    StopWatch watch = startNewStopWatch();
    Pause.pause(condition);
    watch.stop();
    assertThat(watch.ellapsedTime() >= 1000).isTrue();
    assertThat(condition.satisfied).isTrue();
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeoutIfConditionIsNeverTrue() {
    Pause.pause(neverSatisfied());
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeoutWithGivenTimeIfConditionIsNeverTrue() {
    Pause.pause(neverSatisfied(), timeout(100));
  }
  
  private NeverSatisfiedCondition neverSatisfied() {
    return new NeverSatisfiedCondition("Never satisfied");
  }
  
  private static class NeverSatisfiedCondition extends Condition {
    public NeverSatisfiedCondition(String description) {
      super(description);
    }

    @Override public boolean test() {
      return false;
    }
  };    

}
