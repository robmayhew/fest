package org.fest.swing.groovy;

import java.awt.Frame
import javax.swing.JFrame

import org.testng.annotations.BeforeMethod
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test

import org.fest.swing.RobotFixture

class FrameFixtureFactoryTest {

  private RobotFixture robot
  private SwingFixtureBuilder fixture
  private JFrame frame
   
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy()
    fixture = new SwingFixtureBuilder(robot)
    frame = new JFrame()
  } 
  
  @Test public void shouldShowFrame() {
    fixture.frame(frame) {
      show()
      assert frame.visible
    } 
  }
  
  @Test public void shouldShowFrameWithGivenSize() {
    fixture.frame(frame) {
      show(width: 400, height: 200)
      assert frame.visible
      assert frame.width == 400
      assert frame.height == 200
    }
  }
  
  @Test(dependsOnMethods = ['shouldShowFrame'])
  public void shouldMaximizeFrame() {
    fixture.frame(frame) {
      show()
      maximize()
      assert (frame.extendedState & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH
    }
  }
  
  @Test(dependsOnMethods = ['shouldShowFrame', 'shouldMaximizeFrame'])
  public void shouldNormalizeFrame() {
    fixture.frame(frame) {
      show()
      maximize()
      normalize()
      assert frame.extendedState == Frame.NORMAL
    }
  }
  
  @AfterMethod public void tearDown() {
	robot.cleanUp()    
  }
}