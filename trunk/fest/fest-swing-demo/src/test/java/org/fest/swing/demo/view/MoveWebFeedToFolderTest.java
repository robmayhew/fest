package org.fest.swing.demo.view;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.demo.model.WebFeed;
import org.fest.swing.demo.service.Services;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JPopupMenuFixture;
import org.fest.swing.fixture.JTreeFixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.demo.main.Main.updateLookAndFeel;
import static org.fest.swing.demo.service.ServiceStubs.setUpServices;
import static org.fest.swing.finder.WindowFinder.findDialog;
import static org.fest.swing.timing.Pause.pause;

public class MoveWebFeedToFolderTest {

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

  @Test public void shouldUpdateDatabaseWhenMovingWebFeedToDifferentFolder() {
    JTreeFixture feedsTree = mainFrame.tree("feeds");
    JPopupMenuFixture rootPopupMenu = feedsTree.showPopupMenuAt("Web Feeds");
    rootPopupMenu.menuItem("add").click();
    addDiscoBlogWebFeed();
    mainFrame.button("add").click();
    addGroovyFolder();
    feedsTree.drag("Web Feeds/Testing/The Disco Blog")
             .drop("Web Feeds/Groovy");
    WebFeed feed = Services.instance().webFeedService().webFeedWithName("The Disco Blog");
    assertThat(feed.folder().name()).isEqualTo("Groovy");
  }

  private void addDiscoBlogWebFeed() {
    DialogFixture addDialog = findDialog("add").withTimeout(1000).using(mainFrame.robot);
    addDialog.textBox("address").enterText("http://www.thediscoblog.com");
    addDialog.textBox("name").enterText("The Disco Blog");
    addDialog.comboBox("folder").enterText("Testing");
    addDialog.button("ok").click();
    pause(1000);
  }

  private void addGroovyFolder() {
    DialogFixture addDialog = findDialog("add").withTimeout(1000).using(mainFrame.robot);
    addDialog.toggleButton("addFolder").click();
    addDialog.textBox("folderName").enterText("Groovy");
    addDialog.button("ok").click();
    pause(1000);
  }
}
