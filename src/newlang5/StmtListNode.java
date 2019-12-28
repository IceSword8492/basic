package newlang5;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class StmtListNode extends Node {
	List<Node> nodes;

	private static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.NAME,
		LexicalType.FOR,
		LexicalType.END,
		LexicalType.IF,
		LexicalType.WHILE,
		LexicalType.DO,
		LexicalType.NL
	);

	private StmtListNode (Environment env) {
		super(env);
		type = NodeType.STMT_LIST;
	}

	public static boolean isFirst (LexicalUnit unit) {
		return FIRST_SET.contains(unit.getType());
	}

	public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
		if (!isFirst(first)) {
			throw new Exception("Syntax Error: Invalid token");
		} else {
			return new StmtListNode(env);
		}
	}

	@Override
	public boolean parse () throws Exception {
		nodes = new ArrayList<Node>();
		LexicalUnit peeked;
		Node handler = null;

		while (true) {
			while (env.getInput().expect(LexicalType.NL)) {
				System.out.println(env.getInput().get());
			}
			peeked = env.getInput().peek();

			if (StmtNode.isFirst(peeked)) {
				handler = StmtNode.getHandler(peeked, env);
//			} else if (Block.isFirst(peeked)) {
//				handler = BlockNode.getHandler(peeked, env);
			} else {
				throw new Exception("Syntax Error: " + handler + " is not valid");
			}
			handler.parse();
			nodes.add(handler);

			if (handler.getType() == NodeType.END) {
				return true;
			}
		}
	}

	@Override
	public String toString () {
		String res = "";
		for (int i = 0; i < nodes.size(); i++) {
			res += nodes.get(i).toString();
		}
		return res;
	}

	@Override
	public Value getValue() {
		// TODO
		return null;
	}
}
