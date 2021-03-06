package newlang4;

import java.util.EnumSet;
import java.util.Set;
import java.util.StringJoiner;

public class CallFuncNode extends Node {
    Function function = null;
    ExprListNode arg = null;

    private final static Set<LexicalType> FIRST_SET = EnumSet.of(
        LexicalType.NAME
    );

    private CallFuncNode (Environment env) {
        super(env);
        type = NodeType.FUNCTION_CALL;
    }

    public static boolean isFirst (LexicalUnit unit) {
        return FIRST_SET.contains(unit.getType());
    }

    public static Node getHandler (LexicalUnit first, Environment env) throws Exception {
        if (!isFirst(first)) {
            throw new Exception("Syntax Error");
        } else {
            return new CallFuncNode(env);
        }
    }

    public boolean parse () throws Exception {
        LexicalUnit unit = env.getInput().get();
        function = env.getFunction(unit.getValue().getSValue());
        env.getInput().get(); // LP
        unit = env.getInput().peek();
        arg = (ExprListNode)ExprListNode.getHandler(unit, env);
        arg.parse();
        env.getInput().get(); // RP
        return true;
    }

    @Override
    public String toString () {
        StringJoiner sj = new StringJoiner(", ");
        arg.args.stream().forEach(a -> sj.add(a.toString()));
        return function + "(" + sj + ")";
    }

    public Value getValue () {
        return function.invoke(arg);
    }
}
