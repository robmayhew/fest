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
  private JFrame myFrame
   
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy()
    fixture = new SwingFixtureBuilder(robot)
    myFrame = new JFrame()
  } 
  
  @Test public void shouldShowFrame() {
    fixture.frame(myFrame) {
      show()
    } 
    assert myFrame.visible
  }
  
  @Test public void shouldShowFrameWithGivenSize() {
    fixture.frame(myFrame) {
      show(width: 400, height: 200)
    }
    assert myFrame.visible
    assert myFrame.width.equals(400)
    assert myFrame.height.equals(200)
  }
  
  @Test(dependsOnMethods = ['shouldShowFrame'])
  public void shouldMaximizeFrame() {
    fixture.frame(myFrame) {
      show()
      maximize()
    }
    assert (myFrame.extendedState & Frame.MAXIMIZED_BOTH).equals(Frame.MAXIMIZED_BOTH)
  }
  
  @Test(dependsOnMethods = ['shouldShowFrame', 'shouldMaximizeFrame'])
  public void shouldNormalizeFrame() {
    fixture.frame(myFrame) {
      show()
      maximize()
      normalize()
    }
    assert myFrame.extendedState.equals(Frame.NORMAL)
  }
  
  @AfterMethod public void tearDown() {
	robot.cleanUp()    
  }
}