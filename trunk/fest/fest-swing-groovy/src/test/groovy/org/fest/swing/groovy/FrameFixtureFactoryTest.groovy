package org.fest.swing.groovy;

import org.testng.annotations.BeforeMethod
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test

import javax.swing.JFrame

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
  
  @Test public void shouldCreateShowFrame() throws Exception {
    fixture.frame(frame) {
      show 
    } 
    Thread.sleep(5000)
  }

  @AfterMethod public void tearDown() {
	robot.cleanUp()    
  }
}