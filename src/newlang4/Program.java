package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class Program extends Node {
	Node stmtList = null;

	private static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.NAME,
		LexicalType.FOR,
		LexicalType.END,
		LexicalType.IF,
		LexicalType.WHILE,
		LexicalType.DO,
		LexicalType.NL
	);

	private Program (Environment env) {
		super(env);
		type = NodeType.PROGRAM;
	}

	public static boolean isFirst (LexicalUnit unit) {
		return FIRST_SET.contains(unit.getType());
	}

	public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
		if (!isFirst(first)) {
			throw new Exception("Syntax Error: Invalid token");
		} else {
			return new Program(env);
		}
	}

	@Override
	public boolean parse () throws Exception {
		stmtList = StmtListNode.getHandler(env.getInput().peek(), env);
		stmtList.parse();

		while (env.getInput().expect(LexicalType.NL)) {
			env.getInput().get();
		}

		if (!env.getInput().expect(LexicalType.EOF)) {
			throw new Exception("Syntax Error: END token must be end of input");
		}

		return true;
	}

	@Override
	public String toString () {
		return stmtList.toString();
	}

	public Value getValue() {
		// TODO
		return null;
	}
}
