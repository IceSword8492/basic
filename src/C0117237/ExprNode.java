package newlang4;

import java.util.EnumSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ExprNode extends Node {
    List<Node> operandNodes = new ArrayList<Node>();
    List<BinExprNode> binExprNodes = new ArrayList<BinExprNode>();
    public Node primedNode = null;
    Node value = null;

    public static final Set<LexicalType> FIRST_SET = EnumSet.of(
        LexicalType.NAME,
        LexicalType.INTVAL,
        LexicalType.DOUBLEVAL,
        LexicalType.LITERAL,
        LexicalType.LP,
        LexicalType.SUB
    );

    private static final Map<LexicalType, Integer> OPERATORS = new HashMap<LexicalType, Integer>();

    static {
        OPERATORS.put(LexicalType.DIV, 1);
        OPERATORS.put(LexicalType.MUL, 1);
        OPERATORS.put(LexicalType.SUB, 1);
        OPERATORS.put(LexicalType.ADD, 2);
    }

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
        if (!OPERATORS.containsKey(env.getInput().peek(2).getType())) {
            if (ConstNode.isFirst(env.getInput().peek())) {
                value = ConstNode.getHandler(env.getInput().get(), env);
            } else {
                value = VariableNode.getHandler(env.getInput().peek().getType(), env);
                env.getInput().get();
            }
            return true;
        }
        while (true) {
            Node operand = getOperand();
            operandNodes.add(operand);

            BinExprNode operator = getOperator();
            if (operator == null) {
                break;
            }
            binExprNodes.add(operator);
        }
        if (operandNodes.size() == 1 && binExprNodes.size() == 0) {
            primedNode = operandNodes.get(0);
        } else {
            primedNode = setBinExprNodes();
        }
        return true;
    }

    private Node getOperand () throws Exception {
        switch (env.getInput().peek().getType()) {
        case LP:
            env.getInput().get();
            Node exprHandler = ExprNode.getHandler(env.getInput().peek(), env);
            exprHandler.parse();
            if (env.getInput().expect(LexicalType.RP)) {
                env.getInput().get();
            } else {
                throw new Exception("Syntax Error: Too many '('");
            }
            return exprHandler;
        case SUB:
            LexicalUnit peeked2 = env.getInput().peek(2);
            if (ExprNode.isFirst(peeked2)
            ||  !(peeked2.getType() == LexicalType.SUB)
            ||  !(peeked2.getType() == LexicalType.LITERAL)) {
                env.getInput().get();
                LexicalUnit unit = new LexicalUnit(LexicalType.INTVAL, new ValueImpl(-1));
                operandNodes.add(ConstNode.getHandler(unit, env));
                binExprNodes.add(BinExprNode.getHandler(LexicalType.MUL));
                return getOperand();
            } else {
                throw new Exception("Syntax Error");
            }
        case INTVAL:
        case DOUBLEVAL:
        case LITERAL:
            return ConstNode.getHandler(env.getInput().get(), env);
        case NAME:
            if (env.getInput().expect(LexicalType.LP, 2)) {
                // TODO
                // Node handler = CallFuncNode.getHandler(env.getInput().peek(), env);
                // handler.parse();
                // return handler;
            } else {
                Node handler = VariableNode.getHandler(env.getInput().peek().getType(), env);
                env.getInput().get();
                return handler;
            }
        default:
            throw new Exception("Syntax Error");
        }
    }

    private BinExprNode getOperator () throws Exception {
        LexicalUnit peeked = env.getInput().peek();
        if (!OPERATORS.containsKey(peeked.getType())) {
            return null;
        }
        return BinExprNode.getHandler(env.getInput().get().getType());
    }

    private BinExprNode setBinExprNodes () throws Exception {
        if (operandNodes.size() < 2 || binExprNodes.size() < 1) {
            throw new Exception("Parsing Error");
        }
        boolean hasPending = false;
        int pendingID = 999;
        int priorityPrev, priorityNow;
        binExprNodes.get(0).setLeft(operandNodes.get(0));
        for (int i = 1; i < operandNodes.size() - 1; i++) {
            priorityPrev = binExprNodes.get(i - 1).getOperatorPriority();
            priorityNow = binExprNodes.get(i).getOperatorPriority();
            if (priorityPrev == priorityNow) {
                binExprNodes.get(i - 1).setRight(operandNodes.get(i));
                binExprNodes.get(i).setLeft(binExprNodes.get(i - 1));
            } else if (priorityPrev > priorityNow) {
                hasPending = true;
                pendingID = i - 1;
                binExprNodes.get(i).setLeft(operandNodes.get(i));
            } else {
                binExprNodes.get(i - 1).setRight(binExprNodes.get(i));
                if (!hasPending) {
                    binExprNodes.get(i).setLeft(binExprNodes.get(i - 1));
                } else {
                    binExprNodes.get(pendingID).setRight(binExprNodes.get(i - 1));
                    binExprNodes.get(i).setLeft(binExprNodes.get(pendingID));
                    hasPending = false;
                }
            }
        }
        BinExprNode lastBinExpr = binExprNodes.get(operandNodes.size() - 2);
        Node lastOperand = operandNodes.get(operandNodes.size() - 1);
        lastBinExpr.setRight(lastOperand);

        if (hasPending) {
            binExprNodes.get(pendingID).setRight(lastBinExpr);
            return binExprNodes.get(pendingID);
        }
        return lastBinExpr;
    }

    @Override
    public String toString () {
        if (primedNode != null) return primedNode.toString();
        return value.toString();
    }

    @Override
    public Value getValue () throws Exception {
        if (primedNode != null) {
            return primedNode.getValue();
        } else {
            return value.getValue();
        }
    }
}
