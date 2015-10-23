package fr.labri.gumtree.gen.js;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NodeVisitor;
import org.mozilla.javascript.ast.NumberLiteral;
import org.mozilla.javascript.ast.StringLiteral;

import fr.labri.gumtree.tree.Tree;

public class RhinoTreeVisitor implements NodeVisitor {

	private Tree tree;

	private Map<AstNode, Tree> trees;

	public RhinoTreeVisitor(AstRoot root) {
		trees = new HashMap<>();
		tree = buildTree(root);
	}

	@Override
	public boolean visit(AstNode node) {
		if (node instanceof AstRoot)
			return true;
		else {
			Tree t = buildTree(node);
			Tree p = trees.get(node.getParent());
			if(p == null)
				System.out.println(node.toSource());
			p.addChild(t);

			if (node instanceof Name) {
				Name name = (Name) node;
				t.setLabel(name.getIdentifier());
			} else if ( node instanceof StringLiteral) {
				StringLiteral literal = (StringLiteral) node;
				t.setLabel(literal.getValue());
			} else if ( node instanceof NumberLiteral) {
				NumberLiteral l = (NumberLiteral) node;
				t.setLabel(l.getValue());
			}

			return true;
		}
	}

	public Tree getTree() {
		return tree;
	}

	/**
	 * This provides clients with efficient access to Tree nodes if they have
	 * AstNodes. For example, a client may be  working with AstNodes instead
	 * of the Tree nodes (AstNodes provide more data and functionality). This
	 * will allow them to get the GumTree data for each AstNode without having
	 * to perform an inefficient search.
	 * @return The map of each AstNode to the Tree node that it created.
	 */
	public Map<AstNode, Tree> getTreeNodeMap(){
		return this.trees;
	}

	private Tree buildTree(AstNode node)  {
		Tree t = new Tree(node.getType());
		t.setPos(node.getAbsolutePosition());
		t.setLength(node.getLength());
		t.setTypeLabel(Token.typeToName(node.getType()));
        t.setASTNode(node); // qhanam
		trees.put(node, t);
		return t;
	}

}
