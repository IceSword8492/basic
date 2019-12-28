package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ExprNode extends Node {
	public static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.NAME,
		LexicalType.INTVAL,
		LexicalType.DOUBLEVAL,
		LexicalType.LITERAL,
		LexicalType.LP,
		LexicalType.SUB
	);

	private Node left = null;
	private LexicalUnit operator = null;
	private Node right = null;

	private ExprNode (Environment env) {
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
			return new ExprNode(env);
		}
	}

	@Override
	public boolean parse () throws Exception {
		LexicalUnit unit = null;
		unit = env.getInput().get();
//		if (env.getInput().expect(LexicalType.NAME)) {
//			if (env.getInput().peek(2).getType() == LexicalType.LP) {
//
//			} else if (env.getInput().peek(2).getType() == LexicalType.EQ) {
//
//			} else if (env.getInput().peek(2).getType() == LexicalType.INTVAL) {
//				System.out.println(unit);
//			} else {
//				left = VariableNode.getHandler(unit.getType(), env);
//			}
//		}
		if (unit.getType() == LexicalType.INTVAL) {

		}
		operator = env.getInput().get();
		unit = env.getInput().get();
		if (env.getInput().expect(LexicalType.NAME)) {
			if (env.getInput().peek(2).getType() == LexicalType.LP) {

			} else if (env.getInput().peek(2).getType() == LexicalType.EQ) {

			} else {
				right = VariableNode.getHandler(unit.getType(), env);
			}
		}
		return true;
	}

	@Override
	public String toString () {
		return left + " " + operator + " " + right;
	}
}
