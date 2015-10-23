package fr.labri.gumtree.gen.jdt;

import java.io.IOException;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import fr.labri.gumtree.io.TreeGenerator;
import fr.labri.gumtree.tree.Tree;

public abstract class AbstractJdtTreeGenerator extends TreeGenerator {

	/**
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Tree generate(String file) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		/* Source to be parsed as a compilation unit. */
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		/* Set the options for Java parsing. */
		Map pOptions = JavaCore.getOptions();
		pOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		pOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		pOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(pOptions);

		/* The requestor finds the correct concrete tree generator. */
		Requestor req = new Requestor(createVisitor());

		/* Generate the AST for the file.
		 * Param 1: String[] sourceFilePaths
		 * Param 2: String[] encodings
		 * Param 3: String[] bindingKeys
		 * Param 4: FileASTRequestor requestor. */
		parser.createASTs(new String[] { file }, null, new String[] {}, req, null);

		/* The requestor visits the parsed tree and builds a GumTree Tree. */
		return req.getVisitor().getTree();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Tree generate(String file, boolean preProcess) throws IOException {
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		/* Source to be parsed as a compilation unit. */
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		/* Set the options for Java parsing. */
		Map pOptions = JavaCore.getOptions();
		pOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		pOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		pOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(pOptions);

		/* The requestor finds the correct concrete tree generator. */
		Requestor req = new Requestor(createVisitor());

		/* Generate the AST for the file.
		 * Param 1: String[] sourceFilePaths
		 * Param 2: String[] encodings
		 * Param 3: String[] bindingKeys
		 * Param 4: FileASTRequestor requestor. */
		parser.createASTs(new String[] { file }, null, new String[] {}, req, null);

		/* The requestor visits the parsed tree and builds a GumTree Tree. */
		return req.getVisitor().getTree();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Tree generate(String source, String file, boolean preProcess) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		/* We are parsing the source string. */
		parser.setSource(source.toCharArray());

		/* Source to be parsed as a compilation unit. */
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		/* Set the options for Java parsing. */
		Map pOptions = JavaCore.getOptions();
		pOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		pOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		pOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(pOptions);

		/* Parse the file into an ASTNode (CompilationUnit). */
		CompilationUnit compilationUnit = (CompilationUnit)parser.createAST(null);

		/* Visit the parsed tree and build a GumTree Tree. */
		JdtVisitor visitor = new JdtVisitor();
		compilationUnit.accept(visitor);

		return visitor.getTree();

	}


	@Override
	public boolean handleFile(String file) {
		return file.endsWith(".java");
	}

	protected abstract AbstractJdtVisitor createVisitor();

}
