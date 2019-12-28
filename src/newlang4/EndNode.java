package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class EndNode extends Node {
	public static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.END
	);

	private EndNode (Environment env) {
		super(env);
		type = NodeType.END;
	}

	public static boolean isFirst (LexicalUnit unit) {
		return FIRST_SET.contains(unit.getType());
	}

	public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
		if (!isFirst(first)) {
			throw new Exception("Syntax Error: Invalid token");
		} else {
			return new EndNode(env);
		}
	}

	@Override
	public boolean parse () throws Exception {
		if (env.getInput().expect(LexicalType.END)) {
			env.getInput().get();
			return true;
		} else {
			throw new Exception("Parse Error");
		}
	}

	@Override
	public String toString () {
		return "EndNode";
	}
}
