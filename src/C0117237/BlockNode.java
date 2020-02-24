package newlang4;

import java.util.EnumSet;
import java.util.Set;

/**
 *  <block> ::=
 *      <if_prefix> <stmt> <NL>
 *      | <if_prefix> <stmt> <ELSE> <stmt> <ENDIF> <NL>
 *      | <if_prefix> <NL> <stmt_list> <else_block> <ENDIF> <NL>
 *      | <WHILE> <cond> <NL> <stmt_list> <WEND> <NL>
 *      | <DO> <WHILE> <cond> <NL> <stmt_list> <LOOP> <NL>
 * !    | <DO> <UNTIL> <cond> <NL> <stmt_list> <LOOP> <NL>
 *      | <DO> <NL> <stmt_list> <LOOP> <WHILE> <cond> <NL>
 *      | <DO> <NL> <stmt_list> <LOOP> <UNTIL> <cond> <NL>
 */

public class BlockNode extends Node {
    boolean isIf = false;
    boolean isWhile = false;
    boolean isDoWhile = false;
    boolean isDoUntil = false;
    boolean isDo_While = false;
    boolean isDo_Until = false;

    Node cond = null;
    Node body = null;

    private final static Set<LexicalType> FIRST_SET = EnumSet.of(
        LexicalType.WHILE,
        LexicalType.DO
    );

    private BlockNode (Environment env) {
        super(env);
        type = NodeType.BLOCK;
    }

    public static boolean isFirst (LexicalUnit unit) {
        return FIRST_SET.contains(unit.getType());
    }

    public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
        if (!isFirst(first)) {
            throw new Exception("Syntax Error");
        } else {
            return new BlockNode(env);
        }
    }

    public boolean parse () throws Exception {
        LexicalUnit unit = null;
        if (env.getInput().peek().getType() == LexicalType.DO) {
            if (env.getInput().peek(2).getType() == LexicalType.UNTIL) {
                isDoUntil = true;
                env.getInput().get(2);
                unit = env.getInput().peek();
                cond = CondNode.getHandler(unit, env);
                cond.parse();
                unit = env.getInput().peek();
                body = StmtListNode.getHandler(unit, env);
                body.parse();
                if (env.getInput().peek().getType() != LexicalType.LOOP) {
                    throw new Exception("Syntax Error");
                } else {
                    env.getInput().get();
                }
                if (env.getInput().peek().getType() != LexicalType.NL) {
                    throw new Exception("Syntax Error");
                } else {
                    env.getInput().get();
                }
            }
        }
        return false;
    }

    @Override
    public String toString () {
        if (isIf) {
            return null;
        }
        if (isWhile) {
            return null;
        }
        if (isDoWhile) {
            return null;
        }
        if (isDoUntil) {
            return "DO UNTIL " + cond + "\n" + body + "LOOP";
        }
        if (isDo_While) {
            return null;
        }
        if (isDo_Until) {
            return null;
        }
        return null;
    }

    public Value getValue () throws Exception {
        if (isIf) {
            return null;
        }
        if (isWhile) {
            return null;
        }
        if (isDoWhile) {
            return null;
        }
        if (isDoUntil) {
            while (judge()) {
                body.getValue();
            }
            return null;
        }
        if (isDo_While) {
            return null;
        }
        if (isDo_Until) {
            return null;
        }
        return null;
    }

    private boolean judge () throws Exception {
        if (cond.getValue().getBValue() == true) {
            return true;
        } else {
            return false;
        }
    }
}
