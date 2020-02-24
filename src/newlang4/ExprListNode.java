package newlang4;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class ExprListNode extends Node {
    List<Node> args = new ArrayList<>();

    private final static Set<LexicalType> FIRST_SET = ExprNode.FIRST_SET;

    private ExprListNode (Environment env) {
        super(env);
        type = NodeType.EXPR_LIST;
    }

    public static boolean isFirst (LexicalUnit unit) {
        return FIRST_SET.contains(unit.getType());
    }

    public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
        if (!isFirst(first)) {
            throw new Exception("Syntax Error");
        } else {
            return new ExprListNode(env);
        }
    }

    public boolean parse () throws Exception {
        LexicalUnit unit;
        while (true) {
            unit = env.getInput().peek();
            Node tmp = ExprNode.getHandler(unit, env);
            tmp.parse();
            args.add(tmp);
            unit = env.getInput().peek();
            if (unit.getType() == LexicalType.COMMA) {
                env.getInput().get(); // ,
            } else {
                break;
            }
        }
        return false;
    }

    @Override
    public String toString () {
        return null;
    }

    public Value getValue () {
        // TODO
        return null;
    }
}
