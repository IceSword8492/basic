package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ConstNode extends Node {
    Value value = null;

    public static final Set<LexicalType> FIRST_SET = EnumSet.of(
        LexicalType.INTVAL,
        LexicalType.DOUBLEVAL,
        LexicalType.LITERAL
    );

    private ConstNode (LexicalUnit first, Environment env) throws Exception {
        super(env);
        switch (first.getType()) {
        case INTVAL:
            type = NodeType.INT_CONSTANT;
            break;
        case DOUBLEVAL:
            type = NodeType.DOUBLE_CONSTANT;
            break;
        case LITERAL:
            type = NodeType.STRING_CONSTANT;
            break;
        default:
            throw new Exception("Syntax Error: Invalid input for ConstNode");
        }
        value = first.getValue();
    }

    public static boolean isFirst (LexicalUnit unit) {
        return FIRST_SET.contains(unit.getType());
    }

    public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
        if (!isFirst(first)) {
            throw new Exception("Syntax Error");
        } else {
            return new ConstNode(first, env);
        }
    }

    @Override
    public boolean parse () throws Exception {
        throw new Exception ("Parsing Error");
    }

    @Override
    public Value getValue () {
        return value;
    }

    @Override
    public String toString () {
        return value.getSValue();
    }
}
