package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class StmtNode extends Node {
    private static Node node = null;

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
            switch (env.getInput().peek(2).getType()) {
            case EQ:
                return node = SubstNode.getHandler(first, env);
            case LP:
                return node = CallFuncNode.getHandler(first, env);
            default:
            }
            if (ExprListNode.isFirst(env.getInput().peek(2))) {
                return node = CallSubNode.getHandler(first, env);
            } else {
                throw new Exception("Syntax Error");
            }
//		case FOR:
        case END:
            return node = EndNode.getHandler(first, env);
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
        return node.toString();
    }

    @Override
    public Value getValue () throws Exception {
        return node.getValue();
    }
}
