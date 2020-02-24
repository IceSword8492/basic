package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class CondNode extends Node {
    Node left = null;
    Node right = null;
    LexicalType operator = null;

    public final static Set<LexicalType> FIRST_SET = EnumSet.of(
        LexicalType.NAME,
        LexicalType.INTVAL,
        LexicalType.DOUBLEVAL,
        LexicalType.LITERAL,
        LexicalType.LP,
        LexicalType.SUB
    );

    private CondNode (Environment env) {
        super(env);
        type = NodeType.COND;
    }

    public static boolean isFirst (LexicalUnit unit) {
        return FIRST_SET.contains(unit.getType());
    }

    public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
        if (!isFirst(first)) {
            throw new Exception("Syntax Error");
        } else {
            return new CondNode(env);
        }
    }

    public boolean parse () throws Exception {
        LexicalUnit unit = env.getInput().peek();
        left = ExprNode.getHandler(unit, env);
        left.parse();

        operator = env.getInput().get().getType();

        unit = env.getInput().peek();
        right = ExprNode.getHandler(unit, env);
        right.parse();
        return true;
    }

    public String toString () {
        String opstr;
        switch (operator) {
        case EQ:
            opstr = "=";
            break;
        case GT:
            opstr = ">";
            break;
        case LT:
            opstr = "<";
            break;
        case GE:
            opstr = ">=";
            break;
        case LE:
            opstr = "<=";
            break;
        case NE:
            opstr = "<>";
            break;
        default:
            opstr = null;
        }
        return left + " " + opstr + " " + right;
    }

    public Value getValue () throws Exception {
        if (left == null || right == null) {
            throw new Exception("Runtime Error: Null operand found");
        }
        Value lval = left.getValue();
        Value rval = right.getValue();
        if (lval == null || rval == null) {
            throw new Exception("Runtime Error: Null operand found");
        }
        if (lval.getType() == ValueType.STRING || rval.getType() == ValueType.STRING) {
            switch (operator) {
            case EQ:
                return new ValueImpl(lval.getSValue().equals(rval.getSValue()));
            case NE:
                return new ValueImpl(lval.getSValue().equals(rval.getSValue()));
            default:
                throw new Exception("Runtime Error: Invalid operator for String Cond");
            }
        }

        switch (operator) {
        case EQ:
            return new ValueImpl(lval.getDValue() == rval.getDValue());
        case NE:
            return new ValueImpl(lval.getDValue() != rval.getDValue());
        case GT:
            return new ValueImpl(lval.getDValue() > rval.getDValue());
        case GE:
            return new ValueImpl(lval.getDValue() >= rval.getDValue());
        case LT:
            return new ValueImpl(lval.getDValue() < rval.getDValue());
        case LE:
            return new ValueImpl(lval.getDValue() <= rval.getDValue());
        default:
            throw new Exception("Runtime Error: Invalid operator for Cond");
        }
    }
}
