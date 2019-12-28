package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class SubstNode extends Node {
	public static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.NAME
	);

	private Node variable = null;
	private Node expression = null;

	private SubstNode (Environment env) {
		super(env);
		type = NodeType.ASSIGN_STMT;
	}

	public static boolean isFirst (LexicalUnit unit) {
		return FIRST_SET.contains(unit.getType());
	}

	public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
		if (!isFirst(first)) {
			throw new Exception("Syntax Error: Invalid token");
		} else {
			return new SubstNode(env);
		}
	}

	@Override
	public boolean parse () throws Exception {
		LexicalUnit unit = null;
		if (env.getInput().expect(LexicalType.NAME)) {
			unit = env.getInput().get();
			variable = VariableNode.getHandler(unit.getType(), env);
		} else {
			throw new Exception("Parse Error: Unexpected token: " + unit);
		}
		if (env.getInput().expect(LexicalType.EQ)) {
			unit = env.getInput().get();
		} else {
			throw new Exception("Parse Error: Unexpected token: " + unit);
		}
		if (ExprNode.isFirst(env.getInput().peek())) {
			unit = env.getInput().peek();
			expression = ExprNode.getHandler(unit, env);
			expression.parse();
			return true;
		} else {
			throw new Exception("Parse Error: Unexpcted token: " + unit);
		}
	}

	@Override
	public String toString () {
		return variable + " = " + expression;
	}
}
