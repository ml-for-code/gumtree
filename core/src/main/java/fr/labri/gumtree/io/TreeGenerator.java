package fr.labri.gumtree.io;

import java.io.IOException;

import fr.labri.gumtree.tree.Tree;
import fr.labri.gumtree.tree.TreeUtils;

public abstract class TreeGenerator {
	
	public Tree fromSource(String source, String file) throws IOException {
		Tree tree = generate(source, file);
		tree.refresh();
		TreeUtils.postOrderNumbering(tree);
		return tree;
	}
	
	public Tree fromFile(String file) throws IOException {
		Tree tree = generate(file);
		tree.refresh();
		TreeUtils.postOrderNumbering(tree);
		return tree;
	}
	
	public abstract Tree generate(String source, String file);
	
	public abstract Tree generate(String file) throws IOException;
	
	public abstract boolean handleFile(String file);
	
	public abstract String getName();

}
