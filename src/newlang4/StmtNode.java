package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class StmtNode extends Node {
	private static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.NAME,
		LexicalType.FOR,
		LexicalType.END
	);

	public static boolean isFirst (LexicalUnit unit) {
		return FIRST_SET.contains(unit.getType());
	}

	private StmtNode (Environment env) {
		super(env);
		type = NodeType.STMT;
	}

	public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
		switch (first.getType()) {
		case NAME:
			return SubstNode.getHandler(first, env);
//		case FOR:
		case END:
			return EndNode.getHandler(first, env);
		default:
			throw new Exception("Syntax Error: Invalid token");
		}
	}

	@Override
	public boolean parse () throws Exception {
		throw new Exception("Parse Error");
	}

	@Override
	public String toString () {
		// TODO
		return null;
	}

	@Override
	public Value getValue () {
		// TODO
		return null;
	}
}
