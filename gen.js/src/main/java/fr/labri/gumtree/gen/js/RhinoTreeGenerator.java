/*
 * Copyright 2011 Jean-Rémy Falleri
 * 
 * This file is part of Praxis.
 * Praxis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Praxis is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Praxis.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.labri.gumtree.gen.js;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;

import fr.labri.gumtree.io.TreeGenerator;
import fr.labri.gumtree.tree.Tree;

public class RhinoTreeGenerator extends TreeGenerator {
	
	private Map<AstNode, Tree> treeNodeMap;

	public Tree generate(String file) {
		Parser p = new Parser();
		try {
			AstRoot root = p.parse(new FileReader(file), file, 1);
			RhinoTreeVisitor visitor = new RhinoTreeVisitor(root);
			root.visit(visitor);
			this.treeNodeMap = visitor.getTreeNodeMap();
			return visitor.getTree();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This provides clients with efficient access to Tree nodes if they have 
	 * AstNodes. For example, a client may be  working with AstNodes instead
	 * of the Tree nodes (AstNodes provide more data and functionality). This
	 * will allow them to get the GumTree data for each AstNode without having
	 * to perform an inefficient search.
	 * @return The map of each AstNode to the Tree node that it created.
	 */
	public Map<AstNode, Tree> getTreeNodeMap() {
		return this.treeNodeMap;
	}

	@Override
	public boolean handleFile(String file) {
		return file.toLowerCase().endsWith(".js");
	}

	@Override
	public String getName() {
		return "js-rhino";
	}
}
