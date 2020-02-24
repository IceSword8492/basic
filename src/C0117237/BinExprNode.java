package newlang4;

import java.util.Map;
import java.util.HashMap;

public class BinExprNode extends Node {
    Node left = null;
    Node right = null;
    LexicalType operator = null;

    private static final Map<LexicalType, Integer> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put(LexicalType.DIV, 1);
        OPERATORS.put(LexicalType.MUL, 1);
        OPERATORS.put(LexicalType.SUB, 2);
        OPERATORS.put(LexicalType.ADD, 2);
    }

    private BinExprNode (LexicalType operator) {
        this.operator = operator;
        type = NodeType.BIN_EXPR;
    }

    public static boolean isOperator (LexicalType type) {
        return OPERATORS.containsKey(type);
    }

    public static BinExprNode getHandler (LexicalType operator) throws Exception {
        if (!isOperator(operator)) {
            throw new Exception("Syntax Error");
        } else {
            return new BinExprNode(operator);
        }
    }

    public void setLeft (Node left) {
        this.left = left;
    }

    public void setRight (Node right) {
        this.right = right;
    }

    public int getOperatorPriority () {
        return OPERATORS.get(operator);
    }

    @Override
    public boolean parse () throws Exception {
        throw new Exception("Parsing Error");
    }

    @Override
    public String toString () {
        String opstr;
        switch (operator) {
        case DIV:
            opstr = "/";
            break;
        case MUL:
            opstr = "*";
            break;
        case SUB:
            opstr = "-";
            break;
        case ADD:
            opstr = "+";
            break;
        default:
            opstr = null;
        }
        return left + " " + right + " " + opstr;
    }

    @Override
    public Value getValue () throws Exception {
        if (left == null || right == null) {
            throw new Exception("Runtime Error: null operand found");
        }
        Value lval = left.getValue();
        Value rval = right.getValue();
        if (lval == null || rval == null) {
            throw new Exception("Runtime Error: null operand found");
        }
        double result;

        if (lval.getType() == ValueType.STRING || rval.getType() == ValueType.STRING) {
            if (operator == LexicalType.ADD) {
                return new ValueImpl(lval.getSValue() + rval.getSValue());
            } else {
                throw new Exception("Runtime Error: invalid operator");
            }
        }

        switch (operator) {
        case DIV:
            if (rval.getDValue() != .0) {
                result = lval.getDValue() / rval.getDValue();
            } else {
                throw new Exception("Runtime Error: ZeroDivisionError: division by zero");
            }
            break;
        case MUL:
            result = lval.getDValue() * rval.getDValue();
            break;
        case SUB:
            result = lval.getDValue() - rval.getDValue();
            break;
        case ADD:
            result = lval.getDValue() + rval.getDValue();
            break;
        default:
            throw new Exception("Runtime Error: invalid operator");
        }

        if (rval.getType() == ValueType.DOUBLE || lval.getType() == ValueType.DOUBLE) {
            return new ValueImpl(result);
        } else {
            return new ValueImpl((int)result);
        }
    }
}
