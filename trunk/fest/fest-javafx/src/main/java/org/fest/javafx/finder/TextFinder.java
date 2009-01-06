package org.fest.javafx.finder;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGParent;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.fx.FXNode;

public final class TextFinder {
	public static FXNode nodeWithTextBox(SGParent root) {
		for (SGNode child : root.getChildren()) {
			if (child instanceof FXNode) {
				FXNode fxNode = (FXNode) child;
				SGNode leaf = fxNode.getLeaf();
				if (leaf instanceof SGText)
					return fxNode;
			}
			if (child instanceof SGParent) {
				SGParent newRoot = (SGParent) child;
				FXNode fxNode = nodeWithTextBox(newRoot);
				if (fxNode != null)
					return fxNode;
			}
		}
		return null;
	}
}
