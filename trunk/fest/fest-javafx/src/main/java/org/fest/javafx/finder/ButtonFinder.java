package org.fest.javafx.finder;

import java.awt.Component;

import javax.swing.JButton;

import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGParent;
import com.sun.scenario.scenegraph.fx.FXNode;

public final class ButtonFinder {
	public static FXNode nodeWithButton(String text, SGParent root) {
		for (SGNode child : root.getChildren()) {
			if (child instanceof FXNode) {
				FXNode fxNode = (FXNode) child;
				SGNode leaf = fxNode.getLeaf();
				if (leaf instanceof SGComponent) {
					SGComponent componentNode = (SGComponent) leaf;
					Component component = componentNode.getComponent();
					if (component instanceof JButton) {
						JButton button = (JButton) component;
						if (text.equals(button.getText()))
							return fxNode;
					}
				}
			}
			if (child instanceof SGParent) {
				SGParent newRoot = (SGParent) child;
				FXNode fxNode = nodeWithButton(text, newRoot);
				if (fxNode != null)
					return fxNode;
			}
		}
		return null;
	}
}
