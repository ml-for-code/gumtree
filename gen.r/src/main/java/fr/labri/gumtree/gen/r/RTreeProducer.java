package fr.labri.gumtree.gen.r;

import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;

import fr.labri.gumtree.gen.antlr.AbstractAntlrTreeGenerator;
import fr.labri.gumtree.tree.Tree;

public class RTreeProducer extends AbstractAntlrTreeGenerator {

	@Override
	protected CommonTree getStartSymbol(String file) throws RecognitionException, IOException {
		ANTLRStringStream stream = new ANTLRFileStream(file);
		RLexer rl = new RLexer(stream);
		tokens = new TokenRewriteStream(rl);
		RParser rp = new RParser(tokens);
		return rp.script().getTree();
	}

	@Override
	protected Parser getEmptyParser() {
		ANTLRStringStream stream = new ANTLRStringStream();
		RLexer l = new RLexer(stream);
		CommonTokenStream tokens = new TokenRewriteStream(l);
		return new RParser(tokens);
	}

	@Override
	public boolean handleFile(String file) {
		return file.toLowerCase().endsWith(".r");
	}

	@Override
	public String getName() {
		return "r-antlr";
	}

	@Override
	public Tree generate(String source, String file, boolean preProcess) {
		// TODO Auto-generated method stub
		return null;
	}

}
