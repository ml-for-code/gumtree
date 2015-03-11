package fr.labri.gumtree.gen.jdt.cd;

import fr.labri.gumtree.gen.jdt.AbstractJdtVisitor;
import fr.labri.gumtree.gen.jdt.AbstractJdtTreeGenerator;
import fr.labri.gumtree.tree.Tree;

public class CdJdtTreeGenerator extends AbstractJdtTreeGenerator {

	@Override
	public String getName() {
		return "java-jdt-cd";
	}

	@Override
	protected AbstractJdtVisitor createVisitor() {
		return new CdJdtVisitor();
	}

	@Override
	public Tree generate(String source, String file) {
		// TODO Auto-generated method stub
		return null;
	}

}
