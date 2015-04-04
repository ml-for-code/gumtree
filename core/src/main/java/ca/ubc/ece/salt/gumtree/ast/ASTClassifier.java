package ca.ubc.ece.salt.gumtree.ast;

import java.io.InvalidClassException;
import java.util.Set;

import ca.ubc.ece.salt.gumtree.ast.ClassifiedASTNode.ChangeType;
import fr.labri.gumtree.actions.TreeClassifier;
import fr.labri.gumtree.matchers.MappingStore;
import fr.labri.gumtree.tree.Tree;

public class ASTClassifier {
	
	private Tree srcTree;
	private Tree dstTree;
	
	private TreeClassifier treeClassifier;
	private MappingStore mappings;
	
	public ASTClassifier(Tree srcTree, Tree dstTree, TreeClassifier treeClassifier, MappingStore mappings) {
		
		this.srcTree = srcTree;
		this.dstTree = dstTree;
		this.treeClassifier = treeClassifier;
		this.mappings = mappings;
		
	}

	public void classifyASTNodes() throws InvalidClassException {
		
		/* Classify the AST nodes from the Tree nodes. */
		this.classifyAs(this.treeClassifier.getSrcDelTrees(), ChangeType.REMOVED);
		
		/* Classify the children of the classified  AST nodes and assign
		 * node mappings for MOVED and UPDATED nodes. */
		this.classifyASTNode(this.srcTree, ChangeType.UNCHANGED, true);
		this.classifyASTNode(this.dstTree, ChangeType.UNCHANGED, false);
		
	}
	
	private void classifyAs(Set<Tree> classifiedTreeNodes, ChangeType changeType) throws InvalidClassException {

		/* Iterate through the changed Tree nodes and classify the 
		 * corresponding AST node with the Tree node's class. */
		for(Tree tree : classifiedTreeNodes) {
			ClassifiedASTNode astNode = tree.getClassifiedASTNode(); 
            astNode.setChangeType(ClassifiedASTNode.ChangeType.REMOVED);
		}
		
	}
	
	/**
	 * Recursively classifies the Tree node's AST nodes with the change type
	 * of their parent. The classification only occurs if the current node's
	 * class is {@code UNCHANGED}.
	 * @param node The node to classify.
	 * @param changeType The change type to assign the AST node.
	 * @throws InvalidClassException 
	 */
	private void classifyASTNode(Tree node, ChangeType changeType, boolean isSrc) throws InvalidClassException {
		
		/* Set the change class for the AST node if it is currently UNCHANGED. */
		
		ClassifiedASTNode classifiedNode = node.getClassifiedASTNode();
		ChangeType nodeChangeType = classifiedNode.getChangeType();

		if(nodeChangeType == ChangeType.UNCHANGED) {
			classifiedNode.setChangeType(changeType);
			changeType = nodeChangeType;
		}
		
		/* Assign the node mapping if this is an UPDATED or MOVED node. */
		
		if(changeType == ChangeType.MOVED || changeType == ChangeType.UPDATED) {
			if(isSrc) {
				Tree dst = this.mappings.getDst(node);
				if(dst != null) classifiedNode.map(dst.getClassifiedASTNode());
			}
			else {
				Tree src = this.mappings.getSrc(node);
				if(src != null) classifiedNode.map(src.getClassifiedASTNode());
			}
		}
		
		/* Classify this node's children with the new change type. */
		
		for(Tree child : node.getChildren()) {
			classifyASTNode(child, changeType, isSrc);
		}
		
	}
	
}
