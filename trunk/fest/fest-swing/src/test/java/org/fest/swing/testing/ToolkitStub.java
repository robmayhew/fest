/*
 * Created on Mar 22, 2008
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
package org.fest.swing.testing;

import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.event.AWTEventListener;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.peer.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Understands a stub of <code>{@link Toolkit}</code>.
 * 
 * @author Alex Ruiz
 */
public class ToolkitStub extends Toolkit {

  private final Map<AWTEventListener, Long> eventListeners = new HashMap<AWTEventListener, Long>();
  private final EventQueue eventQueue;
  
  public ToolkitStub() {
    this(new EventQueue());
  }

  public ToolkitStub(EventQueue eventQueue) {
    this.eventQueue = eventQueue;
  }
  
  @Override public void addAWTEventListener(AWTEventListener listener, long eventMask) {
    eventListeners.put(listener, eventMask);
  }

  @Override public void removeAWTEventListener(AWTEventListener listener) {
    eventListeners.remove(listener);
  }

  public <T extends AWTEventListener> java.util.List<T> eventListenersUnderEventMask(long eventMask, Class<T> type) {
    java.util.List<T> listeners = new ArrayList<T>();
    for (AWTEventListener listener : eventListeners.keySet()) { 
      if (!type.isInstance(listener)) continue;
      if (eventListeners.get(listener).longValue() != eventMask) continue;
      listeners.add(type.cast(listener));
    }
    return listeners;
  }
  
  public boolean contains(AWTEventListener listener, long eventMask) {
    if (!eventListeners.containsKey(listener)) return false;
    long storedMask = eventListeners.get(listener);
    return storedMask == eventMask;
  }
  
  protected EventQueue getSystemEventQueueImpl() {
    return eventQueue;
  }
  
  public void beep() {}

  public int checkImage(Image image, int width, int height, ImageObserver observer) {
    return 0;
  }

  protected ButtonPeer createButton(Button target) throws HeadlessException {
    return null;
  }

  protected CanvasPeer createCanvas(Canvas target) {
    return null;
  }

  protected CheckboxPeer createCheckbox(Checkbox target) throws HeadlessException {
    return null;
  }

  protected CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem target) throws HeadlessException {
    return null;
  }

  protected ChoicePeer createChoice(Choice target) throws HeadlessException {
    return null;
  }

  protected DesktopPeer createDesktopPeer(Desktop target) throws HeadlessException {
    return null;
  }

  protected DialogPeer createDialog(Dialog target) throws HeadlessException {
    return null;
  }

  public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) throws InvalidDnDOperationException {
    return null;
  }

  protected FileDialogPeer createFileDialog(FileDialog target) throws HeadlessException {
    return null;
  }

  protected FramePeer createFrame(Frame target) throws HeadlessException {
    return null;
  }

  public Image createImage(String filename) {
    return null;
  }

  public Image createImage(URL url) {
    return null;
  }

  public Image createImage(ImageProducer producer) {
    return null;
  }

  public Image createImage(byte[] imagedata, int imageoffset, int imagelength) {
    return null;
  }

  protected LabelPeer createLabel(Label target) throws HeadlessException {
    return null;
  }

  protected MenuPeer createMenu(Menu target) throws HeadlessException {
    return null;
  }

  protected MenuBarPeer createMenuBar(MenuBar target) throws HeadlessException {
    return null;
  }

  protected MenuItemPeer createMenuItem(MenuItem target) throws HeadlessException {
    return null;
  }

  protected PanelPeer createPanel(Panel target) {
    return null;
  }

  protected PopupMenuPeer createPopupMenu(PopupMenu target) throws HeadlessException {
    return null;
  }

  protected ScrollPanePeer createScrollPane(ScrollPane target) throws HeadlessException {
    return null;
  }

  protected ScrollbarPeer createScrollbar(Scrollbar target) throws HeadlessException {
    return null;
  }

  protected TextAreaPeer createTextArea(TextArea target) throws HeadlessException {
    return null;
  }

  protected TextFieldPeer createTextField(TextField target) throws HeadlessException {
    return null;
  }

  protected WindowPeer createWindow(Window target) throws HeadlessException {
    return null;
  }

  public ColorModel getColorModel() throws HeadlessException {
    return null;
  }

  public String[] getFontList() {
    return null;
  }

  public FontMetrics getFontMetrics(Font font) {
    return null;
  }

  protected FontPeer getFontPeer(String name, int style) {
    return null;
  }

  public Image getImage(String filename) {
    return null;
  }

  public Image getImage(URL url) {
    return null;
  }

  public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props) {
    return null;
  }

  public int getScreenResolution() throws HeadlessException {
    return 0;
  }

  public Dimension getScreenSize() throws HeadlessException {
    return null;
  }

  public Clipboard getSystemClipboard() throws HeadlessException {
    return null;
  }

  public boolean isModalExclusionTypeSupported(ModalExclusionType modalExclusionType) {
    return false;
  }

  public boolean isModalityTypeSupported(ModalityType modalityType) {
    return false;
  }

  public Map<TextAttribute, ?> mapInputMethodHighlight(InputMethodHighlight highlight) throws HeadlessException {
    return null;
  }

  public boolean prepareImage(Image image, int width, int height, ImageObserver observer) {
    return false;
  }

  public void sync() {}

  @Override protected ListPeer createList(List target) throws HeadlessException {
    return null;
  }
}
