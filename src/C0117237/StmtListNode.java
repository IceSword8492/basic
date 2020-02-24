package newlang4;

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
                env.getInput().get();
            }
            peeked = env.getInput().peek();

            if (StmtNode.isFirst(peeked)) {
                handler = StmtNode.getHandler(peeked, env);
			} else if (BlockNode.isFirst(peeked)) {
				handler = BlockNode.getHandler(peeked, env);
            } else if (peeked.getType() == LexicalType.LOOP) {
                return true;
            } else {
                throw new Exception("Syntax Error");
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
        for (Node node : nodes) {
            res += node.toString() + "\n";
        }
        return res;
    }

    @Override
    public Value getValue() throws Exception {
        Value res = null;
        for (Node node : nodes) {
            res = node.getValue();
        }
        return res; // return last value
    }
}
