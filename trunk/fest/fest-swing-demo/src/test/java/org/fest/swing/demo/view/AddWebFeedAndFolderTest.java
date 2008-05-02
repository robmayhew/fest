package org.fest.swing.demo.view;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTableCellFixture;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.demo.main.Main.updateLookAndFeel;
import static org.fest.swing.demo.service.ServiceStubs.setUpServices;
import static org.fest.swing.finder.WindowFinder.findDialog;
import static org.fest.swing.fixture.TableCell.row;

public class AddWebFeedAndFolderTest {

  private FrameFixture mainFrame;
  
  @BeforeMethod public void setUp() throws Exception {
    updateLookAndFeel();
    setUpServices();
    mainFrame = new FrameFixture(new MainFrame());
    mainFrame.show();
  }
  
  @AfterMethod public void tearDown() {
    mainFrame.cleanUp();
  }
  
  @Test public void shouldAddNewWebFeedAndFolder() {
    mainFrame.button("add").click();
    DialogFixture addDialog = findDialog("add").withTimeout(1000).using(mainFrame.robot);
    addDialog.textBox("address").enterText("http://www.thediscoblog.com");
    addDialog.textBox("name").enterText("The Disco Blog");
    addDialog.comboBox("folder").enterText("Testing");
    addDialog.button("ok").click();
    pause(1000);
    mainFrame.tree("feeds").requireSelection("Web Feeds/Testing/The Disco Blog");
    JTableCellFixture firstCell = mainFrame.table("webFeedItems").cell(row(0).column(0));
    firstCell.requireContent("The weekly bag– April 18");
  }
}
