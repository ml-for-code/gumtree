package fr.labri.gumtree.io;

import java.io.IOException;

import fr.labri.gumtree.tree.Tree;
import fr.labri.gumtree.tree.TreeUtils;

public abstract class TreeGenerator {

	public Tree fromSource(String source, String file, boolean preProcess) throws IOException {
		Tree tree = generate(source, file, preProcess);
		tree.refresh();
		TreeUtils.postOrderNumbering(tree);
		return tree;
	}

	public Tree fromFile(String file, boolean preProcess) throws IOException {
		Tree tree = generate(file, preProcess);
		tree.refresh();
		TreeUtils.postOrderNumbering(tree);
		return tree;
	}

	public abstract Tree generate(String source, String file, boolean preProcess);

	public abstract Tree generate(String file, boolean preProcess) throws IOException;

	public abstract boolean handleFile(String file);

	public abstract String getName();

}
